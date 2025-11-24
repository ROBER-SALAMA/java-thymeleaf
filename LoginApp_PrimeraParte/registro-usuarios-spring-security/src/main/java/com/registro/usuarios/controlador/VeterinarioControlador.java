/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.controlador;


import com.registro.usuarios.servicio.VeterinarioServicio;
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
@RequestMapping("/veterinarios")
public class VeterinarioControlador {
    @Autowired
    private VeterinarioServicio veterinarioServicio;

    // Mostrar lista de productos
    @GetMapping
    public String listarVeterinarios(Model model) {
        model.addAttribute("veterinarios", veterinarioServicio.listarTodosLosVeterinarios());
        return "veterinarios/lista";
    }

    // Mostrar formulario para crear nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("veterinario", new VeterinarioDTO());
        return "veterinarios/formulario";
    }

    // Guardar nuevo producto
    @PostMapping("/guardar")
    public String guardarVeterinario(@ModelAttribute("veterinario") VeterinarioDTO veterinarioDTO) {
        veterinarioServicio.guardarVeterinario(veterinarioDTO);
        return "redirect:/veterinarios";
    }

    // Mostrar formulario para editar producto
    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model model) {
        model.addAttribute("veterinario", veterinarioServicio.obtenerVeterinarioPorId(id));
        return "veterinarios/formulario";
    }

    // Actualizar producto existente
    @PostMapping("/actualizar/{id}")
    public String actualizarVeterinario(@PathVariable Long id, @ModelAttribute("veterinario") VeterinarioDTO veterinarioDTO) {
        veterinarioServicio.actualizarVeterinario(id, veterinarioDTO);
        return "redirect:/veterinarios";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminarVeterinario(@PathVariable Long id) {
        veterinarioServicio.eliminarVeterinario(id);
        return "redirect:/veterinarios";
    }
}
