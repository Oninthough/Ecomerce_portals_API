package com.ecommerce.ecommerce.payLoad;

import com.ecommerce.ecommerce.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderDetailDTO {

    private Long orderDetailId;

    @NotNull(message = "Order is mandatory")
    @JsonIgnore
    private OrderDTO order;

    @NotNull(message = "Product is mandatory")
    private ProductDTO product;

    @NotNull(message = "Quantity is mandatory")
    private int quantity;

    @NotNull(message = "Price is mandatory")
    private double price;

    // Add other fields as needed
}
