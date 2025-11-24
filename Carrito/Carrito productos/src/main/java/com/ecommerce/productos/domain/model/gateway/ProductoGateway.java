package com.ecommerce.productos.domain.model.gateway;

import com.ecommerce.productos.domain.model.Producto;

import java.util.List;


public interface ProductoGateway {
    Producto guardar(Producto producto);

    void eliminarPorID(Long id);

    Producto consultarPorID(Long id);

    Producto actualizarPorID(Producto producto);

    List<Producto> listarProductos(int page, int size);

}