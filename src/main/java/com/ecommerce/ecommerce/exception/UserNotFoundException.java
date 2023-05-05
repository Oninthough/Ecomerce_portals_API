package com.ecommerce.ecommerce.exception;

public class UserNotFoundException extends RuntimeException{
public UserNotFoundException(String msg)
{
    super(msg);
}
}
