package com.windFarm.service;

import com.windFarm.dto.WindFarmDto;
import com.windFarm.entity.WindFarm;
import com.windFarm.mapper.ElectricityProductionMapper;
import com.windFarm.mapper.WindFarmMapper;
import com.windFarm.repository.WindFarmRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WindFarmService {
    private final WindFarmRepository repository;
    private final WindFarmMapper windFarmMapper;

    public WindFarmService(WindFarmRepository repository, WindFarmMapper windFarmMapper, ElectricityProductionMapper electricityProductionMapper) {
        this.repository = repository;
        this.windFarmMapper = windFarmMapper;
    }

    public List<WindFarmDto> getAllWindFarms() {
        return repository.findAll().stream().map(windFarmMapper::toDto).toList();
    }

    public WindFarmDto getWindFarmInfo(long id) {
        return repository.findById(id)
                .map(windFarmMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("WindFarm with id " + id + " not found"));
    }

    public WindFarmDto createWindFarm(WindFarmDto windFarmDto) {
        WindFarm windFarm = windFarmMapper.toEntity(windFarmDto);
        WindFarm savedWindFarm = repository.save(windFarm);
        windFarmDto.setId(savedWindFarm.getId());
        return windFarmDto;
    }

}