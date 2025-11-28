/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Mascota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jacqu
 */
public interface MascotaRepositorio extends JpaRepository<Mascota, Long> {
    
    List<Mascota> findByNombreContainingIgnoreCase(String nombre);
}
