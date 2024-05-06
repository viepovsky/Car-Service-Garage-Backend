package com.viepovsky.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.viepovsky.security.DataOwnershipValidator;
import com.viepovsky.utility.mapper.VehicleMapper;
import com.viepovsky.vehicle.dto.VehicleDto;
import com.viepovsky.vehicle.model.Vehicle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CarFacadeTest {
    @InjectMocks private VehicleFacade facade;

    @Mock private VehicleService service;

    @Mock private VehicleMapper mapper;

    @Mock private DataOwnershipValidator dataOwnershipValidator;

    @Test
    void shouldGetVehicles_ByUsername() {
        //Given
        List<Vehicle> vehicles = List.of(Mockito.mock(Vehicle.class));
        List<VehicleDto> vehicleDtos = List.of(Mockito.mock(VehicleDto.class));
        when(service.getVehiclesByUsername("username")).thenReturn(vehicles);
        when(mapper.toVehicleDtoList(vehicles)).thenReturn(vehicleDtos);
        //When
        List<VehicleDto> retrievedList = facade.getVehiclesByUsername("username");
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldCreateCar() {
        //Given
        VehicleDto mockedCarDto = Mockito.mock(VehicleDto.class);
        Vehicle mockedCar = Mockito.mock(Vehicle.class);
        when(mapper.toVehicle(mockedCarDto)).thenReturn(mockedCar);
        doNothing().when(service).saveVehicle(mockedCar, "username", vehicleDto.modelId());
        //When
        facade.createVehicle(mockedCarDto, "username");
        //Then
        verify(service, times(1)).saveVehicle(mockedCar, "username", vehicleDto.modelId());
    }

    @Test
    void shouldUpdateCar() {
        //Given
        VehicleDto mockedCarDto = Mockito.mock(VehicleDto.class);
        Vehicle mockedCar = Mockito.mock(Vehicle.class);
        when(mapper.mapToVehicle(mockedCarDto)).thenReturn(mockedCar);
        doNothing().when(service).updateVehicle(mockedCar, vehicle, vehicleDto.modelId());
        //When
        facade.updateVehicle(mockedCarDto);
        //Then
        verify(service, times(1)).updateVehicle(mockedCar, vehicle, vehicleDto.modelId());
    }

    @Test
    void shouldDeleteCar() {
        //Given
        doNothing().when(service).deleteVehicle(1L);
        //When
        facade.deleteVehicle(1L);
        //Then
        verify(service, times(1)).deleteVehicle(1L);
    }
}