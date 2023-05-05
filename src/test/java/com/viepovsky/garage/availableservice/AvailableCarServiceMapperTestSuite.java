package com.viepovsky.garage.availableservice;

import com.viepovsky.garage.availableservice.AvailableCarService;
import com.viepovsky.garage.Garage;
import com.viepovsky.garage.availableservice.AvailableCarServiceDto;
import com.viepovsky.garage.availableservice.AvailableCarServiceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailableCarServiceMapperTestSuite {

    private static final AvailableCarServiceMapper availableServiceMapper = new AvailableCarServiceMapper();

    @Test
    void mapToAvailableCarServiceDto() {
        //Given
        Garage mockedGarage = Mockito.mock(Garage.class);
        AvailableCarService availableCarService = new AvailableCarService(1L, "Testname", "Testdesc", BigDecimal.valueOf(50), 60, "BMW, AUDI", BigDecimal.valueOf(1.2), mockedGarage);
        when(mockedGarage.getId()).thenReturn(2L);
        //When
        AvailableCarServiceDto mappedService = availableServiceMapper.mapToAvailableCarServiceDto(availableCarService);
        //Then
        assertEquals(1L, mappedService.getId());
        assertEquals("Testname", mappedService.getName());
        assertEquals(BigDecimal.valueOf(50), mappedService.getCost());
        assertEquals(2L, mappedService.getGarageId());
    }

    @Test
    void mapToAvailableCarService() {
        //Given
        AvailableCarServiceDto availableCarServiceDto = new AvailableCarServiceDto(1L, "Testname", "Testdesc", BigDecimal.valueOf(50), 60, "BMW, AUDI", BigDecimal.valueOf(1.2), 2L);
        //When
        AvailableCarService mappedService = availableServiceMapper.mapToAvailableCarService(availableCarServiceDto);
        //Then
        assertEquals(1L, mappedService.getId());
        assertEquals("Testname", mappedService.getName());
        assertEquals(BigDecimal.valueOf(50), mappedService.getCost());
    }

    @Test
    void apToAvailableCarServiceDtoList() {
        //Given
        Garage mockedGarage = Mockito.mock(Garage.class);
        AvailableCarService availableCarService = new AvailableCarService(1L, "Testname", "Testdesc", BigDecimal.valueOf(50), 60, "BMW, AUDI", BigDecimal.valueOf(1.2), mockedGarage);
        AvailableCarService availableCarService2 = new AvailableCarService(2L, "Testname", "Testdesc", BigDecimal.valueOf(50), 60, "BMW, AUDI", BigDecimal.valueOf(1.2), mockedGarage);
        //When
        List<AvailableCarServiceDto> mappedList = availableServiceMapper.mapToAvailableCarServiceDtoList(List.of(availableCarService, availableCarService2));
        //Then
        assertEquals(2, mappedList.size());
    }

}