package com.windFarm.mapper;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.entity.ElectricityProduction;
import com.windFarm.entity.WindFarm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ElectricityProductionMapperTest {

    private ElectricityProductionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ElectricityProductionMapper();
    }

    @Test
    void testToDto_NullEntity_ShouldReturnNull() {
        ElectricityProductionDto dto = mapper.toDto(null);
        assertThat(dto).isNull();
    }

    @Test
    void testToDto_ValidEntity_ShouldMapFieldsCorrectly() {
        WindFarm windFarm = WindFarm.builder()
                .id(1L)
                .capacityMw(100.0)
                .build();

        ElectricityProduction entity = ElectricityProduction.builder()
                .id(2L)
                .windFarm(windFarm)
                .timestamp(LocalDateTime.of(2025, 1, 1, 12, 0))
                .electricityProducedMW(50.0)
                .build();
        ElectricityProductionDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getWindFarmId()).isEqualTo(1L);
        assertThat(dto.getCapacityFactor()).isEqualTo(0.5);
        assertThat(dto.getTimestamp()).isEqualTo("2025-01-01T12:00");
        assertThat(dto.getElectricityProducedMw()).isEqualTo(50.0);
    }

    @Test
    void testToEntity_NullDto_ShouldReturnNull() {
        ElectricityProduction entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }

    @Test
    void testToEntity_ValidDto_ShouldMapFieldsCorrectly() {
        ElectricityProductionDto dto = ElectricityProductionDto.builder()
                .id(2L)
                .windFarmId(1L)
                .timestamp("2025-01-01T12:00:00")
                .electricityProducedMw(50.0)
                .build();
        ElectricityProduction entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(2L);
        assertThat(entity.getWindFarm()).isNotNull();
        assertThat(entity.getWindFarm().getId()).isEqualTo(1L);
        assertThat(entity.getTimestamp()).isEqualTo(LocalDateTime.of(2025, 1, 1, 12, 0));
        assertThat(entity.getElectricityProducedMW()).isEqualTo(50L);
    }

    @Test
    void testToEntity_InvalidTimestamp_ShouldThrowException() {
        ElectricityProductionDto dto = ElectricityProductionDto.builder()
                .timestamp("invalid-timestamp").build();

        assertThrows(IllegalArgumentException.class, () -> mapper.toEntity(dto));
    }

    @Test
    void testToEntity_EmptyTimestamp_ShouldNotSetTimestamp() {
        ElectricityProductionDto dto = ElectricityProductionDto.builder()
                .timestamp("").build();

        ElectricityProduction entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getTimestamp()).isNull();
    }

    @Test
    void testToEntity_WindFarmIdZero_ShouldNotSetWindFarm() {
        ElectricityProductionDto dto = ElectricityProductionDto.builder()
                .windFarmId(0).build();

        ElectricityProduction entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getWindFarm()).isNull();
    }
}
