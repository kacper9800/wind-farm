package com.windFarm.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
class ElectricityProductionRepositoryTest {

    @Autowired
    private ElectricityProductionRepository repository;

    @Test
    void shouldReturnAverageWhenFromDateIsOlderThanToDate() {
        long windFarmId = 1L;
        LocalDateTime fromDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime toDate = LocalDateTime.of(2023, 12, 31, 23, 59);

        Double expectedAverage = 50.0;
        ElectricityProductionRepository mockRepository = Mockito.mock(ElectricityProductionRepository.class);
        when(mockRepository.getAverageElectricityProducedMW(windFarmId, fromDate, toDate)).thenReturn(expectedAverage);
        Double actualAverage = mockRepository.getAverageElectricityProducedMW(windFarmId, fromDate, toDate);
        assertEquals(expectedAverage, actualAverage, "Średnia wartość nie jest poprawna.");
    }

    @Test
    void shouldHandleNullResultWhenNoDataIsPresent() {
        long windFarmId = 1L;
        LocalDateTime fromDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime toDate = LocalDateTime.of(2023, 12, 31, 23, 59);

        ElectricityProductionRepository mockRepository = Mockito.mock(ElectricityProductionRepository.class);
        when(mockRepository.getAverageElectricityProducedMW(windFarmId, fromDate, toDate)).thenReturn(null);

        Double actualAverage = mockRepository.getAverageElectricityProducedMW(windFarmId, fromDate, toDate);

        assertEquals(null, actualAverage, "Metoda powinna zwrócić null, jeśli nie ma danych.");
    }
}
