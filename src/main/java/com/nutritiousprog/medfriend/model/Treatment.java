package com.nutritiousprog.medfriend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Treatment")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private double price;

    public Treatment(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Treatment)) return false;
        Treatment treatment = (Treatment) o;
        return Double.compare(treatment.getPrice(), getPrice()) == 0 && getName().equals(treatment.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getName(), getPrice());
    }
}