package com.ecommerce.usuario.repository;
import com.ecommerce.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
 Usuario findByEmail(String email);
}