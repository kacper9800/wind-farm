package com.windFarm.mapper;

import com.windFarm.dto.WindFarmDto;
import com.windFarm.entity.WindFarm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WindFarmMapperTest {

    private WindFarmMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new WindFarmMapper();
    }

    @Test
    void testToDto_NullEntity_ShouldReturnNull() {
        WindFarmDto dto = mapper.toDto(null);

        assertThat(dto).isNull();
    }

    @Test
    void testToDto_ValidEntity_ShouldMapFieldsCorrectly() {
        WindFarm entity = WindFarm.builder()
                .id(1L)
                .description("Wind farm description")
                .timezone("Europe/Warsaw")
                .location("Poland")
                .capacityMw(100.0).build();

        WindFarmDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getDescription()).isEqualTo("Wind farm description");
        assertThat(dto.getTimezone()).isEqualTo("Europe/Warsaw");
        assertThat(dto.getLocation()).isEqualTo("Poland");
        assertThat(dto.getCapacityMw()).isEqualTo(100.0);
    }

    @Test
    void testToEntity_NullDto_ShouldReturnNull() {
        WindFarm entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }

    @Test
    void testToEntity_ValidDto_ShouldMapFieldsCorrectly() {
        WindFarmDto dto = WindFarmDto.builder()
                .id(1L)
                .description("Wind farm description")
                .timezone("Europe/Warsaw")
                .location("Poland")
                .capacityMw(100.0).build();


        WindFarm entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getDescription()).isEqualTo("Wind farm description");
        assertThat(entity.getTimezone()).isEqualTo("Europe/Warsaw");
        assertThat(entity.getLocation()).isEqualTo("Poland");
        assertThat(entity.getCapacityMw()).isEqualTo(100.0);
    }
}
