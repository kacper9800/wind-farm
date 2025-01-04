package com.windFarm.controller;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.dto.ElectricityProductionFilterDto;
import com.windFarm.service.ElectricityProductionService;
import com.windFarm.service.WindFarmService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/electricityProductions")
public class ElectricityProductionController {

    private final ElectricityProductionService electricityProductionService;

    public ElectricityProductionController(ElectricityProductionService electricityProductionService) {
        this.electricityProductionService = electricityProductionService;
    }

    @GetMapping
    public ResponseEntity<Page<ElectricityProductionDto>> getElectricityProductions(ElectricityProductionFilterDto filterDto) {
        Page<ElectricityProductionDto> electricityProductionDtoPage = electricityProductionService.getElectricityProduction(filterDto);
        return ResponseEntity.ok(electricityProductionDtoPage);
    }

    @GetMapping("/averageCapacityFactor")
    public ResponseEntity<Double> getAverageCapacityFactor(ElectricityProductionFilterDto filterDto) {
        Double averageCapacityFactor = electricityProductionService.getAverageCapacityFactor(filterDto);
        return ResponseEntity.ok(averageCapacityFactor);
    }



    @PostMapping
    public ResponseEntity<ElectricityProductionDto> createElectricityProduction(@Validated @RequestBody ElectricityProductionDto electricityProductionDto) {
        ElectricityProductionDto createdElectricityProduction = electricityProductionService.createElectricityProduction(electricityProductionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdElectricityProduction);
    }

}
