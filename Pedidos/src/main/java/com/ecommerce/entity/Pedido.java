package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String numeroPedido;

    @Column(nullable = false, length = 200)
    private String nombreCliente;

    @Column(nullable = false, length = 200)
    private String emailCliente;

    @Column(nullable = false, length = 50)
    private String telefonoCliente;

    @Column(nullable = false, length = 500)
    private String direccionEnvio;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Column(length = 20)
    private String codigoPostal;

    @Column(nullable = false, length = 50)
    private String metodoPago;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double envio;

    @Column(nullable = false)
    private Double iva;

    @Column(nullable = false)
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPedido estado;

    @Column(nullable = false)
    private java.time.LocalDateTime fechaPedido;

    private java.time.LocalDateTime fechaActualizacion;

    @Column(length = 1000)
    private String notas;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetallePedido> detalles = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        fechaPedido = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoPedido.PENDIENTE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    public void addDetalle(DetallePedido detalle) {
        detalles.add(detalle);
        detalle.setPedido(this);
    }

    public void removeDetalle(DetallePedido detalle) {
        detalles.remove(detalle);
        detalle.setPedido(null);
    }
}
