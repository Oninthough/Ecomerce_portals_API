package com.ecommerce.ecommerce.payLoad;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;

    @NotNull(message = "Product cannot be null")
    private ProductDTO product;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
    // Add other fields as needed
}
