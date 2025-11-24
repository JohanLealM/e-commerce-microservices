package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.Producto;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.ProductoData;
import org.springframework.stereotype.Component;

@Component
public class MapperProducto {

    public Producto toProducto(ProductoData data) {
        return new Producto(
                data.getId(),           // Long
                data.getNombre(),       // String
                data.getDescripcion(),  // String
                data.getPrecio(),       // Double
                data.getStock(),        // Integer
                data.getCategoria(),    // String
                data.getImagenUrl()     // String
        );
    }

    public ProductoData toProductoData(Producto producto) {
        ProductoData data = new ProductoData();
        data.setId(producto.getId());
        data.setNombre(producto.getNombre());
        data.setDescripcion(producto.getDescripcion());
        data.setPrecio(producto.getPrecio());
        data.setStock(producto.getStock());
        data.setCategoria(producto.getCategoria());
        data.setImagenUrl(producto.getImagenUrl());
        return data;
    }
}