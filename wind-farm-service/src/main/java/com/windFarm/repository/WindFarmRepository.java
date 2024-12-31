package com.windFarm.repository;

import com.windFarm.entity.ElectricityProduction;
import com.windFarm.entity.WindFarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WindFarmRepository extends JpaRepository<WindFarm, Long> {

    @Query("SELECT e FROM ElectricityProduction e WHERE e.windFarm.id = :windFarmId AND e.timestamp BETWEEN :fromDate AND :toDate")
    List<ElectricityProduction> findElectricityProductionByWindFarmIdAndTimestampBetween(
            @Param("windFarmId") long windFarmId,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

}
