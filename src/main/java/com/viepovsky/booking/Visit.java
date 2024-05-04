package com.viepovsky.booking;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.car_repair.CarRepair;
import com.viepovsky.garage.Garage;
import com.viepovsky.user.model.AppUser;
import jakarta.persistence.*;
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
@Entity(name = "Visit")
@Table(name = "visit")
public class Visit extends BaseEntityAudit {

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

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "visit_user_id_fk")
    )
    private AppUser user;

    public Visit(BookingStatus status,
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
