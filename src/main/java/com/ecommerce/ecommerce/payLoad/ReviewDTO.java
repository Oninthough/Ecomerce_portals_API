package com.ecommerce.ecommerce.payLoad;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ReviewDTO {
    private Long id;

    @NotBlank(message = "Comment cannot be empty")
    @Size(max = 500, message = "Comment cannot be longer than 500 characters")
    private String comment;

    @NotNull(message = "Rating cannot be empty")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private int rating;

    @NotNull(message = "User cannot be empty")
    private UserDTO user;

    @NotNull(message = "Product cannot be empty")
    private ProductDTO product;

    // Add other fields as needed
}
