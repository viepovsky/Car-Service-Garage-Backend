package com.viepovsky.user;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.car.Car;
import com.viepovsky.carservice.CarRepair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USERS")
@SequenceGenerator(name = "seq", initialValue = 5000, allocationSize = 100)
public class User extends BaseEntityAudit {
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
    @Enumerated(EnumType.STRING)
    private Role role;

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

    public User(String firstName, String lastName, String email, String phoneNumber, String username, String password, Role role, List<Car> carList, List<CarRepair> servicesList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.role = role;
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
