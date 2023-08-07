package com.example.statisticsservice.service;

import com.example.statisticsservice.dto.UserStatisticsDto;
import com.example.statisticsservice.entity.UserStatistics;

import java.util.List;

public interface UserStatisticsService {
    UserStatisticsDto saveStatistics(UserStatisticsDto userStatisticsDto);

    UserStatisticsDto getStatisticsBySteamId(String steamId);

    List<UserStatistics> getUsersKdaRange(String steamId);
}
