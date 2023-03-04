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

    @Column(name = "description")
    private String description;

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
}
