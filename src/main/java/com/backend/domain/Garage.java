package com.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GARAGE")
public class Garage {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(
            targetEntity = Booking.class,
            mappedBy = "garage",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Booking> bookingList = new ArrayList<>();

    @OneToMany(
            targetEntity = GarageWorkTime.class,
            mappedBy = "garage",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<GarageWorkTime> garageWorkTimeList = new ArrayList<>();

    @OneToMany(
            targetEntity = AvailableCarService.class,
            mappedBy = "garage",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<AvailableCarService> availableCarServiceList = new ArrayList<>();

    public Garage(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
    public Garage(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Garage(String name, String address, List<Booking> bookingList, List<GarageWorkTime> garageWorkTimeList, List<AvailableCarService> availableCarServiceList) {
        this.name = name;
        this.address = address;
        this.bookingList = bookingList;
        this.garageWorkTimeList = garageWorkTimeList;
        this.availableCarServiceList = availableCarServiceList;
    }
}
