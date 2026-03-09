package com.codewithmosh.controller;

import com.codewithmosh.model.Usuario;
import com.codewithmosh.service.InscripcionService;
import com.codewithmosh.service.EventoService;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class InscripcionController {

    private final InscripcionService inscripcionService;
    private final EventoService eventoService;

    public InscripcionController(InscripcionService inscripcionService, EventoService eventoService) {
        this.inscripcionService = inscripcionService;
        this.eventoService = eventoService;
    }

    public void registrarRutas() {

        get("/mis-eventos", ctx -> {
            ctx.render("/templates/mis-eventos.html");
        });

        get("/mis-eventos/lista", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            ctx.json(inscripcionService.obtenerInscripcionesUsuario(user));
        });

        post("/eventos/{id}/inscribir", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            Long eventoId = Long.parseLong(ctx.pathParam("id"));

            try {
                inscripcionService.inscribir(user, eventoId);
                ctx.redirect("/mis-eventos");
            } catch (RuntimeException e) {
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                ctx.status(400).json(error);
            }
        });

        post("/eventos/{id}/cancelar-inscripcion", ctx -> {
            Usuario user = ctx.sessionAttribute("user");
            Long eventoId = Long.parseLong(ctx.pathParam("id"));

            try {
                inscripcionService.cancelarInscripcion(user, eventoId);
                ctx.redirect("/mis-eventos");
            } catch (RuntimeException e) {
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                ctx.status(400).json(error);
            }
        });

        post("/eventos/validar-qr", ctx -> {
            String body = ctx.body();
            Map<String, Object> datos = ctx.bodyAsClass(Map.class);

            Long eventoId = Long.valueOf(datos.get("eventoId").toString());
            Long usuarioId = Long.valueOf(datos.get("usuarioId").toString());
            String token = datos.get("token").toString();

            try {
                String resultado = inscripcionService.validarAsistencia(eventoId, usuarioId, token);
                ctx.json(Map.of("mensaje", resultado));
            } catch (RuntimeException e) {
                ctx.status(400).json(Map.of("error", e.getMessage()));
            }
        });
    }
}