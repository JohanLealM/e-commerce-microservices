package com.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(min = 3, max = 200, message = "El nombre debe tener entre 3 y 200 caracteres")
    private String nombreCliente;

    @NotBlank(message = "El email del cliente es obligatorio")
    @Email(message = "El email debe ser válido")
    private String emailCliente;

    @NotBlank(message = "El teléfono del cliente es obligatorio")
    @Pattern(regexp = "^\\+?[0-9\\s\\-()]{7,20}$", message = "El teléfono debe ser válido")
    private String telefonoCliente;

    @NotBlank(message = "La dirección de envío es obligatoria")
    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    private String direccionEnvio;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    private String ciudad;

    @Size(max = 20, message = "El código postal no puede exceder 20 caracteres")
    private String codigoPostal;

    @NotBlank(message = "El método de pago es obligatorio")
    @Pattern(regexp = "efectivo|tarjeta|bitcoin", message = "Método de pago inválido")
    private String metodoPago;

    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.0", message = "El subtotal debe ser mayor o igual a 0")
    private Double subtotal;

    @NotNull(message = "El envío es obligatorio")
    @DecimalMin(value = "0.0", message = "El envío debe ser mayor o igual a 0")
    private Double envio;

    @NotNull(message = "El IVA es obligatorio")
    @DecimalMin(value = "0.0", message = "El IVA debe ser mayor o igual a 0")
    private Double iva;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    private Double total;

    @Size(max = 1000, message = "Las notas no pueden exceder 1000 caracteres")
    private String notas;

    @NotEmpty(message = "El pedido debe tener al menos un producto")
    private java.util.List<DetallePedidoDTO> detalles;
}
