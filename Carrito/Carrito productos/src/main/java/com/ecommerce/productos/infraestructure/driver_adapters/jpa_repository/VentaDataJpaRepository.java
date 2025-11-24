package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaDataJpaRepository extends JpaRepository<VentaData, Long> {
}
