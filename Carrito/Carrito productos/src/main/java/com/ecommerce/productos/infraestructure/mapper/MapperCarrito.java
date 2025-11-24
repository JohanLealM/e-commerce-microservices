package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.Carrito;
import com.ecommerce.productos.domain.model.ItemCarrito;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.CarritoData;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.ItemCarritoData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MapperCarrito {

    private final MapperItemCarrito mapperItemCarrito;

    public CarritoData toData(Carrito carrito) {
        CarritoData carritoData = new CarritoData();
        carritoData.setIdCarrito(carrito.getIdCarrito());
        carritoData.setUsuarioId(carrito.getUsuarioId());
        carritoData.setPrecioTotal(carrito.getPrecioTotal());

        List<ItemCarritoData> items = carrito.getItems().stream()
                .map(item -> {
                    ItemCarritoData itemData = mapperItemCarrito.toItemData(item);
                    itemData.setCarrito(carritoData); // relación bidireccional
                    return itemData;
                }).collect(Collectors.toList());

        carritoData.setItems(items);
        return carritoData;
    }

    public Carrito toDomain(CarritoData carritoData) {
        Carrito carrito = new Carrito();
        carrito.setIdCarrito(carritoData.getIdCarrito());
        carrito.setUsuarioId(carritoData.getUsuarioId());
        carrito.setPrecioTotal(carritoData.getPrecioTotal());

        List<ItemCarrito> items = carritoData.getItems().stream()
                .map(mapperItemCarrito::toItem)
                .collect(Collectors.toList());

        carrito.setItems(items);
        return carrito;
    }
}



