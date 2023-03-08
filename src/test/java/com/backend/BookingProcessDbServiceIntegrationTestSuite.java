package com.backend;

import com.backend.domain.*;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Entities Integration Test Suites")
public class BookingProcessDbServiceIntegrationTestSuite {
    @Autowired
    private GarageDbService garageDbService;

    @Autowired
    private GarageWorkTimeDbService garageWorkTimeDbService;

    @Autowired
    private AvailableCarServiceDbService availableCarServiceDbService;

    @Autowired
    private CustomerDbService customerDbService;

    @Autowired
    private UserDbService userDbService;

    @Test
    public void testBookingProcess() throws MyEntityNotFoundException {
        //Given
        initGarageWithWorkTimesAndAvailableCarServices();
        initCustomerAndUser();
        //When
        //Then
    }

    @Test
    public void testInitMethods() throws MyEntityNotFoundException {
        //Given
        initGarageWithWorkTimesAndAvailableCarServices();
        initCustomerAndUser();
        //When
        List<GarageWorkTime> garageWorkTimeList = garageWorkTimeDbService.getAllGarageWorkTimes();
        List<AvailableCarService> availableCarServiceList = availableCarServiceDbService.getAllAvailableCarService();

        List<Customer> customerList = customerDbService.getAllCustomers();
        List<User> userList = userDbService.getAllUsers();
        //Then
        assertEquals("Monday", garageWorkTimeList.get(0).getDay().getDayName());
        assertEquals(LocalTime.of(0,0), garageWorkTimeList.get(6).getEndHour());
        assertEquals("Garage test name", garageWorkTimeList.get(0).getGarage().getName());
        assertEquals(5, availableCarServiceList.size());
        assertEquals("Test car service #1", availableCarServiceList.get(0).getName());
        assertEquals("Garage test name", availableCarServiceList.get(0).getGarage().getName());

        assertEquals("Test name", customerList.get(0).getFirstName());
        assertEquals("Testusername", customerList.get(0).getUser().getUsername());
        assertEquals("Testpassword123", userList.get(0).getPassword());
        assertEquals("test@test.com", userList.get(0).getCustomer().getEmail());
    }

    private void initGarageWithWorkTimesAndAvailableCarServices() throws MyEntityNotFoundException {
        Garage garage = new Garage("Garage test name", "Garage test description");
        garageDbService.saveGarage(garage);
        garage = garageDbService.getAllGarages().get(0);

        GarageWorkTime workDay1 = new GarageWorkTime(WorkDays.MONDAY.toString(), LocalTime.of(7,0),LocalTime.of(15,0));
        GarageWorkTime workDay2 = new GarageWorkTime(WorkDays.TUESDAY.toString(), LocalTime.of(7,0),LocalTime.of(15,0));
        GarageWorkTime workDay3 = new GarageWorkTime(WorkDays.WEDNESDAY.toString(), LocalTime.of(7,0),LocalTime.of(15,0));
        GarageWorkTime workDay4 = new GarageWorkTime(WorkDays.THURSDAY.toString(), LocalTime.of(7,0),LocalTime.of(15,0));
        GarageWorkTime workDay5 = new GarageWorkTime(WorkDays.FRIDAY.toString(), LocalTime.of(7,0),LocalTime.of(15,0));
        GarageWorkTime workDay6 = new GarageWorkTime(WorkDays.SATURDAY.toString(), LocalTime.of(8,0),LocalTime.of(12,0));
        GarageWorkTime workDay7 = new GarageWorkTime(WorkDays.SUNDAY.toString(), LocalTime.of(0,0),LocalTime.of(0,0));
        garageWorkTimeDbService.saveGarageWorkTime(workDay1, garage.getId());
        garageWorkTimeDbService.saveGarageWorkTime(workDay2, garage.getId());
        garageWorkTimeDbService.saveGarageWorkTime(workDay3, garage.getId());
        garageWorkTimeDbService.saveGarageWorkTime(workDay4, garage.getId());
        garageWorkTimeDbService.saveGarageWorkTime(workDay5, garage.getId());
        garageWorkTimeDbService.saveGarageWorkTime(workDay6, garage.getId());
        garageWorkTimeDbService.saveGarageWorkTime(workDay7, garage.getId());

        AvailableCarService availableCarService1 = new AvailableCarService( "Test car service #1", "Test description car service #1", BigDecimal.valueOf(50), 40, "BMW, AUDI, MERCEDES", BigDecimal.valueOf(1.2));
        AvailableCarService availableCarService2 = new AvailableCarService( "Test car service #2", "Test description car service #2", BigDecimal.valueOf(170), 80, "BMW, AUDI, MERCEDES", BigDecimal.valueOf(1.2));
        AvailableCarService availableCarService3 = new AvailableCarService( "Test car service #3", "Test description car service #3", BigDecimal.valueOf(500), 110, "BMW, AUDI, MERCEDES", BigDecimal.valueOf(1.2));
        AvailableCarService availableCarService4 = new AvailableCarService( "Test car service #4", "Test description car service #4", BigDecimal.valueOf(40), 15, "BMW, AUDI, MERCEDES", BigDecimal.valueOf(1.2));
        AvailableCarService availableCarService5 = new AvailableCarService( "Test car service #5", "Test description car service #5", BigDecimal.valueOf(55), 30, "BMW, AUDI, MERCEDES", BigDecimal.valueOf(1.2));
        availableCarServiceDbService.saveAvailableCarService(availableCarService1, garage.getId());
        availableCarServiceDbService.saveAvailableCarService(availableCarService2, garage.getId());
        availableCarServiceDbService.saveAvailableCarService(availableCarService3, garage.getId());
        availableCarServiceDbService.saveAvailableCarService(availableCarService4, garage.getId());
        availableCarServiceDbService.saveAvailableCarService(availableCarService5, garage.getId());
    }

    private void initCustomerAndUser() throws MyEntityNotFoundException {
        Customer customer = new Customer("Test name", "Test lastname", "test@test.com", "+48777777777");
        customerDbService.saveCustomer(customer);
        customer = customerDbService.getAllCustomers().get(0);
        User user = new User("Testusername","Testpassword123");
        userDbService.saveUser(user, customer.getId());
    }
}
