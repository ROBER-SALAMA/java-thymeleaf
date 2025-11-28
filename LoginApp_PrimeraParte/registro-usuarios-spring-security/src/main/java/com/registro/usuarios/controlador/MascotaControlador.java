package com.registro.usuarios.controlador;


import com.registro.usuarios.servicio.MascotaServicio;
import com.registro.usuarios.servicio.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mascotas")
public class MascotaControlador {

    @Autowired
    private MascotaServicio mascotaServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    // Mostrar lista de mascotas
    @GetMapping
    public String listarMascotas(Model model) {
        model.addAttribute("mascotas", mascotaServicio.listarTodasLasMascotas());
        return "mascotas/lista";
    }

    // Mostrar formulario para crear nueva mascota
    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("mascota", new MascotaDTO());
        model.addAttribute("clientes", clienteServicio.listarTodosLosClientes());
        return "mascotas/formulario";
    }

    // Guardar mascota nueva
    @PostMapping("/guardar")
    public String guardarMascota(@ModelAttribute("mascota") MascotaDTO mascotaDTO) {
        mascotaServicio.guardarMascota(mascotaDTO);
        return "redirect:/mascotas";
    }

    // Mostrar formulario para editar mascota
    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model model) {
        model.addAttribute("mascota", mascotaServicio.obtenerMascotaPorId(id));
        model.addAttribute("clientes", clienteServicio.listarTodosLosClientes());
        return "mascotas/formulario";
    }

    // Actualizar mascota existente
    @PostMapping("/actualizar/{id}")
    public String actualizarMascota(@PathVariable Long id, @ModelAttribute("mascota") MascotaDTO mascotaDTO) {
        mascotaServicio.actualizarMascota(id, mascotaDTO);
        return "redirect:/mascotas";
    }

    // Eliminar mascota
    @GetMapping("/eliminar/{id}")
    public String eliminarMascota(@PathVariable Long id) {
        mascotaServicio.eliminarMascota(id);
        return "redirect:/mascotas";
    }
}
