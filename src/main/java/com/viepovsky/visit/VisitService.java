package com.viepovsky.visit;

import com.viepovsky.garage.GarageService;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.offer.CatalogOfferService;
import com.viepovsky.offer.SelectedOfferService;
import com.viepovsky.offer.model.CatalogOffer;
import com.viepovsky.offer.model.RepairStatus;
import com.viepovsky.offer.model.SelectedOffer;
import com.viepovsky.user.UserService;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.utility.exceptions.MyEntityNotFoundException;
import com.viepovsky.utility.exceptions.WrongInputDataException;
import com.viepovsky.vehicle.VehicleService;
import com.viepovsky.vehicle.model.Vehicle;
import com.viepovsky.visit.model.Visit;
import com.viepovsky.visit.model.VisitStatus;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitService.class);

    private final VisitRepository bookingRepository;

    private final GarageService garageService;

    private final SelectedOfferService carRepairService;

    private final VehicleService carService;

    private final UserService userService;

    private final CatalogOfferService availableCarRepairService;

    public List<Visit> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Visit> getAllBookingsByUsername(String username) {
        AppUser user = userService.getUser(username);
        return bookingRepository.findBookingsByCarRepairList(user.getId());
    }

    public List<Visit> getBookingsByDateAndGarageId(LocalDate date, Long garageId) {
        return bookingRepository.findBookingsByDateAndGarageId(date, garageId);
    }

    private Visit getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("Booking" + id));
    }

    public List<LocalTime> getAvailableBookingTimesByDayAndRepairDuration(LocalDate date, Long serviceId) {
        var carRepair = carRepairService.getCarRepair(serviceId);
        var reservedBooking = getBookingById(carRepair.getVisit().getId());
        int repairDuration = reservedBooking.getSelectedOffers()
                .stream()
                .mapToInt(SelectedOffer::getProbableRepairTime)
                .sum();
        Long garageId = reservedBooking.getGarage().getId();

        LOGGER.info("Given parameters to get available times, day: " + date + ", total repair time: " + repairDuration + ", garage id: " + garageId);
        List<Visit> allBookingsForDay = bookingRepository.findBookingsByDateAndGarageId(date, garageId);
        allBookingsForDay.remove(reservedBooking);

        List<LocalTime> availableBookingTimes = checkAvailableBookingTimes(allBookingsForDay, date, repairDuration);
        availableBookingTimes.remove(reservedBooking.getVisitStartTime());
        return availableBookingTimes;
    }

    public List<LocalTime> getAvailableBookingTimesByDayAndRepairDuration(LocalDate date, int repairDuration, Long garageId) {
        LOGGER.info("Given parameters to get available times, day: " + date + ", total repair time: " + repairDuration + ", garage id: " + garageId);
        List<Visit> bookingList = bookingRepository.findBookingsByDateAndGarageId(date, garageId);
        return checkAvailableBookingTimes(bookingList, date, repairDuration);
    }

    private List<LocalTime> checkAvailableBookingTimes(List<Visit> bookingList, LocalDate date, int repairDuration) {
        if (!isGarageWorkingHoursPresent(bookingList)) {
            return new ArrayList<>();
        }

        Visit garageWorkTime = bookingList.stream()
                                          .filter(booking -> booking.getStatus() == VisitStatus.AVAILABLE)
                                          .findFirst()
                                          .orElse(null);
        if (!isGarageWorkTimePresent(garageWorkTime)) {
            return new ArrayList<>();
        }

        LocalTime garageOpenTime = garageWorkTime.getVisitStartTime();
        LocalTime garageCloseTime = garageWorkTime.getVisitEndTime();
        if (isOpenTimeBeforeNow(date, garageOpenTime)) {
            if (isCloseTimeBeforeNow(garageCloseTime)) {
                return new ArrayList<>();
            }
            garageOpenTime = roundUpTimeToNearest10Minutes();
        }

        List<Visit> unavailableBookingTimeList = bookingList.stream()
                                                            .filter(booking -> booking.getStatus() == VisitStatus.UNAVAILABLE || booking.getStatus() == VisitStatus.WAITING_FOR_CUSTOMER)
                                                            .toList();

        return getAvailableTimesForBooking(repairDuration, garageCloseTime, unavailableBookingTimeList, garageOpenTime);
    }

    private boolean isGarageWorkingHoursPresent(List<Visit> bookingList) {
        return bookingList.size() != 0;
    }

    private boolean isGarageWorkTimePresent(Visit garageWorkTime) {
        return garageWorkTime != null;
    }

    private boolean isOpenTimeBeforeNow(LocalDate date, LocalTime openTime) {
        return (date.isEqual(LocalDate.now()) && LocalTime.now().isAfter(openTime));
    }

    private LocalTime roundUpTimeToNearest10Minutes() {
        LocalTime timeNow = LocalTime.now();
        int minutes = timeNow.getMinute() + timeNow.getHour() * 60;
        minutes = ((minutes + 10) / 10) * 10;
        return LocalTime.of(minutes / 60, minutes % 60);
    }

    private boolean isCloseTimeBeforeNow(LocalTime closeTime) {
        return closeTime.isBefore(LocalTime.now());
    }

    private List<LocalTime> getAvailableTimesForBooking(int repairDuration,
                                                        LocalTime closeTime,
                                                        List<Visit> unavailableBookingTimeList,
                                                        LocalTime currentTime) {
        List<LocalTime> availableBookingTimes = new ArrayList<>();
        while (!currentTime.plusMinutes(repairDuration).isAfter(closeTime)) {
            boolean isAvailable = true;
            for (Visit booking : unavailableBookingTimeList) {
                if (currentTime.plusMinutes(repairDuration).isAfter(booking.getVisitStartTime()) && booking.getVisitEndTime().isAfter(currentTime)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableBookingTimes.add(currentTime);
            }
            currentTime = currentTime.plusMinutes(10);
        }
        return availableBookingTimes;
    }

    public void createWorkingHoursBooking(LocalDate date,
                                          LocalTime startHour,
                                          LocalTime endHour,
                                          Long garageId) {
        Garage garage = garageService.getGarage(garageId);
        List<Visit> bookingList = bookingRepository.findBookingsByDateAndStatusAndGarageId(date, VisitStatus.AVAILABLE, garageId);
        if (!isGarageWorkingHoursPresent(bookingList)) {
            Visit booking = createWorkingHoursBooking(date, startHour, endHour, garage);
            bookingRepository.save(booking);
        } else {
            List<Long> bookingIdList = bookingList.stream()
                    .map(Visit::getId)
                    .toList();
            throw new WrongInputDataException("Work times of given day: " + date + ", are already declared. " +
                    "To change them you need to use PUT request or if there are more than one also DELETE request, check given booking id(s): " + bookingIdList);
        }
    }

    private Visit createWorkingHoursBooking(LocalDate date,
                                            LocalTime startHour,
                                            LocalTime endHour,
                                            Garage garage) {
        return new Visit(
                VisitStatus.AVAILABLE,
                date,
                startHour,
                endHour,
                BigDecimal.ZERO,
                new ArrayList<>(),
                garage
        );
    }

    public void updateBooking(Long bookingId, LocalDate date, LocalTime startHour) {
        Visit booking = bookingRepository.findById(bookingId)
                                         .orElseThrow(() -> new MyEntityNotFoundException("Booking" + bookingId));
        int repairTime = (int) Duration.between(booking.getVisitStartTime(), booking.getVisitEndTime()).toMinutes();
        booking.setVisitStartDate(date);
        booking.setVisitStartTime(startHour);
        booking.setVisitEndTime(startHour.plusMinutes(repairTime));
        LOGGER.info("Updated booking with values, day: " + date + ", time: " + startHour);
        bookingRepository.save(booking);
    }

    public void createBooking(List<Long> selectedCarRepairIdList,
                              LocalDate date,
                              LocalTime startHour,
                              Long garageId,
                              Long carId,
                              int repairDuration) {
        Garage garage = garageService.getGarage(garageId);
        Vehicle car = carService.getVehicle(carId);
        AppUser user = userService.getUser(car.getUser().getId());
        List<LocalTime> availableBookingTimes = getAvailableBookingTimesByDayAndRepairDuration(date, repairDuration, garageId);
        if (availableBookingTimes.contains(startHour)) {
            Visit booking = createCarRepairBooking(date, startHour, repairDuration, garage);
            bookingRepository.save(booking);
            saveBookingAndCarRepairsForCarAndUser(selectedCarRepairIdList, car, user, booking);
        } else {
            throw new WrongInputDataException("Given time: " + startHour + " is no longer available. Choose another day.");
        }
    }

    private Visit createCarRepairBooking(LocalDate date,
                                         LocalTime startHour,
                                         int repairDuration,
                                         Garage garage) {
        return new Visit(
                VisitStatus.WAITING_FOR_CUSTOMER,
                date,
                startHour,
                startHour.plusMinutes(repairDuration),
                BigDecimal.ZERO,
                new ArrayList<>(),
                garage
        );
    }

    private void saveBookingAndCarRepairsForCarAndUser(List<Long> selectedCarRepairIdList,
                                                       Vehicle car,
                                                       AppUser user,
                                                       Visit booking) {
        List<CatalogOffer> selectedAvailableCarRepairs = new ArrayList<>();
        List<BigDecimal> repairCosts = new ArrayList<>();
        selectedCarRepairIdList.stream()
                .map(id -> new CatalogOffer(availableCarRepairService.getById(id)))
                .peek(repair -> multiplyCarRepairCostIfCarIsPremiumMake(car, repair))
                .peek(selectedAvailableCarRepairs::add)
                .map(CatalogOffer::getPrice)
                .forEach(repairCosts::add);

        List<SelectedOffer> selectedCarRepairs = selectedAvailableCarRepairs.stream()
                                                                            .map(selectedService -> new SelectedOffer(
                                                                                    selectedService.getDescription(),
                        selectedService.getPrice(),
                        selectedService.getProbableRepairTime(),
                                                                                    user,
                        booking,
                        RepairStatus.AWAITING
                ))
                                                                            .toList();
//TODO fix it
//        user.getVehicles()
//                .stream()
//                .filter(servicedCar -> Objects.equals(servicedCar.getId(), car.getId()))
//                .findFirst()
//                .ifPresent(servicedCar -> servicedCar.getCarServicesList().addAll(selectedCarRepairs));
//        user.getServicesList().addAll(selectedCarRepairs);
        userService.saveUser(user);

        BigDecimal totalRepairCost = repairCosts.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        booking.setTotalPrice(totalRepairCost);
        booking.getSelectedOffers().addAll(selectedCarRepairs);
        bookingRepository.save(booking);
    }

    private void multiplyCarRepairCostIfCarIsPremiumMake(Vehicle car, CatalogOffer availableCarRepair) {
        //TODO fix it
//        if (availableCarRepair.getPremiumMakes().toLowerCase().contains(car.getMake().toLowerCase())) {
//            BigDecimal repairCost = availableCarRepair.getCost();
//            BigDecimal makeMultiplier = availableCarRepair.getMakeMultiplier();
//            repairCost = repairCost.multiply(makeMultiplier);
//            availableCarRepair.setCost(repairCost);
//        }
    }

    public void save(Visit booking) {
        bookingRepository.save(booking);
    }

    public void delete(Visit booking) {
        bookingRepository.delete(booking);
    }
}
