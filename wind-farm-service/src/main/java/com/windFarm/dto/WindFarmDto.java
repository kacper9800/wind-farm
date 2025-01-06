package com.windFarm.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WindFarmDto {
    Long id;
    String description;
    String timezone;
    String location;
    Double capacityMw;
}
