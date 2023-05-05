package com.viepovsky.garage;

import com.viepovsky.booking.Booking;
import com.viepovsky.garage.availablerepair.AvailableCarRepair;
import com.viepovsky.garage.worktime.GarageWorkTime;
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
@SequenceGenerator(name = "seq", initialValue = 5000, allocationSize = 100)
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
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
            targetEntity = AvailableCarRepair.class,
            mappedBy = "garage",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<AvailableCarRepair> availableCarRepairList = new ArrayList<>();

    public Garage(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Garage(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Garage(String name, String address, List<Booking> bookingList, List<GarageWorkTime> garageWorkTimeList, List<AvailableCarRepair> availableCarRepairList) {
        this.name = name;
        this.address = address;
        this.bookingList = bookingList;
        this.garageWorkTimeList = garageWorkTimeList;
        this.availableCarRepairList = availableCarRepairList;
    }
}
