/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jacqu
 */
public interface ClienteRepositorio  extends JpaRepository<Cliente, Long> {
     List<Cliente> findByNombreContainingIgnoreCase(String nombre);
}
