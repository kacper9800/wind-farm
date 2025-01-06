package com.windFarm.mapper;

import com.windFarm.dto.WindFarmDto;
import com.windFarm.entity.WindFarm;
import org.springframework.stereotype.Component;

@Component
public class WindFarmMapper {
    public WindFarmDto toDto(WindFarm entity) {
        if (entity == null) {
            return null;
        }
        WindFarmDto dto = new WindFarmDto();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setTimezone(entity.getTimezone());
        dto.setLocation(entity.getLocation());
        if (entity.getCapacityMW() != null) {
            dto.setCapacityMw(entity.getCapacityMW().toString());
        }
        return dto;
    }

    public WindFarm toEntity(WindFarmDto dto) {
        if (dto == null) {
            return null;
        }
        WindFarm entity = new WindFarm();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setTimezone(dto.getTimezone());
        entity.setLocation(dto.getLocation());
        if (dto.getCapacityMw() != null) {
            entity.setCapacityMW(Double.valueOf(dto.getCapacityMw()));
        }
        return entity;
    }

}
