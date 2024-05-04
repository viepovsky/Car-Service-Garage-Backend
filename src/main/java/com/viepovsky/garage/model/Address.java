package com.viepovsky.garage.model;

import com.viepovsky.utility.entity_audit.BaseEntityAudit;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Address")
@Table(name = "address")
public class Address extends BaseEntityAudit {
    @Id
    @SequenceGenerator(
            name = "address_id_sequence",
            sequenceName = "address_id_sequence",
            initialValue = 5000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_sequence")
    private Long id;

    @Column(name = "city", length = 128, nullable = false)
    private String city;

    @Column(name = "code", length = 16, nullable = false)
    private String code;

    @Column(name = "street", length = 128, nullable = false)
    private String street;

    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "address")
    private Garage garage;
}
