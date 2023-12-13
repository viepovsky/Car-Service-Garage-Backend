package com.viepovsky.garage.availablerepair;

import com.viepovsky.garage.Garage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailableCarRepairMapperTest {

    private static final AvailableCarRepairMapper availableServiceMapper = new AvailableCarRepairMapper();

    @Test
    void mapToAvailableCarServiceDto() {
        //Given
        Garage mockedGarage = Mockito.mock(Garage.class);
        AvailableCarRepair availableCarRepair = new AvailableCarRepair(1L, "Testname", "Testdesc", BigDecimal.valueOf(50), 60, "BMW, AUDI", BigDecimal.valueOf(1.2), mockedGarage);
        when(mockedGarage.getId()).thenReturn(2L);
        //When
        AvailableCarRepairDto mappedService = availableServiceMapper.mapToAvailableCarServiceDto(availableCarRepair);
        //Then
        assertEquals(1L, mappedService.getId());
        assertEquals("Testname", mappedService.getName());
        assertEquals(BigDecimal.valueOf(50), mappedService.getCost());
        assertEquals(2L, mappedService.getGarageId());
    }

    @Test
    void mapToAvailableCarService() {
        //Given
        AvailableCarRepairDto availableCarRepairDto = new AvailableCarRepairDto(1L, "Testname", "Testdesc", BigDecimal.valueOf(50), 60, "BMW, AUDI", BigDecimal.valueOf(1.2), 2L);
        //When
        AvailableCarRepair mappedService = availableServiceMapper.mapToAvailableCarService(availableCarRepairDto);
        //Then
        assertEquals(1L, mappedService.getId());
        assertEquals("Testname", mappedService.getName());
        assertEquals(BigDecimal.valueOf(50), mappedService.getCost());
    }

    @Test
    void apToAvailableCarServiceDtoList() {
        //Given
        Garage mockedGarage = Mockito.mock(Garage.class);
        AvailableCarRepair availableCarRepair = new AvailableCarRepair(1L, "Testname", "Testdesc", BigDecimal.valueOf(50), 60, "BMW, AUDI", BigDecimal.valueOf(1.2), mockedGarage);
        AvailableCarRepair availableCarRepair2 = new AvailableCarRepair(2L, "Testname", "Testdesc", BigDecimal.valueOf(50), 60, "BMW, AUDI", BigDecimal.valueOf(1.2), mockedGarage);
        //When
        List<AvailableCarRepairDto> mappedList = availableServiceMapper.mapToAvailableCarServiceDtoList(List.of(availableCarRepair, availableCarRepair2));
        //Then
        assertEquals(2, mappedList.size());
    }

}