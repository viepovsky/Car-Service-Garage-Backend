package com.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AVAILABLE_CAR_SERVICE")
public class AvailableCarService {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost", nullable = false)
    private Long cost;

    @Column(name = "repair_time")
    private int repairTimeInMinutes;

    @Column(name = "premium_make_list")
    private String premiumMakes;

    @Column(name = "make_multiplier")
    private Long makeMultiplier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    public AvailableCarService(Long id, String name, String description, Long cost, int repairTimeInMinutes, String premiumMakes, Long makeMultiplier) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.repairTimeInMinutes = repairTimeInMinutes;
        this.premiumMakes = premiumMakes;
        this.makeMultiplier = makeMultiplier;
    }
}
