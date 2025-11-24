package com.ecommerce.productos.infraestructure.entry_points;

import com.ecommerce.productos.domain.model.Venta;
import com.ecommerce.productos.domain.usecase.VentaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ecommerce/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaUseCase ventaUseCase;

    @GetMapping
    public ResponseEntity<List<Venta>> listar() {
        return ResponseEntity.ok(ventaUseCase.obtenerVentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtener(@PathVariable Long id) {
        Venta venta = ventaUseCase.obtenerVenta(id);
        if (venta == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(venta);
    }

    @PostMapping
    public ResponseEntity<Venta> crear(@RequestBody Venta venta) {
        Venta creada = ventaUseCase.crearVenta(venta);
        return ResponseEntity.status(201).body(creada);
    }
}