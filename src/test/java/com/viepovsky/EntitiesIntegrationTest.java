package com.viepovsky;


//@SpringBootTest
//@MockBean(ApplicationScheduler.class)
//@Transactional
//@DisplayName("Entities Integration Tests")
public class EntitiesIntegrationTest {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private GarageRepository garageRepository;
//
//    @Autowired
//    private CarServiceRepository carServiceRepository;
//
//    @Autowired
//    private CarRepository carRepository;
//
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    @Test
//    public void testBookingServiceForNewUser(){
//        //Given
//        User user = new User("Oskar", "Raj", "testmail@gmail.com", "+48756756756", "testusername", "testpassword", UserRole.USER, LocalDateTime.now(), new ArrayList<>(), new ArrayList<>());
//        userRepository.save(user);
//        user = userRepository.findAll().get(0);
//
//        Car car1 = new Car("BMW", "3", "Sedan", 2020, "diesel");
//        Car car2 = new Car("BMW", "5","Sedan", 2005, "diesel");
//        car1.setUser(user);
//        car2.setUser(user);
//        user.getCarList().add(car1);
//        user.getCarList().add(car2);
//        userRepository.save(user);
//        user = userRepository.findAll().get(0);
//        car1 = carRepository.findAll().get(0);
//
//        Garage garage = new Garage("Speed Garage", "Garage address", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//        garageRepository.save(garage);
//        Garage garageToUpdate = garageRepository.findAll().get(0);
//
//        GarageWorkTime garageWorkTime1 = new GarageWorkTime(WorkDays.MONDAY, LocalTime.of(8, 0), LocalTime.of(16, 0), garageToUpdate);
//        GarageWorkTime garageWorkTime2 = new GarageWorkTime(WorkDays.TUESDAY, LocalTime.of(8, 0), LocalTime.of(16, 0), garageToUpdate);
//        garageToUpdate.getGarageWorkTimeList().add(garageWorkTime1);
//        garageToUpdate.getGarageWorkTimeList().add(garageWorkTime2);
//        AvailableCarService availableCarService1 = new AvailableCarService("Oil change", "Description of oil change", BigDecimal.valueOf(50), 30, "BMW, AUDI", BigDecimal.valueOf(1.2), garageToUpdate);
//        AvailableCarService availableCarService2 = new AvailableCarService("Suspension check", "Description of suspension check", BigDecimal.valueOf(30), 10, "BMW, AUDI", BigDecimal.valueOf(1.2), garageToUpdate);
//        garageToUpdate.getAvailableCarServiceList().add(availableCarService1);
//        garageToUpdate.getAvailableCarServiceList().add(availableCarService2);
//        garageRepository.save(garageToUpdate);
//        garage = garageRepository.findAll().get(0);
//
//        CarService carService = new CarService(availableCarService1.getName(), availableCarService1.getDescription(), availableCarService1.getCost(), availableCarService1.getRepairTimeInMinutes(), null, null, null, null);
//        carService.setCar(car1);
//        carService.setUser(user);
//        carServiceRepository.save(carService);
//        carService = carServiceRepository.findAll().get(0);
//
//        Booking booking = new Booking(BookingStatus.WAITING_FOR_CUSTOMER, LocalDate.of(2023,3,20),LocalTime.of(8,0),LocalTime.of(8,30), LocalDateTime.now(),BigDecimal.valueOf(0), new ArrayList<>(), null);
//        booking.getCarServiceList().add(carService);
//        booking.setGarage(garage);
//        booking.setTotalCost(carService.getCost());
//        bookingRepository.save(booking);
//        booking = bookingRepository.findAll().get(0);
//
//        carService.setBooking(booking);
//        carServiceRepository.save(carService);
//        carService = carServiceRepository.findAll().get(0);
//
//        user.getServicesList().add(carService);
//        userRepository.save(user);
//
//        car1.getCarServicesList().add(carService);
//        carRepository.save(car1);
//
//        garage.getBookingList().add(booking);
//        garageRepository.save(garage);
//
//        //When
//        User retrievedUser = userRepository.findAll().get(0);
//        CarService retrievedCarService = carServiceRepository.findAll().get(0);
//        Garage retrievedGarage = garageRepository.findAll().get(0);
//        Booking retrievedBooking = bookingRepository.findAll().get(0);
//
//        //Then
//        assertEquals(user.getFirstName(), retrievedUser.getFirstName());
//        assertEquals("BMW", retrievedUser.getCarList().get(0).getMake());
//        assertEquals(30, retrievedUser.getServicesList().get(0).getRepairTimeInMinutes());
//        assertEquals(30, retrievedUser.getCarList().get(0).getCarServicesList().get(0).getRepairTimeInMinutes());
//        assertEquals(LocalTime.of(8,30), retrievedUser.getCarList().get(0).getCarServicesList().get(0).getBooking().getEndHour());
//        assertEquals("Speed Garage", retrievedUser.getCarList().get(0).getCarServicesList().get(0).getBooking().getGarage().getName());
//        assertEquals(carService.getName(), retrievedCarService.getName());
//        assertEquals(carService.getBooking(), retrievedCarService.getBooking());
//        assertEquals(carService.getBooking().getGarage(), retrievedCarService.getBooking().getGarage());
//        assertEquals("Speed Garage", retrievedGarage.getName());
//        assertEquals(BookingStatus.WAITING_FOR_CUSTOMER, retrievedGarage.getBookingList().get(0).getStatus());
//        assertEquals(30, retrievedGarage.getBookingList().get(0).getCarServiceList().get(0).getRepairTimeInMinutes());
//        assertEquals(BookingStatus.WAITING_FOR_CUSTOMER, retrievedBooking.getStatus());
//        assertEquals(30, retrievedBooking.getCarServiceList().get(0).getRepairTimeInMinutes());
//    }
}
