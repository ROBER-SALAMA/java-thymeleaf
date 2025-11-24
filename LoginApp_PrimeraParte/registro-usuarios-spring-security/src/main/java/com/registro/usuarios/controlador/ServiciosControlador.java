/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.controlador;


import com.registro.usuarios.servicio.ServiciosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jacqu
 */
@Controller
@RequestMapping("/servicios")
public class ServiciosControlador {

    @Autowired
    private ServiciosServicio serviciosServicio;

    // LISTAR
    @GetMapping
    public String listarServicios(Model model) {
        model.addAttribute("servicios", serviciosServicio.listarTodosLosServicios());
        return "servicios/lista";
    }

    // FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("servicio", new ServiciosDTO());
        return "servicios/formulario";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardarServicios(@ModelAttribute("servicio") ServiciosDTO serviciosDTO) {
        serviciosServicio.guardarServicios(serviciosDTO);
        return "redirect:/servicios";
    }

    // FORMULARIO EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model model) {
        model.addAttribute("servicio", serviciosServicio.obtenerServiciosPorId(id));
        return "servicios/formulario";
    }

    // ACTUALIZAR
    @PostMapping("/actualizar/{id}")
    public String actualizarServicios(@PathVariable Long id, @ModelAttribute("servicio") ServiciosDTO serviciosDTO) {
        serviciosServicio.actualizarServicios(id, serviciosDTO);
        return "redirect:/servicios";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarServicios(@PathVariable Long id) {
        serviciosServicio.eliminarServicios(id);
        return "redirect:/servicios";
    }
}

