package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.ItemCarritoData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "carrito")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    private Long usuarioId;


    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarritoData> items;

    private Double precioTotal;
}

