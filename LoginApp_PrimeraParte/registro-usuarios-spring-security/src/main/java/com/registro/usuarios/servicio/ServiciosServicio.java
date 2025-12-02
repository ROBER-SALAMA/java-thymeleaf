/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.servicio;


import com.registro.usuarios.controlador.ServiciosDTO;
import com.registro.usuarios.modelo.Servicios;
import com.registro.usuarios.repositorio.ServiciosRepositorio;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jacqu
 */
@Service
public class ServiciosServicio {
    @Autowired
    private ServiciosRepositorio serviciosRepositorio;

    public List<ServiciosDTO> listarTodosLosServicios() {
        return serviciosRepositorio.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ServiciosDTO obtenerServiciosPorId(Long id) {
        Servicios Servicios = serviciosRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("servicio no encontrado"));
        return convertirADTO(Servicios);
    }

    public ServiciosDTO guardarServicios(ServiciosDTO serviciosDTO) {
        Servicios Servicios = convertirAEntidad(serviciosDTO);
        Servicios ServiciosSaved = serviciosRepositorio.save(Servicios);
        return convertirADTO(ServiciosSaved);
    }

    public ServiciosDTO actualizarServicios(Long id, ServiciosDTO serviciosDTO) {
        Servicios servicioExistente = serviciosRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("servicio no encontrado"));

        servicioExistente.setNombre(serviciosDTO.getNombre());
        Servicios servicioActualizado = serviciosRepositorio.save(servicioExistente);
        return convertirADTO(servicioActualizado);
    }

    public void eliminarServicios(Long id) {
        serviciosRepositorio.deleteById(id);
    }

    // Métodos de conversión
    private ServiciosDTO convertirADTO(Servicios servicios) {
        return new ServiciosDTO(
                servicios.getId(),
                servicios.getNombre());
                
    }

    private Servicios convertirAEntidad(ServiciosDTO serviciosDTO) {
        Servicios servicios = new Servicios();
        servicios.setId(serviciosDTO.getId());
        servicios.setNombre(serviciosDTO.getNombre());
       
        return servicios;
    } 

     public List<Servicios> obtenerTodosServicios() {
        return serviciosRepositorio.findAll();
    }
}
