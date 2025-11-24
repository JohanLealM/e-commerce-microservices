package com.ecommerce.controller;

import com.ecommerce.dto.PedidoRequestDTO;
import com.ecommerce.entity.EstadoPedido;
import com.ecommerce.entity.Pedido;
import com.ecommerce.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5501")
@RestController
@RequestMapping("/api/ecommerce/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@Valid @RequestBody PedidoRequestDTO dto) {
        Pedido creado = pedidoService.crearPedido(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos(@RequestParam(value = "q", required = false) String q) {
        if (q != null && !q.isBlank()) {
            return ResponseEntity.ok(pedidoService.buscarPorCliente(q));
        }
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtener(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<Pedido> obtenerPorNumero(@PathVariable String numero) {
        return pedidoService.obtenerPorNumero(numero)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Long id, @RequestParam EstadoPedido estado) {
        try {
            Pedido actualizado = pedidoService.actualizarEstado(id, estado);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
