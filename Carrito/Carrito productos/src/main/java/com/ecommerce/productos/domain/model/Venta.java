package com.ecommerce.productos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    private Long id;
    private Long usuarioId;
    private Double total;
    private LocalDateTime fecha;
}
