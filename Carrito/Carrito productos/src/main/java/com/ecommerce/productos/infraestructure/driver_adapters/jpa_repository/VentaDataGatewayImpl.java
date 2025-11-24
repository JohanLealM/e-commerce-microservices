package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository;

import com.ecommerce.productos.domain.model.Venta;
import com.ecommerce.productos.domain.model.gateway.VentaGateway;
import com.ecommerce.productos.infraestructure.mapper.MapperVenta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class VentaDataGatewayImpl implements VentaGateway {

    private final VentaDataJpaRepository repository;
    private final MapperVenta mapper;

    @Override
    public Venta crearVenta(Venta venta) {
        VentaData data = mapper.toVentaData(venta);
        VentaData saved = repository.save(data);
        return mapper.toVenta(saved);
    }

    @Override
    public List<Venta> obtenerVentas() {
        return repository.findAll().stream().map(mapper::toVenta).collect(Collectors.toList());
    }

    @Override
    public Venta obtenerVenta(Long id) {
        return repository.findById(id).map(mapper::toVenta).orElse(null);
    }
}