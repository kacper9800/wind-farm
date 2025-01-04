package com.windFarm.service;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.dto.ElectricityProductionFilterDto;
import com.windFarm.entity.ElectricityProduction;
import com.windFarm.mapper.ElectricityProductionMapper;
import com.windFarm.repository.ElectricityProductionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ElectricityProductionService {

    private final ElectricityProductionMapper mapper;

    private final ElectricityProductionRepository repository;

    public ElectricityProductionService(ElectricityProductionMapper mapper, ElectricityProductionRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public ElectricityProductionDto createElectricityProduction(ElectricityProductionDto electricityProductionDto) {
        ElectricityProduction electricityProduction = mapper.toEntity(electricityProductionDto);
        ElectricityProduction savedElectricityProduction = repository.save(electricityProduction);
        electricityProductionDto.setId(savedElectricityProduction.getId());
        return electricityProductionDto;
    }

    public Page<ElectricityProductionDto> getElectricityProduction(ElectricityProductionFilterDto filterDto) {
        LocalDateTime fromDate, toDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        if (filterDto.getFromDate() != null) {
            ZonedDateTime zonedFromDateTime = ZonedDateTime.parse(filterDto.getFromDate(), formatter);
            fromDate = zonedFromDateTime.toLocalDateTime();
        } else {
            fromDate = LocalDateTime.of(2000, 9, 11, 22, 22, 22);
        }
        if (filterDto.getToDate() != null) {
            ZonedDateTime zonedToDateTime = ZonedDateTime.parse(filterDto.getToDate(), formatter);
            toDate = zonedToDateTime.toLocalDateTime();
        } else {
            toDate = LocalDateTime.of(2090, 9, 11, 22, 22, 22);
        }

        String targetTimezone = filterDto.getTimezone() != null
                ? filterDto.getTimezone()
                : "UTC";

        ZoneId targetZoneId;
        try {
            targetZoneId = ZoneId.of(targetTimezone);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid timezone: " + targetTimezone);
        }

        Pageable pageable = PageRequest.of(filterDto.getPageNumber(), filterDto.getPageSize());

        Page<ElectricityProduction> results = repository.findElectricityProductionByWindFarmIdAndTimestampBetween(
                filterDto.getWindFarmId(), fromDate, toDate, pageable);

        return results.map(electricityProduction -> {
            ZonedDateTime localTimestamp = electricityProduction.getTimestamp()
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(targetZoneId);

            ElectricityProductionDto dto = mapper.toDto(electricityProduction);
            dto.setTimestamp(localTimestamp.toString()); // np. 2025-01-04T12:00:00+01:00[Europe/Warsaw]
            return dto;
        });
    }

    public Double getAverageCapacityFactor(ElectricityProductionFilterDto filterDto) {
        LocalDateTime fromDate, toDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        if (filterDto.getFromDate() != null) {
            ZonedDateTime zonedFromDateTime = ZonedDateTime.parse(filterDto.getFromDate(), formatter);
            fromDate = zonedFromDateTime.toLocalDateTime();
        } else {
            fromDate = LocalDateTime.of(2000, 9, 11, 22, 22, 22);
        }
        if (filterDto.getToDate() != null) {
            ZonedDateTime zonedToDateTime = ZonedDateTime.parse(filterDto.getToDate(), formatter);
            toDate = zonedToDateTime.toLocalDateTime();
        } else {
            toDate = LocalDateTime.of(2090, 9, 11, 22, 22, 22);
        }
        return repository.getAverageElectricityProducedMW(filterDto.getWindFarmId(), fromDate, toDate);
    }
}
