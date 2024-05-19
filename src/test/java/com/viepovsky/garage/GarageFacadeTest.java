package com.viepovsky.garage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.viepovsky.garage.dto.GarageCreateRequest;
import com.viepovsky.garage.dto.GarageDto;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.utility.mapper.GarageMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
        when(mapper.toGarageDtoList(mockedGarageList)).thenReturn(mockedGarageDtoList);
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
        var responseGarage = Mockito.mock(GarageDto.class);
        when(service.getGarage(anyLong())).thenReturn(garage);
        when(mapper.toGarageDto(any(Garage.class))).thenReturn(responseGarage);
        //When
        var retrievedGarage = facade.getGarage(5L);
        //Then
        assertNotNull(retrievedGarage);
    }

    @Test
    void shouldCreateGarage() {
        //Given
        GarageCreateRequest mockedGarageDto = Mockito.mock(GarageCreateRequest.class);
        Garage mockedGarage = Mockito.mock(Garage.class);
        when(mapper.toGarage(mockedGarageDto)).thenReturn(mockedGarage);
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