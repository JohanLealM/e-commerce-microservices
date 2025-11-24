package com.ecommerce.productos.domain.exception;

public class CantidadNulaException extends RuntimeException {
    public CantidadNulaException(String message) {
        super(message);
    }
}
