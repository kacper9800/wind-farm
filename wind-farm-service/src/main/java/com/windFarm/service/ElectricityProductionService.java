package com.windFarm.service;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.entity.ElectricityProduction;
import com.windFarm.mapper.ElectricityProductionMapper;
import com.windFarm.repository.ElectricityProductionRepository;
import org.springframework.stereotype.Service;

@Service
public class ElectricityProductionService {


    private final ElectricityProductionMapper mapper;
    private final ElectricityProductionRepository electricityProductionRepository;

    public ElectricityProductionService(ElectricityProductionMapper mapper, ElectricityProductionRepository electricityProductionRepository) {
        this.mapper = mapper;
        this.electricityProductionRepository = electricityProductionRepository;
    }

    public void createElectricityProduction(ElectricityProductionDto value) {
        ElectricityProduction electricityProduction = mapper.toEntity(value);
        electricityProductionRepository.save(electricityProduction);
    }
}
