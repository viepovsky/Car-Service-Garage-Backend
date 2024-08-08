package com.viepovsky.garage;

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
class GarageFacadeTest {
    @InjectMocks
    private GarageFacade facade;

    @Mock
    private GarageService service;

    @Mock
    private GarageMapper mapper;

    @Test
    void shouldGetAllGarages() {
        //Given
        List<Garage> mockedGarageList = List.of(Mockito.mock(Garage.class));
        List<GarageDto> mockedGarageDtoList = List.of(Mockito.mock(GarageDto.class));
        when(service.getAllGarages()).thenReturn(mockedGarageList);
        when(mapper.mapToGarageDtoList(mockedGarageList)).thenReturn(mockedGarageDtoList);
        //When
        List<GarageDto> retrievedList = facade.getAllGarages();
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldGetGarage() {
        //Given
        var garage = new Garage();
        var responseGarage = new GarageDto();
        when(service.getGarage(anyLong())).thenReturn(garage);
        when(mapper.mapToGarageDto(any(Garage.class))).thenReturn(responseGarage);
        //When
        var retrievedGarage = facade.getGarage(5L);
        //Then
        assertNotNull(retrievedGarage);
    }

    @Test
    void shouldCreateGarage() {
        //Given
        GarageDto mockedGarageDto = Mockito.mock(GarageDto.class);
        Garage mockedGarage = Mockito.mock(Garage.class);
        when(mapper.mapToGarage(mockedGarageDto)).thenReturn(mockedGarage);
        when(service.saveGarage(any(Garage.class))).thenReturn(Mockito.mock(Garage.class));
        //When
        Garage createdGarage = facade.createGarage(mockedGarageDto);
        //Then
        assertNotNull(createdGarage);
        verify(service, times(1)).saveGarage(any(Garage.class));
    }

    @Test
    void shouldDeleteGarage() {
        //Given
        doNothing().when(service).deleteGarage(1L);
        //When
        facade.deleteGarage(1L);
        //Then
        verify(service, times(1)).deleteGarage(1L);
    }
}