package com.example.statisticsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticsDto {
    private Long id;
    private String steamNick;
    private String steamId;
    private double kda;
    private String headshotPercentage;
    private String winPercentage;
    private String mvp;
    private String kills;
    private String deaths;
    private String headshots;
    private Integer wins;
    private Integer losses;
    private String score;
    private String damage;
    private String shotsAcuury;
    private String planted;
    private String defused;
    private String money;
}
