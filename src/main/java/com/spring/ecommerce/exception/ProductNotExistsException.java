package com.spring.ecommerce.exception;

public class ProductNotExistsException extends IllegalArgumentException{
    public ProductNotExistsException(String msg) {
        super(msg);
    }
}
