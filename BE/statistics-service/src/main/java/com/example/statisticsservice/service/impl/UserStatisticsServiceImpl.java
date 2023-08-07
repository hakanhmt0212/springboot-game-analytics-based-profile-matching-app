package com.example.statisticsservice.service.impl;

import com.example.statisticsservice.dto.UserStatisticsDto;
import com.example.statisticsservice.entity.UserStatistics;
import com.example.statisticsservice.repository.UserStatisticsRepository;
import com.example.statisticsservice.service.UserStatisticsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatisticsServiceImpl implements UserStatisticsService {

    private final UserStatisticsRepository userStatisticsRepository;

    public UserStatisticsServiceImpl(UserStatisticsRepository userStatisticsRepository) {
        this.userStatisticsRepository = userStatisticsRepository;
    }

    @Override
    public UserStatisticsDto saveStatistics(UserStatisticsDto userStatisticsDto) {

        UserStatistics userStatistics = new UserStatistics(
                userStatisticsDto.getId(),
                userStatisticsDto.getSteamNick(),
                userStatisticsDto.getSteamId(),
                userStatisticsDto.getKda(),
                userStatisticsDto.getHeadshotPercentage(),
                userStatisticsDto.getWinPercentage(),
                userStatisticsDto.getMvp(),
                userStatisticsDto.getKills(),
                userStatisticsDto.getDeaths(),
                userStatisticsDto.getHeadshots(),
                userStatisticsDto.getWins(),
                userStatisticsDto.getLosses(),
                userStatisticsDto.getScore(),
                userStatisticsDto.getDamage(),
                userStatisticsDto.getShotsAcuury(),
                userStatisticsDto.getPlanted(),
                userStatisticsDto.getDefused(),
                userStatisticsDto.getMoney()
        );

        UserStatistics savedUserStatistics = userStatisticsRepository.save(userStatistics);

        return new UserStatisticsDto(
                savedUserStatistics.getId(),
                savedUserStatistics.getSteamNick(),
                savedUserStatistics.getSteamId(),
                savedUserStatistics.getKda(),
                savedUserStatistics.getHeadshotPercentage(),
                savedUserStatistics.getWinPercentage(),
                savedUserStatistics.getMvp(),
                savedUserStatistics.getKills(),
                savedUserStatistics.getDeaths(),
                savedUserStatistics.getHeadshots(),
                savedUserStatistics.getWins(),
                savedUserStatistics.getLosses(),
                savedUserStatistics.getScore(),
                savedUserStatistics.getDamage(),
                savedUserStatistics.getShotsAcuury(),
                savedUserStatistics.getPlanted(),
                savedUserStatistics.getDefused(),
                savedUserStatistics.getMoney()
        );
    }

    @Override
    public UserStatisticsDto getStatisticsBySteamId(String steamId) {

        UserStatistics userStatistics = userStatisticsRepository.findBySteamId(steamId);

        UserStatisticsDto userStatisticsDto = new UserStatisticsDto(
                userStatistics.getId(),
                userStatistics.getSteamNick(),
                userStatistics.getSteamId(),
                userStatistics.getKda(),
                userStatistics.getHeadshotPercentage(),
                userStatistics.getWinPercentage(),
                userStatistics.getMvp(),
                userStatistics.getKills(),
                userStatistics.getDeaths(),
                userStatistics.getHeadshots(),
                userStatistics.getWins(),
                userStatistics.getLosses(),
                userStatistics.getScore(),
                userStatistics.getDamage(),
                userStatistics.getShotsAcuury(),
                userStatistics.getPlanted(),
                userStatistics.getDefused(),
                userStatistics.getMoney()
        );
        return userStatisticsDto;
    }

    @Override
    public List<UserStatistics> getUsersKdaRange(String steamId) {
        UserStatistics userStatistics = userStatisticsRepository.findBySteamId(steamId);

        double kda = userStatistics.getKda();
        Integer win = userStatistics.getWins();
        Integer lose = userStatistics.getLosses();
        List<UserStatistics> UserList = userStatisticsRepository.findUserByKdaRange(steamId, kda, win, lose);

        return UserList;
    }
}
