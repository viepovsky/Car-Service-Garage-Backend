package com.viepovsky.offer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.viepovsky.garage.GarageService;
import com.viepovsky.garage.model.Address;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.offer.model.CatalogOffer;
import com.viepovsky.utility.exceptions.MyEntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("Available Car Services Db Service Tests")
class AvailableCarRepairServiceTest {

    @InjectMocks
    private CatalogOfferService availableCarRepairService;

    @Mock
    private CatalogOfferRepository catalogOfferRepository;

    @Mock
    private GarageService garageService;

    @Test
    void testGetAllAvailableCarService() {
        //Given
        List<CatalogOffer> serviceList = new ArrayList<>();
        CatalogOffer availableCarRepair = Mockito.mock(CatalogOffer.class);
        serviceList.add(availableCarRepair);
        when(catalogOfferRepository.findAllByGarageId(5L)).thenReturn(serviceList);
        //When
        List<CatalogOffer> retrievedServiceList = availableCarRepairService.getAllCatalogOffers(5L);
        //Then
        assertEquals(1, retrievedServiceList.size());
    }

    @Test
    void testSaveAvailableCarService() {
        //Given
        Garage garage = new Garage("Test name", Mockito.mock(Address.class), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        CatalogOffer availableCarRepair = Mockito.mock(CatalogOffer.class);
        //TODO: fix this new CatalogOffer("Testname", "Testdescription", BigDecimal.valueOf(50), 40, BigDecimal.valueOf(1.2), null);
        when(garageService.getGarage(anyLong())).thenReturn(garage);
        when(garageService.saveGarage(any(Garage.class))).thenReturn(garage);
        //When
        availableCarRepairService.save(availableCarRepair, 1L);
        //Then
        verify(garageService, times(1)).saveGarage(any(Garage.class));
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Garage", 1L));
    }

    @Test
    void testDeleteAvailableCarService() {
        //Given
        when(catalogOfferRepository.existsById(1L)).thenReturn(true);
        doNothing().when(catalogOfferRepository).deleteById(1L);
        //When
        availableCarRepairService.delete(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("AvailableCarService", 1L));
        verify(catalogOfferRepository, times(1)).deleteById(1L);

    }

}