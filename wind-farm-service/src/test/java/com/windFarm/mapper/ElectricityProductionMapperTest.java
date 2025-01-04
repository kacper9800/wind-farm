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
        // Act
        ElectricityProductionDto dto = mapper.toDto(null);

        // Assert
        assertThat(dto).isNull();
    }

    @Test
    void testToDto_ValidEntity_ShouldMapFieldsCorrectly() {
        // Arrange
        WindFarm windFarm = new WindFarm();
        windFarm.setId(1L);
        windFarm.setCapacityMW(100.0);

        ElectricityProduction entity = new ElectricityProduction();
        entity.setId(2L);
        entity.setWindFarm(windFarm);
        entity.setTimestamp(LocalDateTime.of(2025, 1, 1, 12, 0));
        entity.setElectricityProducedMW(50L);

        // Act
        ElectricityProductionDto dto = mapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getWindFarmId()).isEqualTo(1L);
        assertThat(dto.getCapacityFactor()).isEqualTo(0.5);
        assertThat(dto.getTimestamp()).isEqualTo("2025-01-01T12:00");
        assertThat(dto.getElectricityProducedMW()).isEqualTo(50.0);
    }

    @Test
    void testToEntity_NullDto_ShouldReturnNull() {
        // Act
        ElectricityProduction entity = mapper.toEntity(null);

        // Assert
        assertThat(entity).isNull();
    }

    @Test
    void testToEntity_ValidDto_ShouldMapFieldsCorrectly() {
        // Arrange
        ElectricityProductionDto dto = new ElectricityProductionDto();
        dto.setId(2L);
        dto.setWindFarmId(1L);
        dto.setTimestamp("2025-01-01T12:00:00");
        dto.setElectricityProducedMW(50.0);

        // Act
        ElectricityProduction entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(2L);
        assertThat(entity.getWindFarm()).isNotNull();
        assertThat(entity.getWindFarm().getId()).isEqualTo(1L);
        assertThat(entity.getTimestamp()).isEqualTo(LocalDateTime.of(2025, 1, 1, 12, 0));
        assertThat(entity.getElectricityProducedMW()).isEqualTo(50L);
    }

    @Test
    void testToEntity_InvalidTimestamp_ShouldThrowException() {
        // Arrange
        ElectricityProductionDto dto = new ElectricityProductionDto();
        dto.setTimestamp("invalid-timestamp");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> mapper.toEntity(dto));
    }

    @Test
    void testToEntity_EmptyTimestamp_ShouldNotSetTimestamp() {
        // Arrange
        ElectricityProductionDto dto = new ElectricityProductionDto();
        dto.setTimestamp("");

        // Act
        ElectricityProduction entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getTimestamp()).isNull();
    }

    @Test
    void testToEntity_WindFarmIdZero_ShouldNotSetWindFarm() {
        // Arrange
        ElectricityProductionDto dto = new ElectricityProductionDto();
        dto.setWindFarmId(0);

        // Act
        ElectricityProduction entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getWindFarm()).isNull();
    }
}
