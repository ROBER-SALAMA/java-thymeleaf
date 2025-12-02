package com.registro.usuarios.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.registro.usuarios.servicio.UsuarioServicio;

@Controller
public class RegistroControlador {

    @Autowired
    private UsuarioServicio servicio;

    @GetMapping("/login")
    public String iniciarSesion() {
        return "login";
    }

    @GetMapping("/index")
    public String verPaginaDeInicio(Model modelo) {
        modelo.addAttribute("usuarios", servicio.listarUsuarios());
        return "index";
    }

    @GetMapping("/acerca")
    public String acerca() {
        return "acerca";
    }

    @GetMapping("/ir-productos")
    public String verProductos() {
        return "redirect:/productos";
    }

    @GetMapping("/ir-servicios")
    public String verServicios() {
        return "redirect:/servicios";
    }

    @GetMapping("/ir-veterinarios")
    public String verVeterinarios() {
        return "redirect:/veterinarios";
    }

    @GetMapping("/ir-clientes")
    public String verClientes() {
        return "redirect:/clientes";
    }

    @GetMapping("/ir-mascotas")
    public String verMascotas() {
        return "redirect:/mascotas";
    }

    @GetMapping("/ir-citas")
    public String verCitas() {
        return "redirect:/citas";
    }
    
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
