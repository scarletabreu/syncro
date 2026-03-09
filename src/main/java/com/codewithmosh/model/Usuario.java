package com.codewithmosh.model;

import com.codewithmosh.enums.RolUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public Usuario (String username, String password, String nombre, RolUser rol){
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.rol = rol;
    }

    public Usuario() {

    }
}
