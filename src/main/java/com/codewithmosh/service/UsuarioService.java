package com.codewithmosh.service;

import com.codewithmosh.enums.RolUser;
import com.codewithmosh.model.Usuario;
import com.codewithmosh.repository.UsuarioRepository;

import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    // Dependency Injection
    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public void generarAdmin() {
        if (usuarioRepository.adminExist()) {
            return;
        }
        Usuario admin = new Usuario("admin", "admin", "Administrador", RolUser.ADMIN);
        usuarioRepository.putInMemory(admin);
        usuarioRepository.crearUsuario(admin);
    }

    public void crearUsuario(Usuario user){
        usuarioRepository.crearUsuario(user);
        usuarioRepository.putInMemory(user);
    }

    public void editarUsuario (Usuario user){
        usuarioRepository.editarUsuario(user);
    }

    public void eliminarUsuario (Usuario user){
        usuarioRepository.eliminarUsuario(user);
        usuarioRepository.removeInMemory(user);
    }

    public Usuario buscarUsuarioByUsername(String userName){
        return usuarioRepository.buscarUsuarioByUsername(userName);
    }

    public Usuario buscarusuarioById (Long id){
        return usuarioRepository.buscarUsuarioPorId(id);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.obtenerTodos();
    }

}
