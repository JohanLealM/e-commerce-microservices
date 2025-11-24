package com.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    @Positive(message = "El ID del producto debe ser positivo")
    private Long productoId;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 300, message = "El nombre del producto no puede exceder 300 caracteres")
    private String nombreProducto;

    @NotBlank(message = "La categoría del producto es obligatoria")
    @Size(max = 100, message = "La categoría no puede exceder 100 caracteres")
    private String categoriaProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private Double precioUnitario;
}
