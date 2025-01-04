package com.windFarm.controller;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.dto.ElectricityProductionFilterDto;
import com.windFarm.dto.WindFarmDto;
import com.windFarm.service.WindFarmService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/windFarms")
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


//        public Page<ElectricityProductionDto> getElectricityProduction(@RequestParam long windFarmId,
//                                                                       @RequestParam(required = false) LocalDateTime fromDate,
//                                                                       @RequestParam(required = false) LocalDateTime toDate,
//                                                                       @RequestParam(required = false, defaultValue = "30") Integer pageSize,
//                                                                       @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
//            return windFarmService.getElectricityProduction(windFarmId, fromDate, toDate, pageSize, pageNumber);
//        }



        @PostMapping
        public void createWindFarm(@RequestBody WindFarmDto windFarmDto) {
            this.windFarmService.createWindFarm(windFarmDto);
        }

    }