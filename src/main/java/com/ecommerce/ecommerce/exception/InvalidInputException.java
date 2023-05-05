package com.ecommerce.ecommerce.exception;

import org.springframework.validation.BindingResult;

public class InvalidInputException extends RuntimeException {

    private final BindingResult bindingResult;

    public InvalidInputException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
