package com.backend.api.car.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARS_API")
public class StoredCarApi {
    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    private List<Integer> carYearsList = new ArrayList<>();

    @ElementCollection
    private List<String> carMakesList = new ArrayList<>();

    @ElementCollection
    private List<String> carTypesList = new ArrayList<>();

    @Column(name = "date")
    private LocalDate dateFetched;

    public StoredCarApi(List<Integer> carYearsList, List<String> carMakesList, List<String> carTypesList, LocalDate dateFetched) {
        this.carYearsList = carYearsList;
        this.carMakesList = carMakesList;
        this.carTypesList = carTypesList;
        this.dateFetched = dateFetched;
    }
}
