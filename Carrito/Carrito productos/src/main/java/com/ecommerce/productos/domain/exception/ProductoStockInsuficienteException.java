package com.ecommerce.productos.domain.exception;

public class ProductoStockInsuficienteException extends RuntimeException {
    public ProductoStockInsuficienteException(String message) {
        super(message);
    }
}
