package com.viepovsky.garage.available_car_repair;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.garage.Garage;
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
@Table(name = "AVAILABLE_CAR_REPAIRS")
@SequenceGenerator(name = "seq", initialValue = 5000, allocationSize = 100)

public class AvailableCarRepair extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @Column(name = "repair_time")
    private int repairTimeInMinutes;

    @Column(name = "premium_make_list")
    private String premiumMakes;

    @Column(name = "make_multiplier")
    private BigDecimal makeMultiplier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    public AvailableCarRepair(String name, String description, BigDecimal cost, int repairTimeInMinutes, String premiumMakes, BigDecimal makeMultiplier, Garage garage) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.repairTimeInMinutes = repairTimeInMinutes;
        this.premiumMakes = premiumMakes;
        this.makeMultiplier = makeMultiplier;
        this.garage = garage;
    }

    public AvailableCarRepair(AvailableCarRepair availableCarRepair) {
        this.id = availableCarRepair.getId();
        this.name = availableCarRepair.getName();
        this.description = availableCarRepair.getDescription();
        this.cost = availableCarRepair.getCost();
        this.repairTimeInMinutes = availableCarRepair.getRepairTimeInMinutes();
        this.premiumMakes = availableCarRepair.getPremiumMakes();
        this.makeMultiplier = availableCarRepair.getMakeMultiplier();
        this.garage = availableCarRepair.getGarage();
    }
}
