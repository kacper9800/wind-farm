package com.windFarm.mapper;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.entity.ElectricityProduction;
import com.windFarm.entity.WindFarm;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class ElectricityProductionMapper {

    public ElectricityProductionDto toDto(ElectricityProduction entity) {
        if (entity == null) {
            return null;
        }

        ElectricityProductionDto dto = ElectricityProductionDto.builder().id(entity.getId()).build();

        if (entity.getWindFarm() != null) {
            dto.setWindFarmId(entity.getWindFarm().getId());
            dto.setCapacityFactor(entity.getElectricityProducedMW() / entity.getWindFarm().getCapacityMw());
        }

        dto.setTimestamp(entity.getTimestamp() != null ? entity.getTimestamp().toString() : null);
        dto.setElectricityProducedMw(entity.getElectricityProducedMW() != null ? entity.getElectricityProducedMW() : 0.0);
        return dto;
    }

    public ElectricityProduction toEntity(ElectricityProductionDto dto) {
        if (dto == null) {
            return null;
        }

        ElectricityProduction entity = ElectricityProduction.builder()
                .id(dto.getId() != null ? dto.getId() : null)
                .electricityProducedMW(dto.getElectricityProducedMw())
                .build();

        if (dto.getTimestamp() != null && !dto.getTimestamp().isEmpty()) {
            try {
                entity.setTimestamp(LocalDateTime.parse(dto.getTimestamp()).atOffset(ZoneOffset.UTC).toLocalDateTime());
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid timestamp format: " + dto.getTimestamp(), e);
            }
        }


        if (dto.getWindFarmId() != 0) {
            WindFarm windFarm = WindFarm.builder().id(dto.getWindFarmId()).build();
            entity.setWindFarm(windFarm);
        }

        return entity;
    }
}
