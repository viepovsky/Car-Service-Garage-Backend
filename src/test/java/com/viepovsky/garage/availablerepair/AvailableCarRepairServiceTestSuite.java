package com.viepovsky.garage.availablerepair;

import com.viepovsky.garage.Garage;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.GarageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Available Car Services Db Service Test Suite")
class AvailableCarRepairServiceTestSuite {

    @InjectMocks
    private AvailableCarRepairService availableCarRepairService;

    @Mock
    private AvailableCarRepairRepository availableCarRepairRepository;

    @Mock
    private GarageService garageService;

    @Test
    void testGetAllAvailableCarService() {
        //Given
        List<AvailableCarRepair> serviceList = new ArrayList<>();
        AvailableCarRepair availableCarRepair = Mockito.mock(AvailableCarRepair.class);
        serviceList.add(availableCarRepair);
        when(availableCarRepairRepository.findAllByGarageId(5L)).thenReturn(serviceList);
        //When
        List<AvailableCarRepair> retrievedServiceList = availableCarRepairService.getAllAvailableCarService(5L);
        //Then
        assertEquals(1, retrievedServiceList.size());
    }

    @Test
    void testSaveAvailableCarService() throws MyEntityNotFoundException {
        //Given
        Garage garage = new Garage("Test name", "Test address", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        AvailableCarRepair availableCarRepair = new AvailableCarRepair("Testname", "Testdescription", BigDecimal.valueOf(50), 40, "BMW", BigDecimal.valueOf(1.2), null);
        when(garageService.getGarage(anyLong())).thenReturn(garage);
        doNothing().when(garageService).saveGarage(any(Garage.class));
        //When
        availableCarRepairService.saveAvailableCarService(availableCarRepair, 1L);
        //Then
        verify(garageService, times(1)).saveGarage(any(Garage.class));
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Garage", 1L));
    }

    @Test
    void testDeleteAvailableCarService() throws MyEntityNotFoundException {
        //Given
        AvailableCarRepair availableCarRepair = new AvailableCarRepair("Testname", "Testdescription", BigDecimal.valueOf(50), 40, "BMW", BigDecimal.valueOf(1.2), new Garage());
        when(availableCarRepairRepository.findById(1L)).thenReturn(Optional.of(availableCarRepair));
        doNothing().when(availableCarRepairRepository).deleteById(1L);
        //When
        availableCarRepairService.deleteAvailableCarService(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("AvailableCarService", 1L));
        verify(availableCarRepairRepository, times(1)).deleteById(1L);

    }

}