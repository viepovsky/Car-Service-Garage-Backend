package com.viepovsky.booking;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.car_repair.CarRepair;
import com.viepovsky.garage.Garage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKING")
public class Booking extends BaseEntityAudit {

    @Id
    @SequenceGenerator(
            name = "booking_id_sequence",
            sequenceName = "booking_id_sequence",
            initialValue = 5000,
            allocationSize = 100
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "booking_id_sequence"
    )
    private Long id;

    @Column(name = "status")
    private BookingStatus status;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_hour")
    private LocalTime startHour;

    @Column(name = "end_hour")
    private LocalTime endHour;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @OneToMany(
            targetEntity = CarRepair.class,
            mappedBy = "booking",
            fetch = FetchType.LAZY
    )
    private List<CarRepair> carRepairList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    public Booking(BookingStatus status,
                   LocalDate date,
                   LocalTime startHour,
                   LocalTime endHour,
                   BigDecimal totalCost,
                   List<CarRepair> carRepairList,
                   Garage garage) {
        this.status = status;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.totalCost = totalCost;
        this.carRepairList = carRepairList;
        this.garage = garage;
    }
}
