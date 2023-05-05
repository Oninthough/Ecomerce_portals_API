package com.ecommerce.ecommerce.payLoad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartDTO {
    private Long id;

    @NotNull(message = "User cannot be null")
    @Valid
    @JsonIgnore
    private UserDTO user;

    @NotNull(message = "Cart items cannot be null")
    @Valid
    //private List<CartItemDTO> cartItems;
    private ProductDTO product;
    private int quantity;

    // Add other fields as needed
}
