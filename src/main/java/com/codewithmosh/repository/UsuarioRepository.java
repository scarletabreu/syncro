package com.codewithmosh.repository;

import com.codewithmosh.enums.RolUser;
import com.codewithmosh.model.Usuario;
import jakarta.persistence.EntityManager;
import org.h2.engine.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioRepository {
    private final GestionDataBase<Usuario> db;
    private final Map<Long, Usuario> usuariosInMemory = new HashMap<>();

    public UsuarioRepository(GestionDataBase db){
        this.db = db;
    }

    public Usuario putInMemory (Usuario user){
        return usuariosInMemory.put(user.getIdUser(), user);
    }

    public Usuario editInMemory(Usuario user) {
        if (user.getIdUser() != null) {
            Usuario existente = usuariosInMemory.get(user.getIdUser());

            if (existente == null) throw new RuntimeException("Usuario no encontrado");

            if (user.getUsername() != null) existente.setUsername(user.getUsername());
            if (user.getPassword() != null) existente.setPassword(user.getPassword());
            usuariosInMemory.put(user.getIdUser(), existente);
            return existente;
        }
        return null;
    }

    public Usuario removeInMemory (Usuario user){
        if (!user.getUsername().equalsIgnoreCase("admin")){
            return usuariosInMemory.remove(user.getIdUser());
        }
        return user;
    }

    public Usuario crearUsuario (Usuario user){
        return db.crear(user);
    }

    public Usuario editarUsuario (Usuario user){
        return db.editar(user);
    }

    public boolean eliminarUsuario (Usuario user){
        if (!user.getUsername().equalsIgnoreCase("admin")){
            return db.eliminar(user.getIdUser());
        }
        return false;
    }

    public Usuario buscarUsuarioPorId (Long id){
        return db.find(id);
    }

    public Usuario buscarUsuarioByUsername(String userName) {
        EntityManager em = db.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.username = :username", Usuario.class)
                    .setParameter("username", userName)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public boolean adminExist (){
        return db.findAll().stream().anyMatch(u -> ((Usuario) u).getUsername().equalsIgnoreCase("admin"));
    }

    public List<Usuario> obtenerTodos() {
        return db.findAll();
    }

}
