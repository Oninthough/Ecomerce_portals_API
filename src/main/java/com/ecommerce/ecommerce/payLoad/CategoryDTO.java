package com.ecommerce.ecommerce.payLoad;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDTO {
    private Long id;
    @NotBlank(message = "Category name cannot be blank")
    private String name;

    @NotBlank(message = "Category description cannot be blank")
    private String description;

// Add other fields as needed
}