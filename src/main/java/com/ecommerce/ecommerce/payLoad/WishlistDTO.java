package com.ecommerce.ecommerce.payLoad;

import com.ecommerce.ecommerce.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class WishlistDTO {
    private Long wishlistId;

    @NotNull(message = "User cannot be null")
    @JsonIgnore
    private User user;

    @NotNull(message = "Product cannot be null")
    private ProductDTO product;

    // Add other fields as needed
}
