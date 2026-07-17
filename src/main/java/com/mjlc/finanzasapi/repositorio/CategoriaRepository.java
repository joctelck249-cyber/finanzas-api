package com.mjlc.finanzasapi.repositorio;

import com.mjlc.finanzasapi.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByTipo(String tipo);
}
