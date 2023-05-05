package com.ecommerce.ecommerce.payLoad;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ProductDTO {
    @NotNull(message = "Product ID cannot be null.")
    private Long id;
    @NotBlank(message = "Product name cannot be blank.")
    private String name;

    @NotBlank(message = "Product description cannot be blank.")
    private String description;

    @Positive(message = "Price must be a positive value.")
    private double price;

    @Positive(message = "Quantity must be a positive value.")
    private int quantity;

    @NotNull(message = "Category cannot be null.")
    private CategoryDTO category;
}