package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCarritoDataJpaRepository extends JpaRepository<ItemCarritoData, Long> {
    List<ItemCarritoData> findByCarritoId(Long carritoId);
}
