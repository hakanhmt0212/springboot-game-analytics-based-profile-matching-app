package com.example.statisticsservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "statistics")
public class UserStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
