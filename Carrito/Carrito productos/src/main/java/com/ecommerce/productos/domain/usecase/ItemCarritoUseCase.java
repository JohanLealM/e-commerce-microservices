package com.ecommerce.productos.domain.usecase;
import com.ecommerce.productos.domain.exception.CantidadNegativaException;
import com.ecommerce.productos.domain.exception.CantidadNulaException;
import com.ecommerce.productos.domain.model.Carrito;
import com.ecommerce.productos.domain.model.ItemCarrito;
import com.ecommerce.productos.domain.model.gateway.ItemCarritoGateway;

import java.util.Optional;

public class ItemCarritoUseCase {

    private final ItemCarritoGateway itemCarritoGateway;

    public ItemCarritoUseCase(ItemCarritoGateway itemCarritoGateway) {
        this.itemCarritoGateway = itemCarritoGateway;
    }

    public Carrito agregarOActualizarItem(Carrito carrito, ItemCarrito nuevoItem) {
        // Validar cantidad
        if (nuevoItem.getCantidad() == null) {
            throw new CantidadNulaException("La cantidad no puede ser nula");
        }
        if (nuevoItem.getCantidad() <= 0) {
            throw new CantidadNegativaException("La cantidad debe ser mayor a cero");
        }

        Optional<ItemCarrito> existente = carrito.getItems().stream()
                .filter(item -> item.getProductoId().equals(nuevoItem.getProductoId()))
                .findFirst();

        if (existente.isPresent()) {
            ItemCarrito item = existente.get();
            item.setCantidad(item.getCantidad() + nuevoItem.getCantidad());
            item.setSubtotal(item.getCantidad() * item.getPrecioUnitario());
        } else {
            nuevoItem.setSubtotal(nuevoItem.getCantidad() * nuevoItem.getPrecioUnitario());
            carrito.getItems().add(nuevoItem);
        }

        double total = carrito.getItems().stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();
        carrito.setPrecioTotal(total);

        return carrito;
    }

    // MÉTODO NUEVO - Actualizar cantidad específica de un item
    public Carrito actualizarCantidadItem(Carrito carrito, Long productoId, int cantidad) {
        // Validar cantidad
        if (cantidad <= 0) {
            throw new CantidadNegativaException("La cantidad debe ser mayor a cero");
        }

        Optional<ItemCarrito> itemExistente = carrito.getItems().stream()
                .filter(item -> item.getProductoId().equals(productoId))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrito item = itemExistente.get();
            item.setCantidad(cantidad);
            item.setSubtotal(cantidad * item.getPrecioUnitario());

            // Recalcular total
            double total = carrito.getItems().stream()
                    .mapToDouble(ItemCarrito::getSubtotal)
                    .sum();
            carrito.setPrecioTotal(total);
        } else {
            throw new RuntimeException("Producto no encontrado en el carrito");
        }

        return carrito;
    }

    public Carrito eliminarItem(Carrito carrito, Long productoId) {
        boolean removed = carrito.getItems().removeIf(item -> item.getProductoId().equals(productoId));

        if (removed) {
            double total = carrito.getItems().stream()
                    .mapToDouble(ItemCarrito::getSubtotal)
                    .sum();
            carrito.setPrecioTotal(total);
        }

        return carrito;
    }
}