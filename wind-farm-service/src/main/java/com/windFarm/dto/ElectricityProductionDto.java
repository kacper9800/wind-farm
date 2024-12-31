package com.windFarm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ElectricityProductionDto {

    private long id;

    @JsonProperty("wind_farm_id")
    private long windFarmId;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("electricity_produced_mw")
    private double electricityProducedMW;

}
