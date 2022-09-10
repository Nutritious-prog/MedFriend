package com.nutritiousprog.medfriend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
@NoArgsConstructor
@Getter
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "street", nullable = false, columnDefinition = "TEXT")
    private String street;
    @Column(name = "city", nullable = false, columnDefinition = "TEXT")
    private String city;
    @Column(name = "postal_code", nullable = false, columnDefinition = "TEXT")
    private String postalCode;

    public Address(String street, String city, String postalCode) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getStreet().equals(address.getStreet()) && getCity().equals(address.getCity()) && getPostalCode().equals(address.getPostalCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStreet(), getCity(), getPostalCode());
    }
}
