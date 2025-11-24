package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository;


import com.ecommerce.productos.domain.model.Carrito;
import com.ecommerce.productos.domain.model.gateway.CarritoGateway;
import com.ecommerce.productos.infraestructure.mapper.MapperCarrito;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CarritoDataGatewayImpl implements CarritoGateway {

    private final CarritoDataJpaRepository repository;
    private final MapperCarrito mapper;

    @Override
    public Carrito guardar(Carrito carrito) {
        var entity = mapper.toData(carrito);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Carrito buscarPorUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public void eliminarCarrito(Long carritoId) {
        repository.deleteById(carritoId);
    }

    @Override
    public boolean existeCarrito(Long carritoId) {
        return repository.existsById(carritoId);
    }

    @Override
    public Optional<Carrito> buscarPorId(Long carritoId) {
        return repository.findById(carritoId)
                .map(mapper::toDomain);
    }
}