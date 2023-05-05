package com.ecommerce.ecommerce.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String street;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}

