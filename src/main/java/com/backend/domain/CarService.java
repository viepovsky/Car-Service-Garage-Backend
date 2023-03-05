package com.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARS_SERVICES")
public class CarService {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost")
    private Long cost;

    @Column(name = "repair_time")
    private Time repairTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", nullable = false)
    private Car customerCar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_services_costs_id")
    private CarServiceCost carServiceCost;

    public CarService(Long id, String name, String description, Long cost, Time repairTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.repairTime = repairTime;
    }
}
