package com.windFarm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ElectricityProductionDto {

    private Long id;

    @JsonProperty("wind_farm_id")
    private long windFarmId;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("electricity_produced_mw")
    private double electricityProducedMw;

    @JsonProperty("capacity_factor")
    private double capacityFactor;
}
