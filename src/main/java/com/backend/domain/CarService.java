package com.backend.domain;

import lombok.*;
import javax.persistence.*;

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
    private int repairTimeInMinutes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", nullable = false)
    private Car customerCar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public CarService(Long id, String name, String description, Long cost, int repairTimeInMinutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.repairTimeInMinutes = repairTimeInMinutes;
    }
}
