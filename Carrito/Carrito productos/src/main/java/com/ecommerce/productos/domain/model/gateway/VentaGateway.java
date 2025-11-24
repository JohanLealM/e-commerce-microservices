package com.ecommerce.productos.domain.model.gateway;

import com.ecommerce.productos.domain.model.Venta;

import java.util.List;

public interface VentaGateway {
    Venta crearVenta(Venta venta);
    List<Venta> obtenerVentas();
    Venta obtenerVenta(Long id);
}
