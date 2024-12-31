package com.windFarm.service;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.dto.WindFarmDto;
import com.windFarm.entity.ElectricityProduction;
import com.windFarm.entity.WindFarm;
import com.windFarm.mapper.ElectricityProductionMapper;
import com.windFarm.mapper.WindFarmMapper;
import com.windFarm.repository.WindFarmRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WindFarmService {
    private final WindFarmRepository repository;

    private final WindFarmMapper windFarmMapper;
    private final ElectricityProductionMapper electricityProductionMapper;

    public WindFarmService(WindFarmRepository repository, WindFarmMapper windFarmMapper, ElectricityProductionMapper electricityProductionMapper) {
        this.repository = repository;
        this.windFarmMapper = windFarmMapper;
        this.electricityProductionMapper = electricityProductionMapper;
    }

    public List<WindFarmDto> getAllWindFarms() {
        return repository.findAll().stream().map(windFarmMapper::toDto).toList();
    }

    public WindFarmDto getWindFarmInfo(long id) {
        return repository.findById(id)
                .map(windFarmMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("WindFarm with id " + id + " not found"));
    }

    public void createWindFarm(WindFarmDto dto) {
        WindFarm windFarm = windFarmMapper.toEntity(dto);
        repository.save(windFarm);
    }

    public List<ElectricityProductionDto> getElectricityProduction(long windFarmId, LocalDateTime filterFromDate, LocalDateTime filterToDate) {
        List<ElectricityProduction> results = repository.findElectricityProductionByWindFarmIdAndTimestampBetween(windFarmId, filterFromDate, filterToDate);
        return results.stream().map(electricityProductionMapper::toDto).toList();
    }
}