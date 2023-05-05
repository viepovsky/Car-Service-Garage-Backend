package com.viepovsky.booking;

import com.viepovsky.carservice.CarRepair;
import com.viepovsky.garage.Garage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKING")
@SequenceGenerator(name = "seq", initialValue = 5000, allocationSize = 100)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "status")
    private BookingStatus status;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_hour")
    private LocalTime startHour;

    @Column(name = "end_hour")
    private LocalTime endHour;

    @Column(name = "created")
    private LocalDateTime created;

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

    public Booking(BookingStatus status, LocalDate date, LocalTime startHour, LocalTime endHour, LocalDateTime created, BigDecimal totalCost, List<CarRepair> carRepairList, Garage garage) {
        this.status = status;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.created = created;
        this.totalCost = totalCost;
        this.carRepairList = carRepairList;
        this.garage = garage;
    }
}
