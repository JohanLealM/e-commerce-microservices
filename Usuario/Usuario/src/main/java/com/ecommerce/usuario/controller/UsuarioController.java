package com.ecommerce.usuario.controller;
import com.ecommerce.usuario.entity.Usuario;
import com.ecommerce.usuario.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@CrossOrigin(origins="http://localhost:5501")
@RestController @RequestMapping("/api/ecommerce/usuario")
public class UsuarioController{
 private final UsuarioService service;
 public UsuarioController(UsuarioService s){this.service=s;}
 @PostMapping("/save")
 public ResponseEntity<Usuario> save(@RequestBody Usuario u){return ResponseEntity.ok(service.save(u));}
 @PostMapping("/login")
 public ResponseEntity<String> login(@RequestBody Map<String,String> body){
  Usuario u=service.findByEmail(body.get("email"));
  if(u==null) return ResponseEntity.status(401).body("Usuario no existe");
  if(!u.getPassword().equals(body.get("password"))) return ResponseEntity.status(401).body("Contraseña incorrecta");
  return ResponseEntity.ok("Login exitoso");
 }
}