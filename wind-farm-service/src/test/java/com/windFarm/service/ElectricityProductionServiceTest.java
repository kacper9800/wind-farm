package com.windFarm.service;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.dto.ElectricityProductionFilterDto;
import com.windFarm.entity.ElectricityProduction;
import com.windFarm.mapper.ElectricityProductionMapper;
import com.windFarm.repository.ElectricityProductionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ElectricityProductionServiceTest {

    @Mock
    private ElectricityProductionMapper mapper;

    @Mock
    private ElectricityProductionRepository repository;

    @InjectMocks
    private ElectricityProductionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateElectricityProduction() {
        ElectricityProductionDto dto = ElectricityProductionDto.builder()
                .id(1L)
                .timestamp("2025-01-04T12:00:00+01:00[Europe/Warsaw]")
                .build();

        ElectricityProduction entity = ElectricityProduction.builder()
                .id(1L)
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);

        ElectricityProductionDto result = service.createElectricityProduction(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testGetElectricityProduction() {
        ElectricityProductionFilterDto filterDto = new ElectricityProductionFilterDto();
        filterDto.setFromDate("2025-01-01T00:00:00.000Z");
        filterDto.setToDate("2025-01-02T00:00:00.000Z");
        filterDto.setTimezone("Europe/Warsaw");
        filterDto.setWindFarmId(1L);
        filterDto.setPageNumber(0);
        filterDto.setPageSize(10);

        ElectricityProduction entity = ElectricityProduction
                .builder()
                .id(1L)
                .timestamp(LocalDateTime.of(2025, 1, 1, 12, 0))
                .build();

        Page<ElectricityProduction> page = new PageImpl<>(Collections.singletonList(entity));
        when(repository.findElectricityProduction(
                eq(1L), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(page);

        ElectricityProductionDto dto = ElectricityProductionDto.builder()
                .id(1L)
                .timestamp("2025-01-01T13:00:00+01:00[Europe/Warsaw]").build();
        when(mapper.toDto(entity)).thenReturn(dto);

        Page<ElectricityProductionDto> result = service.getElectricityProduction(filterDto);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals("2025-01-01T13:00+01:00[Europe/Warsaw]", result.getContent().get(0).getTimestamp());
        verify(repository, times(1)).findElectricityProduction(
                eq(1L), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class));
    }

    @Test
    void testGetElectricityProduction_SummerTime() {
        ElectricityProductionFilterDto filterDto = new ElectricityProductionFilterDto();
        filterDto.setFromDate("2025-06-01T00:00:00.000Z");
        filterDto.setToDate("2025-06-02T00:00:00.000Z");
        filterDto.setTimezone("Europe/Warsaw");
        filterDto.setWindFarmId(1L);
        filterDto.setPageNumber(0);
        filterDto.setPageSize(10);

        ElectricityProduction entity = ElectricityProduction.builder()
                .id(1L)
                .timestamp(LocalDateTime.of(2025, 6, 1, 12, 0))
                .build();

        Page<ElectricityProduction> page = new PageImpl<>(Collections.singletonList(entity));
        when(repository.findElectricityProduction(
                eq(1L), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(page);

        ElectricityProductionDto dto = ElectricityProductionDto.builder()
                .id(1L)
                .timestamp("2025-06-01T14:00:00+02:00[Europe/Warsaw]")
                .build();
        when(mapper.toDto(entity)).thenReturn(dto);

        Page<ElectricityProductionDto> result = service.getElectricityProduction(filterDto);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("2025-06-01T14:00+02:00[Europe/Warsaw]", result.getContent().get(0).getTimestamp());
    }

    @Test
    void testGetAverageCapacityFactor() {
        ElectricityProductionFilterDto filterDto = new ElectricityProductionFilterDto();
        filterDto.setFromDate("2025-01-01T00:00:00.000Z");
        filterDto.setToDate("2025-01-02T00:00:00.000Z");
        filterDto.setWindFarmId(1L);

        when(repository.getAverageElectricityProducedMW(eq(1L), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(50.0);

        Double result = service.getAverageCapacityFactor(filterDto);

        assertNotNull(result);
        assertEquals(50.0, result);
        verify(repository, times(1)).getAverageElectricityProducedMW(eq(1L), any(LocalDateTime.class), any(LocalDateTime.class));
    }
}
