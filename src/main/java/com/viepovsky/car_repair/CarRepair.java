package com.viepovsky.car_repair;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.booking.Booking;
import com.viepovsky.car.Car;
import com.viepovsky.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARS_REPAIRS")
@SequenceGenerator(name = "seq", initialValue = 5000, allocationSize = 100)
public class CarRepair extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "repair_time")
    private int repairTimeInMinutes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "service_status")
    private RepairStatus status;

    public CarRepair(String name, String description, BigDecimal cost, int repairTimeInMinutes, Car car, User user, Booking booking, RepairStatus status) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.repairTimeInMinutes = repairTimeInMinutes;
        this.car = car;
        this.user = user;
        this.booking = booking;
        this.status = status;
    }

    public CarRepair(String name, String description, BigDecimal cost, int repairTimeInMinutes, Car car, User user, RepairStatus status) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.repairTimeInMinutes = repairTimeInMinutes;
        this.car = car;
        this.user = user;
        this.status = status;
    }
}
