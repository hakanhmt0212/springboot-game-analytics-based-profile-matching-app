package com.example.statisticsservice.controller;

import com.example.statisticsservice.dto.UserStatisticsDto;
import com.example.statisticsservice.entity.UserStatistics;
import com.example.statisticsservice.service.UserStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user-statistics")
@AllArgsConstructor
public class UserStatisticsController {

    private UserStatisticsService userStatisticsService;

    @PostMapping
    public ResponseEntity<UserStatisticsDto> saveUserStatistics(@RequestBody UserStatisticsDto userStatisticsDto) {
        UserStatisticsDto savedUserStatistics = userStatisticsService.saveStatistics(userStatisticsDto);
        return new ResponseEntity<>(savedUserStatistics, HttpStatus.CREATED);
    }

    @GetMapping("{steam-id}")
    public ResponseEntity<UserStatisticsDto> getUserStatistics(@PathVariable("steam-id") String steamId) {
        UserStatisticsDto userStatisticsDto = userStatisticsService.getStatisticsBySteamId(steamId);
        return new ResponseEntity<>(userStatisticsDto, HttpStatus.OK);
    }

    @GetMapping("find/{steam-id}")
    public  ResponseEntity<List<UserStatistics>> getUsers(@PathVariable("steam-id") String steamId) {
        List<UserStatistics> userStatisticsList = userStatisticsService.getUsersKdaRange(steamId);
        return new ResponseEntity<>(userStatisticsList, HttpStatus.OK);
    }
}
