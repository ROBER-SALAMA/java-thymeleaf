package com.registro.usuarios.servicio;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.registro.usuarios.controlador.UsuarioRegistroDTO;
import com.registro.usuarios.modelo.Usuario;

public interface UsuarioServicio extends UserDetailsService {

    public Usuario guardar(UsuarioRegistroDTO registroDTO);

    public List<Usuario> listarUsuarios();

    public Usuario obtenerPorId(Long id);

    public void guardarExistente(Usuario usuario);

    public void eliminar(Long id);

}
