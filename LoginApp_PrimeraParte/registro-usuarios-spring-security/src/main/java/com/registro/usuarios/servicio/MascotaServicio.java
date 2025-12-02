/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.servicio;

import com.registro.usuarios.controlador.MascotaDTO;
import com.registro.usuarios.modelo.Cliente;
import com.registro.usuarios.modelo.Mascota;
import com.registro.usuarios.repositorio.ClienteRepositorio;
import com.registro.usuarios.repositorio.MascotaRepositorio;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jacqu
 */
@Service
public class MascotaServicio {

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public List<MascotaDTO> listarTodasLasMascotas() {
        return mascotaRepositorio.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public MascotaDTO obtenerMascotaPorId(Long id) {
        Mascota mascota = mascotaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        return convertirADTO(mascota);
    }

    public MascotaDTO guardarMascota(MascotaDTO mascotaDTO) {

        Cliente cliente = clienteRepositorio.findById(mascotaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Mascota mascota = convertirAEntidad(mascotaDTO);
        mascota.setCliente(cliente);

        Mascota mascotaSaved = mascotaRepositorio.save(mascota);
        return convertirADTO(mascotaSaved);
    }

    public MascotaDTO actualizarMascota(Long id, MascotaDTO mascotaDTO) {
        Mascota mascotaExistente = mascotaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        Cliente cliente = clienteRepositorio.findById(mascotaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        mascotaExistente.setNombre(mascotaDTO.getNombre());
        mascotaExistente.setEspecie(mascotaDTO.getEspecie());
        mascotaExistente.setRaza(mascotaDTO.getRaza());
        mascotaExistente.setSexo(mascotaDTO.getSexo());
        mascotaExistente.setCliente(cliente);

        Mascota mascotaActualizada = mascotaRepositorio.save(mascotaExistente);
        return convertirADTO(mascotaActualizada);
    }

    public void eliminarMascota(Long id) {
        mascotaRepositorio.deleteById(id);
    }

    private MascotaDTO convertirADTO(Mascota mascota) {

        MascotaDTO dto = new MascotaDTO();

        dto.setId(mascota.getId());
        dto.setNombre(mascota.getNombre());
        dto.setEspecie(mascota.getEspecie());
        dto.setRaza(mascota.getRaza());
        dto.setSexo(mascota.getSexo());

        dto.setClienteId(mascota.getCliente().getId());

        dto.setNombreCliente(
                mascota.getCliente().getNombre() + " " + mascota.getCliente().getApellido());

        return dto;
    }

    private Mascota convertirAEntidad(MascotaDTO dto) {
        Mascota mascota = new Mascota();
        mascota.setId(dto.getId());
        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
        mascota.setRaza(dto.getRaza());
        mascota.setSexo(dto.getSexo());
        return mascota;
    }

    public List<Mascota> obtenerTodasMascotas() {
        return mascotaRepositorio.findAll();
    }
}
