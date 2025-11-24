package com.ecommerce.productos.domain.usecase;

import com.ecommerce.productos.domain.exception.*;
import com.ecommerce.productos.domain.model.Carrito;
import com.ecommerce.productos.domain.model.ItemCarrito;
import com.ecommerce.productos.domain.model.Producto;
import com.ecommerce.productos.domain.model.gateway.CarritoGateway;
import com.ecommerce.productos.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CarritoUseCase {

    private final CarritoGateway carritoGateway;
    private final ProductoUseCase productoUseCase;
    private final UsuarioGateway usuarioGateway;
    private final ItemCarritoUseCase itemCarritoUseCase;


    public Carrito crearCarrito(Long usuarioId) {
        // Verificar si el usuario existe
        if (!usuarioGateway.usuarioExiste(usuarioId)) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + usuarioId);
        }

        // Verificar si ya existe un carrito para este usuario
        Carrito carritoExistente = carritoGateway.buscarPorUsuarioId(usuarioId);
        if (carritoExistente != null) {
            return carritoExistente;
        }

        // Crear nuevo carrito
        Carrito nuevoCarrito = new Carrito();
        nuevoCarrito.setUsuarioId(usuarioId);
        nuevoCarrito.setItems(new ArrayList<>());

        // Guardar y retornar el carrito
        return carritoGateway.guardar(nuevoCarrito);
    }

    public Carrito agregarProductoAlCarrito(Long usuarioId, ItemCarrito itemCarrito) {
        if (!usuarioGateway.usuarioExiste(usuarioId)) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + usuarioId);
        }

        // Verificar si el producto existe
        Producto producto = productoUseCase.consultarProducto(itemCarrito.getProductoId());
        if (producto == null) {
            throw new ProductoNoEncontradoException("Producto no encontrado con ID: " + itemCarrito.getProductoId());
        }

        // Configurar datos del item
        itemCarrito.setNombreProducto(producto.getNombre());
        itemCarrito.setPrecioUnitario(producto.getPrecio());
        itemCarrito.setSubtotal(producto.getPrecio() * itemCarrito.getCantidad());

        // Buscar o crear carrito
        Carrito carrito = carritoGateway.buscarPorUsuarioId(usuarioId);
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setUsuarioId(usuarioId);
            carrito.setItems(new ArrayList<>());
        }

        // Agregar o actualizar item
        carrito = itemCarritoUseCase.agregarOActualizarItem(carrito, itemCarrito);
        return carritoGateway.guardar(carrito);
    }

    public Carrito verCarrito(Long usuarioId) {
        return carritoGateway.buscarPorUsuarioId(usuarioId);
    }

    public void vaciarCarrito(Long carritoId) {
        if (!carritoGateway.existeCarrito(carritoId)) {
            throw new CarritoNoFoundException("Carrito no encontrado con ID: " + carritoId);
        }
        carritoGateway.eliminarCarrito(carritoId);
    }

    public Carrito eliminarProductoDelCarrito(Long usuarioId, Long productoId) {
        Carrito carrito = carritoGateway.buscarPorUsuarioId(usuarioId);
        if (carrito == null) {
            throw new CarritoNoFoundException("Carrito no encontrado para el usuario ID: " + usuarioId);
        }

        carrito = itemCarritoUseCase.eliminarItem(carrito, productoId);
        return carritoGateway.guardar(carrito);
    }

    // Actualizar cantidad de un producto en el carrito
    public Carrito actualizarCantidadProducto(Long usuarioId, Long productoId, int cantidad) {
        Carrito carrito = carritoGateway.buscarPorUsuarioId(usuarioId);
        if (carrito == null) {
            throw new CarritoNoFoundException("Carrito no encontrado para el usuario ID: " + usuarioId);
        }

        carrito = itemCarritoUseCase.actualizarCantidadItem(carrito, productoId, cantidad);
        return carritoGateway.guardar(carrito);
    }

    public String venderCarrito(Long usuarioId) {
        Carrito carrito = carritoGateway.buscarPorUsuarioId(usuarioId);
        if (carrito == null) {
            throw new CarritoNoFoundException("No hay carrito activo para el usuario ID: " + usuarioId);
        }

        List<ItemCarrito> items = carrito.getItems();
        if (items == null || items.isEmpty()) {
            throw new CarritoVacioException("El carrito está vacío. No hay productos para comprar.");
        }

        items.stream()
                .map(item -> {
                    // Validación de cantidad nula
                    if (item.getCantidad() == null) {
                        throw new CantidadNulaException("La cantidad del producto con ID " + item.getProductoId() + " no puede ser nula.");
                    }

                    // Validación de cantidad negativa o cero
                    if (item.getCantidad() <= 0) {
                        throw new CantidadNegativaException("La cantidad del producto con ID " + item.getProductoId() + " debe ser mayor que cero.");
                    }

                    // Consulta del producto
                    Producto producto = productoUseCase.consultarProducto(item.getProductoId());

                    if (producto == null) {
                        throw new ProductoNoEncontradoException("Producto no encontrado con ID: " + item.getProductoId());
                    }

                    if (producto.getStock() <= 0) {
                        throw new ProductoSinStockException("El producto '" + producto.getNombre() + "' no tiene stock disponible.");
                    }

                    if (producto.getStock() < item.getCantidad()) {
                        throw new ProductoStockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre());
                    }

                    // Actualiza el stock
                    producto.setStock(producto.getStock() - item.getCantidad());
                    return producto;
                })
                .forEach(productoUseCase::actualizarProducto);

        carritoGateway.eliminarCarrito(carrito.getIdCarrito());
        return "Compra realizada con éxito. Se vendieron " + items.size() + " productos.";
    }
}