package com.example.accessingdatajpa.model;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
 
@Entity
@Getter
@Setter

public class Property {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private double price;
    private double size;
    private String description;

    // Default constructor
    public Property() {}

    // Constructor with parameters
    public Property(String address, double price, double size, String description) {
        this.address = address;
        this.price = price;
        this.size = size;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "Property[id=%d, address='%s', price=%.2f, size=%.2f, description='%s']",
                id, address, price, size, description);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public double getPrice() {
        return price;
    }

    public double getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }
}