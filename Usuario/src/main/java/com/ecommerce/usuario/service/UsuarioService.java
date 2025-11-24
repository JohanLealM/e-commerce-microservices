package com.ecommerce.usuario.service;
import com.ecommerce.usuario.entity.Usuario;
import com.ecommerce.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
@Service
public class UsuarioService {
 private final UsuarioRepository repo;
 public UsuarioService(UsuarioRepository r){this.repo=r;}
 public Usuario save(Usuario u){return repo.save(u);}
 public Usuario findByEmail(String email){return repo.findByEmail(email);}
}