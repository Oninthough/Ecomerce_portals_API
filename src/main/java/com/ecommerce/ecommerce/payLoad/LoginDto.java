package com.ecommerce.ecommerce.payLoad;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
public class LoginDto {
    private String firstNameOrEmail;
    private String password;
}
