package com.viepovsky.vehicle;

import com.viepovsky.vehicle.dto.MakeDto;
import com.viepovsky.vehicle.dto.ModelDto;
import com.viepovsky.vehicle.dto.VehicleCreateRequest;
import com.viepovsky.vehicle.dto.VehicleDto;
import com.viepovsky.vehicle.dto.VehicleUpdateRequest;
import com.viepovsky.vehicle.model.EngineType;
import com.viepovsky.vehicle.model.Make;
import com.viepovsky.vehicle.model.Model;
import com.viepovsky.vehicle.model.Type;
import com.viepovsky.vehicle.model.Vehicle;

class VehicleTestData {
    public static final String TEST_USERNAME = "testuser";

    public VehicleCreateRequest getVehicleCreateRequest() {
        return VehicleCreateRequest.builder()
                .vin("VIN")
                .licensePlate("LICENSE_PLATE")
                .modelId(1L)
                .engineType(EngineType.DIESEL.name())
                .manufactured_year(2020)
                .details("SAMPLE_DETAILS")
                .build();
    }

    public VehicleUpdateRequest getVehicleUpdateRequest() {
        return VehicleUpdateRequest.builder()
                .vehicleId(1L)
                .vin("VIN")
                .licensePlate("LICENSE_PLATE")
                .modelId(1L)
                .engineType(EngineType.DIESEL.name())
                .manufactured_year(2020)
                .details("SAMPLE_DETAILS")
                .build();
    }

    public VehicleDto getVehicleDto() {
        MakeDto makeDto = new MakeDto(1L, "MAKE");
        ModelDto modelDto = new ModelDto(1L, "MODEL", makeDto, Type.SEDAN.name());
        return VehicleDto.builder()
                .vehicleId(1L)
                .vin("VIN")
                .licensePlate("LICENSE_PLATE")
                .vehicleModel(modelDto)
                .engineType(EngineType.DIESEL.name())
                .manufactured_year(2020)
                .details("SAMPLE_DETAILS")
                .build();
    }

    public Vehicle getVehicle() {
        Make make = new Make(1L, "MAKE");
        Model modelDto = new Model(1L, "MODEL", make, Type.SEDAN);
        return Vehicle.builder()
                .id(1L)
                .vin("VIN")
                .licensePlate("LICENSE_PLATE")
                .model(modelDto)
                .engineType(EngineType.DIESEL)
                .manufactured_year(2020)
                .details("SAMPLE_DETAILS")
                .build();
    }
}
