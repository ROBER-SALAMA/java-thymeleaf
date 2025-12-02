package com.registro.usuarios.controlador;

import com.registro.usuarios.modelo.Cita;
import com.registro.usuarios.modelo.Mascota;
import com.registro.usuarios.modelo.Servicios;
import com.registro.usuarios.servicio.CitaServicio;
import com.registro.usuarios.servicio.MascotaServicio;
import com.registro.usuarios.servicio.ServiciosServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/citas")
public class CitaControlador {

    @Autowired
    private CitaServicio citaServicio;

    @Autowired
    private MascotaServicio mascotaServicio;

    @Autowired
    private ServiciosServicio servicioServicio;

    @InitBinder
    public void initBinder(org.springframework.web.bind.WebDataBinder binder) {
        // Registrar un editor personalizado para LocalDate
        binder.registerCustomEditor(LocalDate.class, new org.springframework.beans.propertyeditors.CustomDateEditor(
                new java.text.SimpleDateFormat("yyyy-MM-dd"), true));

        // Registrar un editor personalizado para LocalTime
        binder.registerCustomEditor(LocalTime.class, new org.springframework.beans.propertyeditors.CustomDateEditor(
                new java.text.SimpleDateFormat("HH:mm"), true));

        // Trimmer para strings
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    // Listar todas las citas
    @GetMapping
    public String listarCitas(Model modelo) {
        List<Cita> citas = citaServicio.obtenerTodasCitas();
        modelo.addAttribute("citas", citas);
        return "citas/listar";
    }

    // Mostrar formulario para crear nueva cita
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaCita(Model modelo) {
        Cita cita = new Cita();
        List<Mascota> mascotas = mascotaServicio.obtenerTodasMascotas();
        List<Servicios> servicios = servicioServicio.obtenerTodosServicios();

        modelo.addAttribute("cita", cita);
        modelo.addAttribute("mascotas", mascotas);
        modelo.addAttribute("servicios", servicios);

        return "citas/formulario";
    }

    // Guardar nueva cita
    @PostMapping
    public String guardarCita(@ModelAttribute("cita") Cita cita) {
        citaServicio.guardarCita(cita);
        return "redirect:/citas";
    }

    // Mostrar formulario para editar cita
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCita(@PathVariable Long id, Model modelo) {
        Cita cita = citaServicio.obtenerCitaPorId(id);
        List<Mascota> mascotas = mascotaServicio.obtenerTodasMascotas();
        List<Servicios> servicios = servicioServicio.obtenerTodosServicios();

        modelo.addAttribute("cita", cita);
        modelo.addAttribute("mascotas", mascotas);
        modelo.addAttribute("servicios", servicios);

        return "citas/formulario";
    }

    // Eliminar cita
    @GetMapping("/eliminar/{id}")
    public String eliminarCita(@PathVariable Long id) {
        citaServicio.eliminarCita(id);
        return "redirect:/citas";
    }

    // Ver detalles de cita
    @GetMapping("/detalles/{id}")
    public String verDetallesCita(@PathVariable Long id, Model modelo) {
        Cita cita = citaServicio.obtenerCitaPorId(id);
        modelo.addAttribute("cita", cita);
        return "citas/detalles";
    }
}