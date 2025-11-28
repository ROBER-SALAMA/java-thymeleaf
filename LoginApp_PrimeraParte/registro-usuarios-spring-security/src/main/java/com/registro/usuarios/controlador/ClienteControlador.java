/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.controlador;


import com.registro.usuarios.servicio.ClienteServicio;
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
@RequestMapping("/clientes")
public class ClienteControlador {
     @Autowired
    private ClienteServicio clienteServicio;

    // Mostrar lista de productos
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteServicio.listarTodosLosClientes());
        return "clientes/lista";
    }

    // Mostrar formulario para crear nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("cliente", new ClienteDTO());
        return "clientes/formulario";
    }

    // Guardar nuevo producto
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute("cliente") ClienteDTO clienteDTO) {
        clienteServicio.guardarCliente(clienteDTO);
        return "redirect:/clientes";
    }

    // Mostrar formulario para editar producto
    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", clienteServicio.obtenerClientePorId(id));
        return "clientes/formulario";
    }

    // Actualizar producto existente
    @PostMapping("/actualizar/{id}")
    public String actualizarCliente(@PathVariable Long id, @ModelAttribute("cliente") ClienteDTO clienteDTO) {
        clienteServicio.actualizarCliente(id, clienteDTO);
        return "redirect:/clientes";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteServicio.eliminarCliente(id);
        return "redirect:/clientes";
    }
}
