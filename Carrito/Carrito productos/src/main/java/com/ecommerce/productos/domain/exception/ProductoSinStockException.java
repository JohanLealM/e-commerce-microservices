package com.ecommerce.productos.domain.exception;

public class ProductoSinStockException extends RuntimeException {
    public ProductoSinStockException(String message) {
        super(message);
    }
}
