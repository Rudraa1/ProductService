package com.rudra.productservice.Exceptions;

public class ProductNotExistsException extends Exception{
    public ProductNotExistsException(String message) {
        super(message);
    }
}
