package com.viepovsky.car_repair;

import com.viepovsky.mapper.OfferSelectedMapper;
import com.viepovsky.offer.OfferSelectedFacade;
import com.viepovsky.offer.SelectedOfferService;
import com.viepovsky.offer.dto.SelectedOfferDto;
import com.viepovsky.offer.model.SelectedOffer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarRepairFacadeTest {
    @InjectMocks
    private OfferSelectedFacade facade;

    @Mock
    private SelectedOfferService service;

    @Mock
    private OfferSelectedMapper mapper;

    @Test
    void shouldGetCarServices() {
        //Given
        List<SelectedOffer> mockedCarRepairList = List.of(Mockito.mock(SelectedOffer.class));
        List<SelectedOfferDto> mockedCarRepairDtoList = List.of(Mockito.mock(SelectedOfferDto.class));
        when(service.getCarRepairs("username")).thenReturn(mockedCarRepairList);
        when(mapper.mapToCarServiceDtoList(mockedCarRepairList)).thenReturn(mockedCarRepairDtoList);
        //When
        List<SelectedOfferDto> retrievedList = facade.getCarRepairs("username");
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldDeleteCarService() {
        //Given
        doNothing().when(service).deleteCarRepair(1L);
        //When
        facade.deleteCarRepair(1L);
        //Then
        verify(service, times(1)).deleteCarRepair(1L);
    }

}