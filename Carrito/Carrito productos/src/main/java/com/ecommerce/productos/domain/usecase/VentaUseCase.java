package com.ecommerce.productos.domain.usecase;

import com.ecommerce.productos.domain.model.Venta;
import com.ecommerce.productos.domain.model.gateway.VentaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaUseCase {

    private final VentaGateway ventaGateway;

    public Venta crearVenta(Venta venta) {
        venta.setFecha(LocalDateTime.now());
        return ventaGateway.crearVenta(venta);
    }

    public List<Venta> obtenerVentas() {
        return ventaGateway.obtenerVentas();
    }

    public Venta obtenerVenta(Long id) {
        return ventaGateway.obtenerVenta(id);
    }
}
