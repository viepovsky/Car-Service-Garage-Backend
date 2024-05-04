package com.viepovsky.user;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.booking.Visit;
import com.viepovsky.car.Vehicle;
import com.viepovsky.car_repair.CarRepair;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "AppUser")
@Table(name = "app_user")
public class AppUser extends BaseEntityAudit implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence",
            initialValue = 5000,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "username",
            unique = true,
            length = 64,
            updatable = false,
            nullable = false
    )
    private String username;

    @Column(name = "first_name", length = 128)
    private String firstName;

    @Column(name = "last_name", length = 128)
    private String lastName;

    @Column(name = "company_name", length = 128)
    private String companyName;

    @Column(name = "email", length = 128)
    private String email;

    @Column(name = "mobile", length = 64)
    private String mobile;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(
            targetEntity = Vehicle.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(
            targetEntity = Visit.class,
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    private List<Visit> visits = new ArrayList<>();

    public AppUser(String firstName,
                   String lastName,
                   String email,
                   String mobile,
                   String username,
                   String password,
                   Role role,
                   List<Vehicle> vehicles,
                   List<CarRepair> servicesList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.username = username;
        this.password = password;
        this.role = role;
        this.vehicles = vehicles;
    }

    public AppUser(String firstName,
                   String lastName,
                   String email,
                   String mobile,
                   String username,
                   String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.username = username;
        this.password = password;
    }

    public void updateUser(AppUser user) {
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        mobile = user.getMobile();
        if (user.getPassword() != null) {
            password = user.getPassword();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
