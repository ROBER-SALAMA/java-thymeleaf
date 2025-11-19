package com.registro.usuarios.controlador;

import com.registro.usuarios.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    // Mostrar lista de productos
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoServicio.listarTodosLosProductos());
        return "productos/lista";
    }

    // Mostrar formulario para crear nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("producto", new ProductoDTO());
        return "productos/formulario";
    }

    // Guardar nuevo producto
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") ProductoDTO productoDTO) {
        productoServicio.guardarProducto(productoDTO);
        return "redirect:/productos";
    }

    // Mostrar formulario para editar producto
    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoServicio.obtenerProductoPorId(id));
        return "productos/formulario";
    }

    // Actualizar producto existente
    @PostMapping("/actualizar/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute("producto") ProductoDTO productoDTO) {
        productoServicio.actualizarProducto(id, productoDTO);
        return "redirect:/productos";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoServicio.eliminarProducto(id);
        return "redirect:/productos";
    }
}