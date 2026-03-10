package com.codewithmosh.model;

import com.codewithmosh.enums.RolUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdUser;
    private String username;
    private String password;
    private String nombre;
    private RolUser rol;

    @Column(columnDefinition = "TEXT")
    private String fotoPerfil;

    public Usuario(String username, String password, String nombre, RolUser rol) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.rol = rol;
    }
    public Usuario() {}
}
