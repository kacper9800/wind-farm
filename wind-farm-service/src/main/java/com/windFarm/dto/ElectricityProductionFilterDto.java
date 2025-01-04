package com.windFarm.dto;

import lombok.Data;

@Data
public class ElectricityProductionFilterDto {
    private long windFarmId;
    private String fromDate;
    private String toDate;
    private Integer pageSize = 30;
    private Integer pageNumber = 0;
    private String timezone;
}
