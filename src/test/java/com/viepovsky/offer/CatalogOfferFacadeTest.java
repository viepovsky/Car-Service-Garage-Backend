package com.viepovsky.offer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.viepovsky.offer.dto.CatalogOfferDto;
import com.viepovsky.offer.model.CatalogOffer;

import com.viepovsky.utility.mapper.CatalogOfferMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CatalogOfferFacadeTest {
    @InjectMocks
    private CatalogOfferFacade facade;

    @Mock
    private CatalogOfferService service;

    @Mock
    private CatalogOfferMapper mapper;

    @Test
    void shouldGetAvailableCarServices() {
        //Given
        List<CatalogOffer> carServiceList = List.of(Mockito.mock(CatalogOffer.class));
        List<CatalogOfferDto> carServiceDtoList = List.of(Mockito.mock(CatalogOfferDto.class));
        when(service.getAllAvailableCarRepair(1L)).thenReturn(carServiceList);
        when(mapper.mapToAvailableCarServiceDtoList(carServiceList)).thenReturn(carServiceDtoList);
        //When
        List<CatalogOfferDto> retrievedList = facade.getAvailableCarServices(1L);
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldCreateAvailableCarService() {
        //Given
        CatalogOfferDto serviceDto = Mockito.mock(CatalogOfferDto.class);
        CatalogOffer service = Mockito.mock(CatalogOffer.class);
        when(mapper.mapToAvailableCarService(serviceDto)).thenReturn(service);
        doNothing().when(this.service).saveAvailableCarRepair(service, 1L);
        //When
        facade.createAvailableCarService(serviceDto, 1L);
        //Then
        verify(this.service, times(1)).saveAvailableCarRepair(service, 1L);
    }

    @Test
    void shouldDeleteAvailableCarService() {
        //Given
        doNothing().when(service).deleteAvailableCarRepair(1L);
        //When
        facade.deleteAvailableCarService(1L);
        //Then
        verify(service, times(1)).deleteAvailableCarRepair(1L);
    }
}