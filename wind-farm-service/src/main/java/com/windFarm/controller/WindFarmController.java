package com.windFarm.controller;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.dto.WindFarmDto;
import com.windFarm.service.WindFarmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/windFarm")
public class WindFarmController {
        private final WindFarmService windFarmService;

        public WindFarmController(WindFarmService windFarmService) {
            this.windFarmService = windFarmService;
        }

        @GetMapping
        public List<WindFarmDto> getAllWindFarms() {
            return windFarmService.getAllWindFarms();
        }

        @GetMapping("/info")
        public WindFarmDto getWindFarmInfo(@RequestParam long id) {
           return windFarmService.getWindFarmInfo(id);
        }

        @GetMapping("/electricityProduction")
        public List<ElectricityProductionDto> getElectricityProduction(@RequestParam long windFarmId,
                                                                       @RequestParam(required = false) LocalDateTime filterFromDate,
                                                                       @RequestParam(required = false) LocalDateTime filterToDate) {
            return windFarmService.getElectricityProduction(windFarmId, filterFromDate, filterToDate);
        }

        @PostMapping
        public ResponseEntity.BodyBuilder createWindFarm(@RequestBody WindFarmDto windFarmDto) {
            this.windFarmService.createWindFarm(windFarmDto);
            return ResponseEntity.ok();
        }

    }