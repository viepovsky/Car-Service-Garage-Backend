package com.viepovsky.clients.car;

import com.viepovsky.scheduler.TimeKeeper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Car Api Service Test Suite")
class CarApiServiceTestSuite {
    @InjectMocks
    private CarApiService service;

    @Mock
    private CarApiClient client;

    @Mock
    private StoredCarApiRepository repository;

    @Mock
    private TimeKeeper timeKeeper;

    @Test
    void testGetCarMakes() {
        //Given
        List<String> makeList = List.of("AUDI", "BMW", "OPEL", "PEUGEOT");
        StoredCarApi storedCarApi = Mockito.mock(StoredCarApi.class);

        when(repository.findByDateFetched(LocalDate.now())).thenReturn(storedCarApi);
        when(storedCarApi.getCarMakesList()).thenReturn(makeList);
        //When
        List<String> retrievedList = service.getCarMakes();
        //Then
        assertEquals(4, retrievedList.size());
        assertEquals("BMW", retrievedList.get(1));
    }

    @Test
    void testGetCarTypes() {
        //Given
        List<String> typeList = List.of("Sedan", "Suv", "Hatchback", "Coupe");
        StoredCarApi storedCarApi = Mockito.mock(StoredCarApi.class);

        when(repository.findByDateFetched(LocalDate.now())).thenReturn(storedCarApi);
        when(storedCarApi.getCarTypesList()).thenReturn(typeList);
        //When
        List<String> retrievedList = service.getCarTypes();
        //Then
        assertEquals(4, retrievedList.size());
        assertEquals("Suv", retrievedList.get(1));
    }

    @Test
    void testGetCarYears() {
        //Given
        List<Integer> yearList = List.of(2022, 2021, 2020, 2019);
        StoredCarApi storedCarApi = Mockito.mock(StoredCarApi.class);

        when(repository.findByDateFetched(LocalDate.now())).thenReturn(storedCarApi);
        when(storedCarApi.getCarYearsList()).thenReturn(yearList);
        //When
        List<Integer> retrievedList = service.getCarYears();
        //Then
        assertEquals(4, retrievedList.size());
        assertEquals(2021, retrievedList.get(1));
    }

    @Test
    void testGetAndSaveCarYearsMakesTypes() throws InterruptedException {
        //Given
        List<String> makeList = List.of("AUDI", "BMW", "OPEL", "PEUGEOT");
        List<String> typeList = List.of("Sedan", "Suv", "Hatchback", "Coupe");
        List<Integer> yearList = List.of(2022, 2021, 2020, 2019);
        when(client.getCarMakes()).thenReturn(makeList);
        when(client.getCarTypes()).thenReturn(typeList);
        when(client.getCarYears()).thenReturn(yearList);

        LocalDate localDate = LocalDate.now();
        StoredCarApi mockedStoredCarApi = Mockito.mock(StoredCarApi.class);

        when(repository.save(any(StoredCarApi.class))).thenReturn(mockedStoredCarApi);
        //When
        service.getAndSaveCarYearsMakesTypes(localDate);
        //Then
        verify(repository, times(1)).save(any(StoredCarApi.class));
    }

    @Test
    void testGetCarModels() throws InterruptedException {
        //Given
        List<CarApiDto> carApiDto = List.of(new CarApiDto("A4"), new CarApiDto("A6"));
        when(client.getCarModels(2020, "AUDI", "Sedan")).thenReturn(carApiDto);
        //When
        List<String> retrievedModelList = service.getCarModels(2020, "AUDI", "Sedan");
        //Then
        assertEquals(2, retrievedModelList.size());
        assertEquals("A6", retrievedModelList.get(1));
    }
}