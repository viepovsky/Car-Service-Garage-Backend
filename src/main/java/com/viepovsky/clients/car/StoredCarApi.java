package com.viepovsky.clients.car;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARS_API")
class StoredCarApi {
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
