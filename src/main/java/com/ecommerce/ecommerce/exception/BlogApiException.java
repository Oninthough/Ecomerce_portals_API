package com.ecommerce.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException{
    private HttpStatus status;
    private  String msg;

    public BlogApiException(HttpStatus status, String msg) {
        super(msg);
        this.status = status;
        this.msg = msg;
    }


        public HttpStatus getStatus () {
            return status;
        }

        public String getMsg () {
            return msg;
        }
}
