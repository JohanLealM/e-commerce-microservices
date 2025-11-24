package com.ecommerce.service;

import com.ecommerce.dto.DetallePedidoDTO;
import com.ecommerce.dto.PedidoRequestDTO;
import com.ecommerce.entity.DetallePedido;
import com.ecommerce.entity.EstadoPedido;
import com.ecommerce.entity.Pedido;
import com.ecommerce.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido crearPedido(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setNumeroPedido(generateNumeroPedido());
        pedido.setNombreCliente(dto.getNombreCliente());
        pedido.setEmailCliente(dto.getEmailCliente());
        pedido.setTelefonoCliente(dto.getTelefonoCliente());
        pedido.setDireccionEnvio(dto.getDireccionEnvio());
        pedido.setCiudad(dto.getCiudad());
        pedido.setCodigoPostal(dto.getCodigoPostal());
        pedido.setMetodoPago(dto.getMetodoPago());
        pedido.setSubtotal(dto.getSubtotal());
        pedido.setEnvio(dto.getEnvio());
        pedido.setIva(dto.getIva());
        pedido.setTotal(dto.getTotal());
        pedido.setNotas(dto.getNotas());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        // Add detalles
        if (dto.getDetalles() != null) {
            for (DetallePedidoDTO d : dto.getDetalles()) {
                DetallePedido detalle = new DetallePedido();
                detalle.setProductoId(d.getProductoId());
                detalle.setNombreProducto(d.getNombreProducto());
                detalle.setCategoriaProducto(d.getCategoriaProducto());
                detalle.setCantidad(d.getCantidad());
                detalle.setPrecioUnitario(d.getPrecioUnitario());
                // subtotal calculated in entity lifecycle
                pedido.addDetalle(detalle);
            }
        }
        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> obtenerPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Optional<Pedido> obtenerPorNumero(String numero) {
        return pedidoRepository.findByNumeroPedido(numero);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAllOrderByFechaPedidoDesc();
    }

    public List<Pedido> buscarPorCliente(String busqueda) {
        if (!StringUtils.hasText(busqueda)) {
            return listarTodos();
        }
        return pedidoRepository.buscarPorCliente(busqueda);
    }

    public List<Pedido> listarPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }

    public Pedido actualizarEstado(Long id, EstadoPedido nuevoEstado) {
        Pedido p = pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
        p.setEstado(nuevoEstado);
        p.setFechaActualizacion(LocalDateTime.now());
        return pedidoRepository.save(p);
    }

    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    public Double calcularTotalPorEstado(EstadoPedido estado) {
        return pedidoRepository.calcularTotalPorEstado(estado);
    }

    private String generateNumeroPedido() {
        return "FP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
