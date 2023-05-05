package com.viepovsky.user;

import com.viepovsky.car.Car;
import com.viepovsky.carservice.CarRepair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USERS")
@SequenceGenerator(name = "seq", initialValue = 5000, allocationSize = 100)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private UserRole role;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @OneToMany(
            targetEntity = Car.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Car> carList = new ArrayList<>();

    @OneToMany(
            targetEntity = CarRepair.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<CarRepair> servicesList = new ArrayList<>();

    public User(String firstName, String lastName, String email, String phoneNumber, String username, String password, UserRole role, LocalDateTime createdDate, List<Car> carList, List<CarRepair> servicesList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdDate = createdDate;
        this.carList = carList;
        this.servicesList = servicesList;
    }

    public User(String firstName, String lastName, String email, String phoneNumber, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }
}
