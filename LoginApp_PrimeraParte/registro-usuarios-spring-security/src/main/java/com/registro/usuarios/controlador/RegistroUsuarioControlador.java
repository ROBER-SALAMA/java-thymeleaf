package com.registro.usuarios.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.servicio.UsuarioServicio;

@Controller
@RequestMapping("/registro")

public class RegistroUsuarioControlador {

    private UsuarioServicio usuarioServicio;

    public RegistroUsuarioControlador(UsuarioServicio usuarioServicio) {
        super();
        this.usuarioServicio = usuarioServicio;
    }

    @ModelAttribute("usuario")
    public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO() {
        return new UsuarioRegistroDTO();
    }

    @GetMapping
    public String mostrarFormularioDeRegistro() {
        return "registro";
    }

    @PostMapping
    public String registrarCuentaDeUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO) {
        usuarioServicio.guardar(registroDTO);
        return "redirect:/registro?exito";
    }

    // FORMULARIO DE EDICIÓN
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioServicio.obtenerPorId(id);

        // Convertimos la entidad a DTO
        UsuarioRegistroDTO dto = new UsuarioRegistroDTO();
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setPassword(usuario.getPassword());

        model.addAttribute("usuario", dto);
        model.addAttribute("id", id);
        return "edit"; // nombre del HTML o JSP
    }

    // ACTUALIZAR USUARIO
    @PostMapping("/editar/{id}")
    public String actualizarUsuario(@PathVariable Long id,
            @ModelAttribute("usuario") UsuarioRegistroDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioServicio.obtenerPorId(id);

        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setPassword(usuarioDTO.getPassword());

        usuarioServicio.guardarExistente(usuarioExistente);
        return "redirect:/index"; // o "redirect:/usuarios" si tenés una lista
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioServicio.eliminar(id);
        return "redirect:/index";
    }

}
