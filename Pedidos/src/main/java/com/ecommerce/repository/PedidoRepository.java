package com.ecommerce.repository;

import com.ecommerce.entity.EstadoPedido;
import com.ecommerce.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findByNumeroPedido(String numeroPedido);

    List<Pedido> findByEmailCliente(String emailCliente);

    List<Pedido> findByEstado(EstadoPedido estado);

    List<Pedido> findByFechaPedidoBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT p FROM Pedido p WHERE p.nombreCliente LIKE %:busqueda% OR p.emailCliente LIKE %:busqueda% OR p.numeroPedido LIKE %:busqueda%")
    List<Pedido> buscarPorCliente(@Param("busqueda") String busqueda);

    @Query("SELECT COALESCE(SUM(p.total), 0.0) FROM Pedido p WHERE p.estado = :estado")
    Double calcularTotalPorEstado(@Param("estado") EstadoPedido estado);

    Long countByEstado(EstadoPedido estado);

    @Query("SELECT p FROM Pedido p ORDER BY p.fechaPedido DESC")
    List<Pedido> findAllOrderByFechaPedidoDesc();

    @Query("SELECT p FROM Pedido p WHERE p.ciudad = :ciudad")
    List<Pedido> findByCiudad(@Param("ciudad") String ciudad);
}
