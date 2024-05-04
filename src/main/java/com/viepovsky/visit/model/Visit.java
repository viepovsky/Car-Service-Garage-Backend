package com.viepovsky.visit.model;

import com.viepovsky.garage.model.Garage;
import com.viepovsky.offer.model.SelectedOffer;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.utility.entity_audit.BaseEntityAudit;
import com.viepovsky.vehicle.model.Vehicle;

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
            name = "visit_id_sequence",
            sequenceName = "visit_id_sequence",
            initialValue = 5000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visit_id_sequence")
    private Long id;

    @Column(name = "visit_start_date", nullable = false)
    private LocalDate visitStartDate;

    @Column(name = "visit_start_time", nullable = false)
    private LocalTime visitStartTime;

    @Column(name = "visit_end_date", nullable = false)
    private LocalDate visitEndDate;

    @Column(name = "visit_end_time", nullable = false)
    private LocalTime visitEndTime;

    @Column(name = "license_plate", length = 64, nullable = false)
    private String licensePlate;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VisitStatus status;

    @OneToMany(
            targetEntity = SelectedOffer.class,
            mappedBy = "visit",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<SelectedOffer> selectedOffers;

    @ManyToOne
    @JoinColumn(
            name = "garage_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "visit_garage_id_fk"))
    private Garage garage;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "visit_user_id_fk"))
    private AppUser user;

    @ManyToOne
    @JoinColumn(
            name = "vehicle_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "visit_vehicle_id_fk"))
    private Vehicle vehicle;

    public Visit(
            VisitStatus status,
            LocalDate visitStartDate,
            LocalTime visitStartTime,
            LocalTime visitEndTime,
            BigDecimal totalPrice,
            List<SelectedOffer> selectedOffers,
            Garage garage) {
        this.status = status;
        this.visitStartDate = visitStartDate;
        this.visitStartTime = visitStartTime;
        this.visitEndTime = visitEndTime;
        this.totalPrice = totalPrice;
        this.selectedOffers = selectedOffers;
        this.garage = garage;
    }
}
