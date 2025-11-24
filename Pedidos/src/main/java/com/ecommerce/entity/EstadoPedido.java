package com.ecommerce.entity;

public enum EstadoPedido {
    PENDIENTE("Pedido pendiente de procesamiento"),
    PROCESANDO("Preparando el pedido"),
    ENVIADO("Pedido en camino"),
    ENTREGADO("Pedido entregado exitosamente"),
    CANCELADO("Pedido cancelado");

    private final String descripcion;

    EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
