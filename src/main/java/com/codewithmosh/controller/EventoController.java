package com.codewithmosh.controller;

import com.codewithmosh.enums.RolUser;
import com.codewithmosh.model.Evento;
import com.codewithmosh.model.Usuario;
import com.codewithmosh.service.EventoService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    private boolean puedeGestionar(Usuario user) {
        return user != null &&
                (user.getRol() == RolUser.ADMIN || user.getRol() == RolUser.ORGANIZADOR);
    }

    public void registrarRutas() {

        get("/eventos", ctx -> ctx.render("/templates/eventos.html"));

        get("/eventos/lista", ctx -> {
            List<Evento> eventos = eventoService.obtenerTodos();
            ctx.json(eventos);
        });

        get("/eventos/crear", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            if (!puedeGestionar(user)) {
                ctx.status(403).result("Acceso denegado");
                return;
            }
            ctx.render("/templates/crear-evento.html");
        });

        post("/eventos", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            if (!puedeGestionar(user)) {
                ctx.status(403).result("Acceso denegado");
                return;
            }

            Evento evento = new Evento();
            evento.setTitulo(ctx.formParam("titulo"));
            evento.setDescripcion(ctx.formParam("descripcion"));
            evento.setFechaHora(LocalDateTime.parse(ctx.formParam("fechaHora")));
            evento.setLugar(ctx.formParam("lugar"));
            evento.setCupoMaximo(Integer.parseInt(ctx.formParam("cupoMaximo")));
            evento.setOrganizador(user);

            eventoService.crear(evento);
            ctx.redirect("/eventos");
        });

        get("/eventos/{id}/datos", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            ctx.json(eventoService.findById(id));
        });

        get("/eventos/{id}/editar", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            if (!puedeGestionar(user)) {
                ctx.status(403).result("Acceso denegado");
                return;
            }
            ctx.render("/templates/editar-evento.html");
        });

        get("/eventos/escanear", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            if (!puedeGestionar(user)) {
                ctx.status(403).result("Acceso denegado");
                return;
            }
            ctx.render("/templates/escanear.html");
        });

        get("/home/stats", ctx -> {
            List<Evento> todos = eventoService.obtenerTodos();
            List<Evento> publicados = eventoService.obtenerPublicados();

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalEventos", todos.size());
            stats.put("eventosPublicados", publicados.size());
            ctx.json(stats);
        });

        post("/eventos/{id}/editar", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            if (!puedeGestionar(user)) {
                ctx.status(403).result("Acceso denegado");
                return;
            }

            Long id = Long.parseLong(ctx.pathParam("id"));
            Evento evento = eventoService.findById(id);

            evento.setTitulo(ctx.formParam("titulo"));
            evento.setDescripcion(ctx.formParam("descripcion"));
            evento.setFechaHora(LocalDateTime.parse(ctx.formParam("fechaHora")));
            evento.setLugar(ctx.formParam("lugar"));
            evento.setCupoMaximo(Integer.parseInt(ctx.formParam("cupoMaximo")));

            eventoService.editar(evento);
            ctx.redirect("/eventos");
        });

        post("/eventos/{id}/cancelar", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            if (!puedeGestionar(user)) {
                ctx.status(403).result("Acceso denegado");
                return;
            }
            Long id = Long.parseLong(ctx.pathParam("id"));
            eventoService.cancelar(id);
            ctx.redirect("/eventos");
        });

        post("/eventos/{id}/publicar", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            if (!puedeGestionar(user)) {
                ctx.status(403).result("Acceso denegado");
                return;
            }
            Long id = Long.parseLong(ctx.pathParam("id"));
            eventoService.publicar(id);
            ctx.redirect("/eventos");
        });

        post("/eventos/{id}/despublicar", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            if (!puedeGestionar(user)) {
                ctx.status(403).result("Acceso denegado");
                return;
            }
            Long id = Long.parseLong(ctx.pathParam("id"));
            eventoService.despublicar(id);
            ctx.redirect("/eventos");
        });
    }
}