package com.viepovsky.offer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.viepovsky.garage.model.Garage;
import com.viepovsky.offer.dto.CatalogOfferDto;
import com.viepovsky.offer.model.CatalogOffer;

import com.viepovsky.utility.mapper.CatalogOfferMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AvailableCarRepairMapperTest {

    private static final CatalogOfferMapper CATALOG_OFFER_MAPPER = new CatalogOfferMapper();

    @Test
    void mapToAvailableCarServiceDto() {
        //Given
        Garage mockedGarage = Mockito.mock(Garage.class);
        CatalogOffer availableCarRepair = Mockito.mock(CatalogOffer.class);
        //TODO fithis//new CatalogOffer(1L, "Testname", "Testdesc", BigDecimal.valueOf(50), 60, "BMW, AUDI", BigDecimal.valueOf(1.2), mockedGarage);
        when(mockedGarage.getId()).thenReturn(2L);
        //When
        CatalogOfferDto mappedService = CATALOG_OFFER_MAPPER.toCatalogDto(availableCarRepair);
        //Then
        assertEquals(1L, mappedService.getId());
        assertEquals("Testname", mappedService.getName());
        assertEquals(BigDecimal.valueOf(50), mappedService.getCost());
        assertEquals(2L, mappedService.getGarageId());
    }

    @Test
    void mapToAvailableCarService() {
        //Given
        CatalogOfferDto availableCarRepairDto = new CatalogOfferDto(1L, "Testname", "Testdesc", BigDecimal.valueOf(50), 60, "BMW, AUDI", BigDecimal.valueOf(1.2), 2L);
        //When
        CatalogOffer mappedService = CATALOG_OFFER_MAPPER.toCatalogOffer(availableCarRepairDto);
        //Then
        assertEquals(1L, mappedService.getId());
        assertEquals("Testname", mappedService.getName());
        assertEquals(BigDecimal.valueOf(50), mappedService.getPrice());
    }

    @Test
    void apToAvailableCarServiceDtoList() {
        //Given
        Garage mockedGarage = Mockito.mock(Garage.class);
        CatalogOffer availableCarRepair = Mockito.mock(CatalogOffer.class);//
        // new CatalogOffer(1L, "Testname", "Testdesc", BigDecimal.valueOf(50), 60, "BMW, AUDI", BigDecimal.valueOf(1.2), mockedGarage);
        CatalogOffer availableCarRepair2 = Mockito.mock(CatalogOffer.class);//
        //TODO fix this
        // new CatalogOffer(2L, "Testname", "Testdesc", BigDecimal.valueOf(50), 60, "BMW, AUDI", BigDecimal.valueOf(1.2), mockedGarage);
        //When
        List<CatalogOfferDto> mappedList = CATALOG_OFFER_MAPPER.toCatalogOffer(List.of(availableCarRepair, availableCarRepair2));
        //Then
        assertEquals(2, mappedList.size());
    }

}