package com.ecommerce.productos.domain.exception;

public class CantidadNegativaException extends RuntimeException {
    public CantidadNegativaException(String message) {
        super(message);
    }
}
