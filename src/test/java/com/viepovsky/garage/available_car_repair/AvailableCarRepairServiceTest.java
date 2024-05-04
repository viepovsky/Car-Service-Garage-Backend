package com.viepovsky.garage.available_car_repair;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.Address;
import com.viepovsky.garage.Garage;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Available Car Services Db Service Tests")
class AvailableCarRepairServiceTest {

    @InjectMocks
    private AvailableCarRepairService availableCarRepairService;

    @Mock
    private AvailableCarRepairRepository availableCarRepairRepository;

    @Mock
    private GarageService garageService;

    @Test
    void testGetAllAvailableCarService() {
        //Given
        List<ServiceCatalog> serviceList = new ArrayList<>();
        ServiceCatalog availableCarRepair = Mockito.mock(ServiceCatalog.class);
        serviceList.add(availableCarRepair);
        when(availableCarRepairRepository.findAllByGarageId(5L)).thenReturn(serviceList);
        //When
        List<ServiceCatalog> retrievedServiceList = availableCarRepairService.getAllAvailableCarRepair(5L);
        //Then
        assertEquals(1, retrievedServiceList.size());
    }

    @Test
    void testSaveAvailableCarService() {
        //Given
        Garage garage = new Garage("Test name", Mockito.mock(Address.class), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ServiceCatalog availableCarRepair = new ServiceCatalog("Testname", "Testdescription", BigDecimal.valueOf(50), 40, "BMW", BigDecimal.valueOf(1.2), null);
        when(garageService.getGarage(anyLong())).thenReturn(garage);
        when(garageService.saveGarage(any(Garage.class))).thenReturn(garage);
        //When
        availableCarRepairService.saveAvailableCarRepair(availableCarRepair, 1L);
        //Then
        verify(garageService, times(1)).saveGarage(any(Garage.class));
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Garage", 1L));
    }

    @Test
    void testDeleteAvailableCarService() {
        //Given
        when(availableCarRepairRepository.existsById(1L)).thenReturn(true);
        doNothing().when(availableCarRepairRepository).deleteById(1L);
        //When
        availableCarRepairService.deleteAvailableCarRepair(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("AvailableCarService", 1L));
        verify(availableCarRepairRepository, times(1)).deleteById(1L);

    }

}