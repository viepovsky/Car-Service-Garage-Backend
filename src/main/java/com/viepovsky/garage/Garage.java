package com.viepovsky.garage;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.booking.Booking;
import com.viepovsky.garage.available_car_repair.AvailableCarRepair;
import com.viepovsky.garage.garage_work_time.GarageWorkTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GARAGE")
public class Garage extends BaseEntityAudit {

    @Id
    @SequenceGenerator(
            name = "garage_id_sequence",
            sequenceName = "garage_id_sequence",
            initialValue = 5000,
            allocationSize = 100
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "garage_id_sequence"
    )
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

    public Garage(Long id,
                  String name,
                  String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Garage(String name,
                  String address) {
        this.name = name;
        this.address = address;
    }

    public Garage(String name,
                  String address,
                  List<Booking> bookingList,
                  List<GarageWorkTime> garageWorkTimeList,
                  List<AvailableCarRepair> availableCarRepairList) {
        this.name = name;
        this.address = address;
        this.bookingList = bookingList;
        this.garageWorkTimeList = garageWorkTimeList;
        this.availableCarRepairList = availableCarRepairList;
    }
}
