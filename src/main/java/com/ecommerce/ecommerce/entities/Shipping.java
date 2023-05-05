package com.ecommerce.ecommerce.entities;

import lombok.Data;
import javax.persistence.*;
@Entity
@Table(name = "shipping")
@Data
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingId;

    private String shippingMethod;

    private String address;

    private double shippingCost;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;
}
