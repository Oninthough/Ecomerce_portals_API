package com.ecommerce.ecommerce.payLoad;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class AddressDTO {
    private Long id;

    @NotBlank(message = "Street name cannot be blank")
    private String street;

    @NotBlank(message = "City name cannot be blank")
    private String city;

    @NotBlank(message = "State name cannot be blank")
    private String state;

    @NotBlank(message = "Country name cannot be blank")
    private String country;

    @NotBlank(message = "Zip code cannot be blank")
    private String zipCode;

    // Add other fields as needed
}
