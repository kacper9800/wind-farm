package com.windFarm.dto;

import lombok.Data;

@Data
public class WindFarmDto {
    Long id;
    String description;
    String timezone;
    String location;
    String capacityMw;
}
