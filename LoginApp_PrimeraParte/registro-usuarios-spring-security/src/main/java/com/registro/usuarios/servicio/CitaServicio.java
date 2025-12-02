package com.registro.usuarios.servicio;

import com.registro.usuarios.modelo.Cita;
import com.registro.usuarios.repositorio.CitaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CitaServicio {

    @Autowired
    private CitaRepositorio citaRepositorio;

    public Cita guardarCita(Cita cita) {
        return citaRepositorio.save(cita);
    }

    public List<Cita> obtenerTodasCitas() {
        return citaRepositorio.findAll();
    }

    public Cita obtenerCitaPorId(Long id) {
        Optional<Cita> optionalCita = citaRepositorio.findById(id);
        return optionalCita.orElse(null);
    }

    public void eliminarCita(Long id) {
        citaRepositorio.deleteById(id);
    }

    // Verificar si existe cita
    public boolean existeCita(Long id) {
        return citaRepositorio.existsById(id);
    }
}