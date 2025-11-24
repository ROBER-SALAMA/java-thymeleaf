/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.servicio;




import com.registro.usuarios.controlador.VeterinarioDTO;
import com.registro.usuarios.modelo.Veterinario;
import com.registro.usuarios.repositorio.VeterinarioRepositorio;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jacqu
 */
@Service
public class VeterinarioServicio {
     @Autowired
    private VeterinarioRepositorio veterinarioRepositorio;

    public List<VeterinarioDTO> listarTodosLosVeterinarios() {
        return veterinarioRepositorio.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public VeterinarioDTO obtenerVeterinarioPorId(Long id) {
        Veterinario veterinario = veterinarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));
        return convertirADTO(veterinario);
    }

    public VeterinarioDTO guardarVeterinario(VeterinarioDTO veterinarioDTO) {
        Veterinario veterinario = convertirAEntidad(veterinarioDTO);
        Veterinario veterinarioSaved = veterinarioRepositorio.save(veterinario);
        return convertirADTO(veterinarioSaved);
    }

    public VeterinarioDTO actualizarVeterinario(Long id, VeterinarioDTO veterinarioDTO) {
        Veterinario veterinarioExistente = veterinarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

        veterinarioExistente.setNombre(veterinarioDTO.getNombre());
        veterinarioExistente.setApellido(veterinarioDTO.getApellido());
        veterinarioExistente.setCorreo(veterinarioDTO.getCorreo());
        veterinarioExistente.setTelefono(veterinarioDTO.getTelefono());
        

        Veterinario veterinarioctualizado = veterinarioRepositorio.save(veterinarioExistente);
        return convertirADTO(veterinarioctualizado);
    }

    public void eliminarVeterinario(Long id) {
        veterinarioRepositorio.deleteById(id);
    }

    // Métodos de conversión
    private VeterinarioDTO convertirADTO(Veterinario veterinario) {
        return new VeterinarioDTO(
                veterinario.getId(),
                veterinario.getNombre(),
                veterinario.getApellido(),
                veterinario.getCorreo(),
                veterinario.getTelefono());
                
    }

    private Veterinario convertirAEntidad(VeterinarioDTO veterinarioDTO) {
        Veterinario veterinario = new Veterinario();
        veterinario.setId(veterinarioDTO.getId());
        veterinario.setNombre(veterinarioDTO.getNombre());
        veterinario.setApellido(veterinarioDTO.getApellido());
        veterinario.setCorreo(veterinarioDTO.getCorreo());
        veterinario.setTelefono(veterinarioDTO.getTelefono());
       
        return veterinario;
    }
}
