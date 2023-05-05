package com.ecommerce.ecommerce.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private String paymentMethod;

    private String cardHolderName;

    private String cardNumber;

    private String expiryDate;

    private String cvv;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;
}
