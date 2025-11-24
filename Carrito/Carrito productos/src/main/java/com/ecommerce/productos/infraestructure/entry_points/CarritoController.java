package com.ecommerce.productos.infraestructure.entry_points;

import com.ecommerce.productos.domain.exception.CarritoNoFoundException;
import com.ecommerce.productos.domain.exception.UsuarioNoEncontradoException;
import com.ecommerce.productos.domain.model.Carrito;
import com.ecommerce.productos.domain.model.ItemCarrito;
import com.ecommerce.productos.domain.usecase.CarritoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecommerce/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoUseCase carritoUseCase;

    // 1. CREAR CARRITO
    @PostMapping("/crear/{usuarioId}")
    public ResponseEntity<?> crearCarrito(@PathVariable Long usuarioId) {
        try {
            System.out.println("DEBUG: Creando carrito para usuario: " + usuarioId);
            Carrito carrito = carritoUseCase.crearCarrito(usuarioId);
            return ResponseEntity.ok(carrito);
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear carrito: " + e.getMessage());
        }
    }

    // 2. AGREGAR PRODUCTO AL CARRITO
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarItem(@RequestParam Long usuarioId,
                                         @RequestBody ItemCarrito item) {
        try {
            Carrito carritoActualizado = carritoUseCase.agregarProductoAlCarrito(usuarioId, item);
            return ResponseEntity.ok(carritoActualizado);
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // 3. VER CARRITO
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> verCarrito(@PathVariable Long usuarioId) {
        try {
            Carrito carrito = carritoUseCase.verCarrito(usuarioId);
            if (carrito == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró carrito para el usuario ID: " + usuarioId);
            }
            return ResponseEntity.ok(carrito);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener carrito: " + e.getMessage());
        }
    }

    // 4. VACIAR CARRITO
    @DeleteMapping("/vaciar/{carritoId}")
    public ResponseEntity<?> vaciarCarrito(@PathVariable Long carritoId) {
        try {
            carritoUseCase.vaciarCarrito(carritoId);
            return ResponseEntity.ok("Carrito vaciado correctamente");
        } catch (CarritoNoFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al vaciar carrito: " + e.getMessage());
        }
    }

    // 5. ELIMINAR PRODUCTO ESPECÍFICO DEL CARRITO
    @DeleteMapping("/{usuarioId}/{productoId}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long usuarioId,
                                              @PathVariable Long productoId) {
        try {
            Carrito carrito = carritoUseCase.eliminarProductoDelCarrito(usuarioId, productoId);
            return ResponseEntity.ok(carrito);
        } catch (CarritoNoFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar producto: " + e.getMessage());
        }
    }

    // 6. ACTUALIZAR CANTIDAD DE PRODUCTO
    @PutMapping("/actualizar-cantidad/{usuarioId}/{productoId}")
    public ResponseEntity<?> actualizarCantidad(@PathVariable Long usuarioId,
                                                @PathVariable Long productoId,
                                                @RequestParam int cantidad) {
        try {
            Carrito carrito = carritoUseCase.actualizarCantidadProducto(usuarioId, productoId, cantidad);
            return ResponseEntity.ok(carrito);
        } catch (CarritoNoFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // 7. FINALIZAR COMPRA
    @PostMapping("/vender")
    public ResponseEntity<?> venderCarrito(@RequestParam Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            return ResponseEntity.badRequest().body("ID de usuario inválido");
        }

        try {
            String mensaje = carritoUseCase.venderCarrito(usuarioId);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al vender carrito: " + e.getMessage());
        }
    }
}