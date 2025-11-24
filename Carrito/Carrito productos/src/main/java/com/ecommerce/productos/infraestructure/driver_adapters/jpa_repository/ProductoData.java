package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //Indica que la clase es una entidad de BD
@AllArgsConstructor //genera constructor con todos los parámetros
@NoArgsConstructor //genera constructor vacío
@Table (name = "producto") //Crear otra tabla en la BD llamada "Producto"
@Data //Con Data se obtienen getters, setters, y se utiliza en entidades JPA
public class ProductoData {

    @Id //definir el ID como llave primaria (PK)
    @GeneratedValue(strategy = GenerationType.AUTO) //Se generan los IDs automáticamente
    private Long id;

    private String nombre;

    //Columna "Descripción" con restriccion de no mas de 40 caracteres, y no puede ser nula
    @Column(length = 40, nullable = false)
    private String descripcion;

    @Column(length = 100)  // ← NUEVO: Campo para categoría
    private String categoria;

    private Double precio;
    private Integer stock;
    private String imagenUrl;

}