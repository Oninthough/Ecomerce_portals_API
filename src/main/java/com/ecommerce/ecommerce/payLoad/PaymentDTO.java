package com.ecommerce.ecommerce.payLoad;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class PaymentDTO {

    private Long paymentId;

    @NotBlank(message = "Payment method cannot be blank")
    private String paymentMethod;

    @NotBlank(message = "Cardholder name cannot be blank")
    private String cardHolderName;

    @NotBlank(message = "Card number cannot be blank")
    @Pattern(regexp = "^[0-9]{16}$", message = "Card number must be 16 digits")
    private String cardNumber;

    @NotBlank(message = "Expiry date cannot be blank")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{4})$", message = "Expiry date must be in the format MM/yyyy")
    private String expiryDate;

    @NotBlank(message = "CVV cannot be blank")
    @Pattern(regexp = "^[0-9]{3,4}$", message = "CVV must be 3 or 4 digits")
    private String cvv;

    private OrderDTO order;

    // Add other fields as needed
}

