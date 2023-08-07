package com.example.statisticsservice.repository;

import com.example.statisticsservice.entity.UserStatistics;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {

    UserStatistics findBySteamId(String steamId);

    @Query("SELECT u FROM UserStatistics u WHERE u.steamId <> :SteamID ORDER BY sqrt(power((u.kda - :KDA),2) + power(((u.wins/(u.wins + u.losses)) - (:WIN/(:WIN + :LOSE))),2)) LIMIT 10")
    List<UserStatistics> findUserByKdaRange (String SteamID,double KDA, Integer WIN, Integer LOSE);
}
