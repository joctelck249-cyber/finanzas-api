package com.mjlc.finanzasapi.repositorio;

import com.mjlc.finanzasapi.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsuarioAndPasswordHashAndActivo(String usuario, String passwordHash, Integer activo);
}