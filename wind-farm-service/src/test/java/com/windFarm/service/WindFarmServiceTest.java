package com.windFarm.service;

import com.windFarm.dto.WindFarmDto;
import com.windFarm.entity.WindFarm;
import com.windFarm.mapper.WindFarmMapper;
import com.windFarm.repository.WindFarmRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WindFarmServiceTest {

    @Mock
    private WindFarmRepository repository;

    @Mock
    private WindFarmMapper windFarmMapper;

    @InjectMocks
    private WindFarmService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllWindFarms() {
        WindFarm windFarm = new WindFarm();
        windFarm.setId(1L);

        WindFarmDto dto = new WindFarmDto();
        dto.setId(1L);

        when(repository.findAll()).thenReturn(Collections.singletonList(windFarm));
        when(windFarmMapper.toDto(windFarm)).thenReturn(dto);

        List<WindFarmDto> result = service.getAllWindFarms();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(repository, times(1)).findAll();
        verify(windFarmMapper, times(1)).toDto(windFarm);
    }

    @Test
    void testGetWindFarmInfo_ExistingId() {
        WindFarm windFarm = new WindFarm();
        windFarm.setId(1L);

        WindFarmDto dto = new WindFarmDto();
        dto.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(windFarm));
        when(windFarmMapper.toDto(windFarm)).thenReturn(dto);

        WindFarmDto result = service.getWindFarmInfo(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).findById(1L);
        verify(windFarmMapper, times(1)).toDto(windFarm);
    }

    @Test
    void testGetWindFarmInfo_NonExistingId() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.getWindFarmInfo(1L));

        assertEquals("WindFarm with id 1 not found", exception.getMessage());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testCreateWindFarm() {
        WindFarmDto dto = new WindFarmDto();
        dto.setId(1L);
        dto.setLocation("50,99 34,88");

        WindFarm windFarm = new WindFarm();
        windFarm.setId(1L);
        windFarm.setLocation("50,99 34,88");

        when(windFarmMapper.toEntity(dto)).thenReturn(windFarm);

        service.createWindFarm(dto);

        verify(repository, times(1)).save(windFarm);
    }
}
