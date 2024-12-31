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
        dto.setCapacityMW(entity.getCapacityMW());
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
        entity.setCapacityMW(dto.getCapacityMW());
        return entity;
    }

}
