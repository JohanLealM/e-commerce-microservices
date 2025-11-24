package com.ecommerce.productos.infraestructure.entry_points;

import com.ecommerce.productos.domain.model.Producto;
import com.ecommerce.productos.domain.usecase.ProductoUseCase;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.ProductoData;
import com.ecommerce.productos.infraestructure.mapper.MapperProducto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5501") // ← ESTA ES LA LÍNEA CORRECTA
@RestController
@RequestMapping("/api/ecommerce/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoUseCase productoUseCase;
    private final MapperProducto mapperProducto;

    @PostMapping("/save")
    public ResponseEntity<Producto> saveProducto(@RequestBody ProductoData productodata) {
        Producto producto = mapperProducto.toProducto(productodata);
        Producto productoValidadoGuardado = productoUseCase.guardarProducto(producto);

        if (productoValidadoGuardado.getId() != null) {
            return ResponseEntity.ok(productoValidadoGuardado);
        }
        return new ResponseEntity<>(productoValidadoGuardado, HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        try {
            productoUseCase.eliminarProducto(id);
            return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> findByIdProducto(@PathVariable Long id) {
        Producto productoValidadoEncontrado = productoUseCase.consultarProducto(id);
        if (productoValidadoEncontrado.getId() != null) {
            return new ResponseEntity<>(productoValidadoEncontrado, HttpStatus.OK);
        }
        return new ResponseEntity<>(productoValidadoEncontrado, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<Producto> updateProducto(@RequestBody ProductoData productodata) {
        try {
            Producto producto = mapperProducto.toProducto(productodata);
            Producto productoValidadoActualizado = productoUseCase.actualizarProducto(producto);
            return new ResponseEntity<>(productoValidadoActualizado, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> findAllProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Producto> productos = productoUseCase.consultarProductos(page, size);
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}
