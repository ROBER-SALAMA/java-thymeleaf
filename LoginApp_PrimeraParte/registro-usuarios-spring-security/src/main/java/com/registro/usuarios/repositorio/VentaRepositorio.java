package com.registro.usuarios.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.registro.usuarios.modelo.Venta;

@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Long> {
}