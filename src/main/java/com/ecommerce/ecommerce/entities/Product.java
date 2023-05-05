package com.ecommerce.ecommerce.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private String description;

    private double price;

    private int inventory;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
