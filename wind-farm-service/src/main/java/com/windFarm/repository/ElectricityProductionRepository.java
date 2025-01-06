package com.windFarm.repository;

import com.windFarm.entity.ElectricityProduction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ElectricityProductionRepository extends JpaRepository<ElectricityProduction, Long> {

    @Query("SELECT e FROM ElectricityProduction e " +
            "WHERE e.windFarm.id = :windFarmId " +
            "AND e.timestamp BETWEEN :fromDate AND :toDate " +
            "ORDER BY e.timestamp DESC")
    Page<ElectricityProduction> findElectricityProduction(
            @Param("windFarmId") long windFarmId,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable);

    @Query("SELECT AVG(e.electricityProducedMW) FROM ElectricityProduction e " +
            "WHERE e.windFarm.id = :windFarmId " +
            "AND e.timestamp BETWEEN :fromDate AND :toDate")
    Double getAverageElectricityProducedMW(
            @Param("windFarmId") long windFarmId,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate);


}


