package com.ecommerce.productos.domain.model.gateway;

import com.ecommerce.productos.domain.model.Carrito;
import java.util.Optional;

public interface CarritoGateway {

    Carrito guardar(Carrito carrito);
    Carrito buscarPorUsuarioId(Long usuarioId);
    Optional<Carrito> buscarPorId(Long carritoId); //
    void eliminarCarrito(Long carritoId);
    boolean existeCarrito(Long carritoId); //
}
