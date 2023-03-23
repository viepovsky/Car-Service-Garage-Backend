package com.backend.service;

import com.backend.domain.AvailableCarService;
import com.backend.domain.Garage;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.AvailableCarServiceRepository;
import com.backend.repository.GarageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@DisplayName("Available Car Services Db Service Test Suite")
class AvailableCarServiceDbServiceTestSuite {

    @InjectMocks
    private AvailableCarServiceDbService availableCarServiceDbService;

    @Mock
    private AvailableCarServiceRepository availableCarServiceRepository;

    @Mock
    private GarageRepository garageRepository;

    @Test
    void testGetAllAvailableCarService() {
        //Given
        List<AvailableCarService> serviceList = new ArrayList<>();
        AvailableCarService availableCarService = Mockito.mock(AvailableCarService.class);
        serviceList.add(availableCarService);
        when(availableCarServiceRepository.findAllByGarageId(5L)).thenReturn(serviceList);
        //When
        List<AvailableCarService> retrievedServiceList = availableCarServiceDbService.getAllAvailableCarService(5L);
        //Then
        assertEquals(1, retrievedServiceList.size());
    }

    @Test
    void testSaveAvailableCarService() throws MyEntityNotFoundException {
        //Given
        Garage garage = new Garage("Test name", "Test address", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        AvailableCarService availableCarService = new AvailableCarService("Testname", "Testdescription", BigDecimal.valueOf(50), 40, "BMW", BigDecimal.valueOf(1.2), null);
        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));
        when(garageRepository.save(garage)).thenReturn(garage);
        //When
        availableCarServiceDbService.saveAvailableCarService(availableCarService, 1L);
        //Then
        verify(garageRepository, times(1)).save(garage);
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Garage", 1L));
    }

    @Test
    void testDeleteAvailableCarService() throws MyEntityNotFoundException {
        //Given
        AvailableCarService availableCarService = new AvailableCarService("Testname", "Testdescription", BigDecimal.valueOf(50), 40, "BMW", BigDecimal.valueOf(1.2), new Garage());
        when(availableCarServiceRepository.findById(1L)).thenReturn(Optional.of(availableCarService));
        doNothing().when(availableCarServiceRepository).deleteById(1L);
        //When
        availableCarServiceDbService.deleteAvailableCarService(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("AvailableCarService", 1L));
        verify(availableCarServiceRepository, times(1)).deleteById(1L);

    }

}