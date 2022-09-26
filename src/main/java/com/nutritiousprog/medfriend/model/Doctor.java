package com.nutritiousprog.medfriend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Doctor")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
    @Column(name = "role", columnDefinition = "TEXT")
    private String role;
    @Column(name = "specialization", columnDefinition = "TEXT")
    private String specialization;
    @Column(name = "phone_number")
    private String phoneNumber;

    public Doctor(String name, String role, String specialization, String phoneNumber) {
        this.name = name;
        this.role = role;
        this.specialization = specialization;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return getName().equals(doctor.getName()) && getRole().equals(doctor.getRole()) && getSpecialization().equals(doctor.getSpecialization()) && getPhoneNumber().equals(doctor.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRole(), getSpecialization(), getPhoneNumber());
    }
}
