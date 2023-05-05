package com.ecommerce.ecommerce.payLoad;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ShippingDTO {
    private Long shippingId;
    @NotBlank(message = "Shipping method cannot be blank")
    private String shippingMethod;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotNull(message = "Shipping cost cannot be null")
    private double shippingCost;

    @NotNull(message = "Order cannot be null")
    @Valid
    private OrderDTO order;

// Add other fields as needed
}