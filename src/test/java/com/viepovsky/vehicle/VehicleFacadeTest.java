package com.viepovsky.vehicle;

import static com.viepovsky.vehicle.VehicleTestData.TEST_USERNAME;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.viepovsky.security.DataOwnershipValidator;
import com.viepovsky.utility.mapper.VehicleMapper;
import com.viepovsky.vehicle.dto.VehicleCreateRequest;
import com.viepovsky.vehicle.dto.VehicleDto;
import com.viepovsky.vehicle.dto.VehicleUpdateRequest;
import com.viepovsky.vehicle.model.Vehicle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class VehicleFacadeTest {
    @InjectMocks private VehicleFacade facade;
    @Mock private VehicleService service;
    @Mock private VehicleMapper mapper;
    @Mock private DataOwnershipValidator dataOwnershipValidator;
    private final VehicleTestData testData = new VehicleTestData();

    @Test
    void shouldGetVehicles_ByUsername() {
        // Given
        List<Vehicle> vehicles = List.of(testData.getVehicle());
        List<VehicleDto> vehicleDtos = List.of(testData.getVehicleDto());
        doNothing().when(dataOwnershipValidator).belongsToAuthenticatedUser(TEST_USERNAME);
        when(service.getVehiclesByUsername(TEST_USERNAME)).thenReturn(vehicles);
        when(mapper.toVehicleDtoList(vehicles)).thenReturn(vehicleDtos);
        // When
        List<VehicleDto> retrievedVehicles = facade.getVehiclesByUsername(TEST_USERNAME);
        // Then
        assertNotNull(retrievedVehicles);
        assertEquals(1, retrievedVehicles.size());
    }

    @Test
    void shouldCreateVehicle() {
        // Given
        VehicleCreateRequest vehicleCreateRequest = testData.getVehicleCreateRequest();
        Vehicle vehicleToCreate = testData.getVehicle();
        VehicleDto vehicleResponse = testData.getVehicleDto();

        doNothing().when(dataOwnershipValidator).belongsToAuthenticatedUser(TEST_USERNAME);
        when(mapper.toVehicle(vehicleCreateRequest)).thenReturn(vehicleToCreate);
        when(service.createVehicle(eq(vehicleToCreate), eq(TEST_USERNAME), anyLong()))
                .thenReturn(vehicleToCreate);
        when(mapper.toVehicleDto(vehicleToCreate)).thenReturn(vehicleResponse);
        // When
        VehicleDto retrievedResponse = facade.createVehicle(vehicleCreateRequest, TEST_USERNAME);
        // Then
        assertNotNull(retrievedResponse);
    }

    @Test
    void shouldUpdateVehicle() {
        // Given
        VehicleUpdateRequest vehicleUpdateRequest = testData.getVehicleUpdateRequest();
        Vehicle vehicleToUpdate = testData.getVehicle();

        when(service.getVehicle(anyLong())).thenReturn(vehicleToUpdate);
        doNothing().when(dataOwnershipValidator).belongsToAuthenticatedUser(vehicleToUpdate);
        when(mapper.toVehicle(vehicleUpdateRequest)).thenReturn(vehicleToUpdate);
        doNothing().when(service).updateVehicle(any(Vehicle.class), any(Vehicle.class), eq(vehicleUpdateRequest.vehicleId()));
        // When
        facade.updateVehicle(vehicleUpdateRequest);
        // Then
        verify(service, times(1)).updateVehicle(any(Vehicle.class), any(Vehicle.class), eq(vehicleUpdateRequest.vehicleId()));
    }

    @Test
    void shouldDeleteVehicle() {
        // Given
        Vehicle vehicleToDelete = testData.getVehicle();
        when(service.getVehicle(1L)).thenReturn(vehicleToDelete);
        doNothing().when(dataOwnershipValidator).belongsToAuthenticatedUser(vehicleToDelete);
        doNothing().when(service).deleteVehicle(1L);
        // When
        facade.deleteVehicle(1L);
        // Then
        verify(service, times(1)).deleteVehicle(1L);
    }
}
