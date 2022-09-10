package com.nutritiousprog.medfriend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Patient")
@NoArgsConstructor
@Getter
@ToString
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "address_id", referencedColumnName = "ID")
    private Address address;
    @Column(name = "phone_number")
    private String phoneNumber;


    public Patient(String name, Address address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return  getPhoneNumber().equals(patient.getPhoneNumber()) &&
                getName().equals(patient.getName()) &&
                getAddress().equals(patient.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAddress(), getPhoneNumber());
    }
}
