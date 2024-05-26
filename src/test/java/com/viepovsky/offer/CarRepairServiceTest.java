package com.viepovsky.offer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.viepovsky.offer.model.RepairStatus;
import com.viepovsky.offer.model.SelectedOffer;
import com.viepovsky.user.UserService;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.utility.exceptions.MyEntityNotFoundException;
import com.viepovsky.visit.VisitService;
import com.viepovsky.visit.model.Visit;
import com.viepovsky.visit.model.VisitStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Car Service Db Service Tests")
class CarRepairServiceTest {
    @InjectMocks
    private SelectedOfferService carRepairService;

    @Mock
    private SelectedOfferRepository selectedOfferRepository;

    @Mock
    private UserService userService;

    @Mock
    private VisitService bookingService;

    @Test
    void testGetCarRepairs() {
        //Given
        List<SelectedOffer> carRepairList = new ArrayList<>();
        SelectedOffer mockedCarRepair = Mockito.mock(SelectedOffer.class);
        carRepairList.add(mockedCarRepair);
        AppUser mockedUser = Mockito.mock(AppUser.class);
        when(userService.getUser(anyString())).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(1L);
        //TODO fix it
//        when(carRepairRepository.findAllOfferSelected(1L)).thenReturn(carRepairList);
        //When
        List<SelectedOffer> retrievedCarRepairList = carRepairService.getAllSelectedOffers("username");
        //Then
        assertEquals(1, retrievedCarRepairList.size());
    }

    @Test
    void shouldGetCarRepair() {
        //Given
        var carRepair = new SelectedOffer();
        when(selectedOfferRepository.findById(anyLong())).thenReturn(Optional.of(carRepair));
        //When
        var retrievedCarRepair = carRepairService.getById(5L);
        //Then
        assertNotNull(retrievedCarRepair);
    }

    @Test
    void testDeleteCarServiceMoreThanOneService() {
        //Given
        Visit booking = new Visit(VisitStatus.WAITING_FOR_CUSTOMER, LocalDate.now().plusDays(2), LocalTime.of(10, 0), LocalTime.of(12, 0), BigDecimal.valueOf(300), new ArrayList<>(), null);
        SelectedOffer carRepair = new SelectedOffer("Testdescription", BigDecimal.valueOf(50), 30, null, booking, RepairStatus.AWAITING);
        SelectedOffer carRepair2 = new SelectedOffer("Testdescription2", BigDecimal.valueOf(250), 90, null, booking, RepairStatus.AWAITING);
        booking.getSelectedOffers().add(carRepair);
        booking.getSelectedOffers().add(carRepair2);

        when(selectedOfferRepository.findById(1L)).thenReturn(Optional.of(carRepair));
        doNothing().when(selectedOfferRepository).delete(carRepair);
        doNothing().when(bookingService).save(any(Visit.class));
        //When
        carRepairService.delete(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("CarService", 1L));
        assertEquals(1, booking.getSelectedOffers().size());
        assertEquals(BigDecimal.valueOf(250), booking.getTotalPrice());
        assertEquals(LocalTime.of(10, 0), booking.getVisitStartTime());
        assertEquals(LocalTime.of(11, 30), booking.getVisitEndTime());
        verify(selectedOfferRepository, times(1)).delete(carRepair);
        verify(bookingService, times(1)).save(any(Visit.class));
    }

    @Test
    void testDeleteCarServiceOnlyOneService() {
        //Given
        Visit booking = new Visit(VisitStatus.WAITING_FOR_CUSTOMER, LocalDate.now().plusDays(2), LocalTime.of(10, 0), LocalTime.of(10, 30), BigDecimal.valueOf(50), new ArrayList<>(), null);
        SelectedOffer carRepair = new SelectedOffer("Testdescription", BigDecimal.valueOf(50), 30, null, booking, RepairStatus.AWAITING);
        booking.getSelectedOffers().add(carRepair);

        when(selectedOfferRepository.findById(1L)).thenReturn(Optional.of(carRepair));
        doNothing().when(selectedOfferRepository).delete(carRepair);
        doNothing().when(bookingService).delete(booking);
        //When
        carRepairService.delete(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("CarService", 1L));
        verify(selectedOfferRepository, times(1)).delete(carRepair);
        verify(bookingService, times(1)).delete(booking);
    }
}