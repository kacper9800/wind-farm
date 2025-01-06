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
        return WindFarmDto.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .timezone(entity.getTimezone())
                .location(entity.getLocation())
                .capacityMw(entity.getCapacityMw())
                .build();
    }

    public WindFarm toEntity(WindFarmDto dto) {
        if (dto == null) {
            return null;
        }
        return WindFarm.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .timezone(dto.getTimezone())
                .location(dto.getLocation())
                .capacityMw(dto.getCapacityMw())
                .build();
    }

}
