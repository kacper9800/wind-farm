package com.windFarm.repository;

import com.windFarm.entity.ElectricityProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricityProductionRepository extends JpaRepository<ElectricityProduction, Long> {
}
