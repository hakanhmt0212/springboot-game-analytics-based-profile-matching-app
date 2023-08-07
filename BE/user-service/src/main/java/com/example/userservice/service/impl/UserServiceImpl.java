package com.example.userservice.service.impl;

import org.json.*;
import com.example.userservice.dto.APIResponsDto;
import com.example.userservice.dto.StatisticsDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import lombok.AllArgsConstructor;
import netscape.javascript.JSObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RestTemplate restTemplate;
    @Override
    public UserDto saveUser(UserDto userDto) {


        User user = new User(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getNickName(),
                userDto.getSteamId()
        );
        User savedUser = userRepository.save(user);

        UserDto savedUserDto = new UserDto(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getPassword(),
                savedUser.getNickName(),
                savedUser.getSteamId()
        );

        ResponseEntity<String> response = callExternalApi("https://public-api.tracker.gg/v2/csgo/standard/profile/steam/" + savedUser.getSteamId());
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("segments");
        JSONObject jsonForNick = jsonObject.getJSONObject("data").getJSONObject("platformInfo");

        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setSteamId(savedUser.getSteamId());
        statisticsDto.setSteamNick(jsonForNick.getString("platformUserHandle"));
        statisticsDto.setKda(Double.parseDouble(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("kd").getString("displayValue")));
        statisticsDto.setHeadshotPercentage(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("headshotPct").getString("displayValue"));
        statisticsDto.setWinPercentage(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("wlPercentage").getString("displayValue"));
        statisticsDto.setMvp(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("mvp").getString("displayValue"));
        statisticsDto.setKills(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("kills").getString("displayValue"));
        statisticsDto.setDeaths(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("deaths").getString("displayValue"));
        statisticsDto.setHeadshots(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("headshots").getString("displayValue"));
        statisticsDto.setWins(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("wins").getInt("value"));
        statisticsDto.setLosses(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("losses").getInt("value"));
        statisticsDto.setScore(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("score").getString("displayValue"));
        statisticsDto.setDamage(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("damage").getString("displayValue"));
        statisticsDto.setShotsAcuury(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("shotsAccuracy").getString("displayValue"));
        statisticsDto.setPlanted(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("bombsPlanted").getString("displayValue"));
        statisticsDto.setDefused(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("bombsDefused").getString("displayValue"));
        statisticsDto.setMoney(jsonArray.getJSONObject(0).getJSONObject("stats").getJSONObject("moneyEarned").getString("displayValue"));

        ResponseEntity<StatisticsDto> responseEntity = restTemplate.postForEntity("http://localhost:8081/api/user-statistics", statisticsDto, StatisticsDto.class);

        return savedUserDto;
    }

    public ResponseEntity<String> callExternalApi(@RequestBody String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("TRN-Api-Key","2b43cf7c-02e4-4a99-8dac-8b198cbf1a29");
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response;
    }

    @Override
    public APIResponsDto getUserBySteamId(String steamId) {
        User user = userRepository.findBySteamId(steamId);

        ResponseEntity<StatisticsDto> responseEntity = restTemplate.getForEntity("http://localhost:8081/api/user-statistics/" + user.getSteamId(),
                StatisticsDto.class);
        StatisticsDto statisticsDto = responseEntity.getBody();

        UserDto userDto = new UserDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getNickName(),
                user.getSteamId()
        );

        APIResponsDto apiResponsDto = new APIResponsDto();
        apiResponsDto.setUserDto(userDto);
        apiResponsDto.setStatisticsDto(statisticsDto);

        return apiResponsDto;
    }

    @Override
    public ResponseEntity loginUser(String email, String password) {
        Optional<User> loggedUser = userRepository.findByEmail(email);
        if (loggedUser.isPresent()) {
            UserDto loggedUserDto = new UserDto(loggedUser.get().getId(),
                    loggedUser.get().getEmail(),
                    loggedUser.get().getPassword(),
                    loggedUser.get().getNickName(),
                    loggedUser.get().getSteamId());
            String givenPassword = loggedUserDto.getPassword();
            if (givenPassword.equals(password)) {
                return new ResponseEntity<>(loggedUserDto, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Given password is not correct.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Given user id is not exist.");
    }
}
