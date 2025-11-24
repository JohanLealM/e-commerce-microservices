package com.ecommerce.usuario.entity;
import jakarta.persistence.*;
import lombok.Data;
@Data @Entity
public class Usuario {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 private String nombre;
 private String email;
 private String password;
}