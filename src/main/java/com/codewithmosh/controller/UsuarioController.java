package com.codewithmosh.controller;

import com.codewithmosh.enums.RolUser;
import com.codewithmosh.model.Usuario;
import com.codewithmosh.service.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void registrarRutas() {

        before(ctx -> {
            String path = ctx.path();
            Usuario user = ctx.sessionAttribute("user");

            if (path.equals("/login") || path.equals("/") ||
                    path.equals("/registro") || path.equals("/session")) return;

            if (user == null) {
                ctx.redirect("/login");
                return;
            }

            if (path.startsWith("/usuarios") && user.getRol() != RolUser.ADMIN) {
                ctx.status(403).result("Acceso denegado");
                ctx.skipRemainingHandlers();
                return;
            }
        });

        usuarioService.generarAdmin();

        get("/", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            if (user == null) {
                ctx.redirect("/login");
            } else {
                ctx.redirect("/home");
            }
        });

        get("/login", ctx -> ctx.render("/templates/login.html"));

        get("/home", ctx -> ctx.render("/templates/home.html"));

        get("/usuarios", ctx -> ctx.render("/templates/usuarios.html"));

        get("/usuarios/lista", ctx -> {
            List<Usuario> usuarios = usuarioService.obtenerTodos();
            ctx.json(usuarios);
        });

        get("/usuarios/crear", ctx -> {
            Usuario sessionUser = ctx.sessionAttribute("user");
            if (sessionUser == null || sessionUser.getRol() != RolUser.ADMIN) {
                ctx.redirect("/login");
                return;
            }
            ctx.render("/templates/crear-usuario.html");
        });

        get("/registro", ctx -> ctx.render("/templates/registro.html"));

        get("/session", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            if (user == null) {
                ctx.status(401).result("Sin sesión");
                return;
            }
            ctx.json(user);
        });

        post("/login", ctx -> {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");

            Usuario user = usuarioService.buscarUsuarioByUsername(username);

            if (user == null || user.getPassword() == null || !user.getPassword().equals(password)) {
                Map<String, Object> model = new HashMap<>();
                model.put("error", "Usuario o contraseña incorrectos");
                model.put("username", username);
                ctx.redirect("/login?error=true");
                return;
            }

            ctx.sessionAttribute("user", user);
            ctx.redirect("/home");
        });

        post("/usuarios", ctx -> {
            Usuario user = new Usuario();
            user.setUsername(ctx.formParam("username"));
            user.setNombre(ctx.formParam("nombre"));
            user.setPassword(ctx.formParam("password"));
            user.setRol(RolUser.valueOf(ctx.formParam("rol")));

            var foto = ctx.uploadedFile("fotoPerfil");
            if (foto != null) {
                byte[] bytes = foto.content().readAllBytes();
                String base64 = "data:" + foto.contentType() + ";base64," +
                        java.util.Base64.getEncoder().encodeToString(bytes);
                user.setFotoPerfil(base64);
            }

            usuarioService.crearUsuario(user);
            ctx.redirect("/usuarios");
        });

        post("/registro", ctx -> {
            Usuario user = new Usuario();
            user.setUsername(ctx.formParam("username"));
            user.setNombre(ctx.formParam("nombre"));
            user.setPassword(ctx.formParam("password"));
            user.setRol(RolUser.PARTICIPANTE);
            usuarioService.crearUsuario(user);
            ctx.redirect("/login");
        });

        post("/usuarios/{id}/rol", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            String nuevoRol = ctx.formParam("rol");

            Usuario user = usuarioService.buscarusuarioById(id);

            if (user.getUsername().equalsIgnoreCase("admin")) {
                ctx.redirect("/usuarios");
                return;
            }

            user.setRol(RolUser.valueOf(nuevoRol));
            usuarioService.editarUsuario(user);
            ctx.redirect("/usuarios");
        });

        post("/logout", ctx -> {
            ctx.req().getSession().invalidate();
            ctx.redirect("/");
        });
    }
}