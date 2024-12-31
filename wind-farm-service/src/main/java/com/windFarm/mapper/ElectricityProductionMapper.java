package com.windFarm.mapper;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.entity.ElectricityProduction;
import com.windFarm.entity.WindFarm;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ElectricityProductionMapper {

    public ElectricityProductionDto toDto(ElectricityProduction entity) {
        if (entity == null) {
            return null;
        }

        ElectricityProductionDto dto = new ElectricityProductionDto();
        dto.setId(entity.getId());

        if (entity.getWindFarm() != null) {
            dto.setWindFarmId(entity.getWindFarm().getId());
        }

        dto.setTimestamp(entity.getTimestamp() != null ? entity.getTimestamp().toString() : null);
        dto.setElectricityProducedMW(entity.getElectricityProducedMW() != null ? entity.getElectricityProducedMW() : 0.0);

        return dto;
    }

    public ElectricityProduction toEntity(ElectricityProductionDto dto) {
        if (dto == null) {
            return null;
        }

        ElectricityProduction entity = new ElectricityProduction();

        entity.setId(dto.getId() != 0 ? dto.getId() : null);

        if (dto.getTimestamp() != null && !dto.getTimestamp().isEmpty()) {
            try {
                entity.setTimestamp(LocalDateTime.parse(dto.getTimestamp()));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid timestamp format: " + dto.getTimestamp(), e);
            }
        }

        entity.setElectricityProducedMW((long) dto.getElectricityProducedMW());

        if (dto.getWindFarmId() != 0) {
            WindFarm windFarm = new WindFarm();
            windFarm.setId(dto.getWindFarmId());
            entity.setWindFarm(windFarm);
        }

        return entity;
    }
}
