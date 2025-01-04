package com.windFarm.repository;

import com.windFarm.entity.WindFarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WindFarmRepository extends JpaRepository<WindFarm, Long> {

}
