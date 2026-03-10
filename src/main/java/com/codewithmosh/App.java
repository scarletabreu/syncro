package com.codewithmosh;

import com.codewithmosh.config.H2Server;
import com.codewithmosh.controller.EventoController;
import com.codewithmosh.controller.InscripcionController;
import com.codewithmosh.controller.UsuarioController;
import com.codewithmosh.model.Evento;
import com.codewithmosh.model.Inscripcion;
import com.codewithmosh.model.Usuario;
import com.codewithmosh.repository.EventoRepository;
import com.codewithmosh.repository.GestionDataBase;
import com.codewithmosh.repository.InscripcionRepository;
import com.codewithmosh.repository.UsuarioRepository;
import com.codewithmosh.service.EventoService;
import com.codewithmosh.service.InscripcionService;
import com.codewithmosh.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.rendering.FileRenderer;
import static io.javalin.apibuilder.ApiBuilder.*;
import java.io.IOException;
import java.io.InputStream;

public class App {
    public static void main(String[] args) {
        H2Server.start();

        GestionDataBase<Usuario> db = new GestionDataBase<>(Usuario.class);
        UsuarioRepository usuarioRepository = new UsuarioRepository(db);
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        UsuarioController usuarioController = new UsuarioController(usuarioService);

        GestionDataBase<Evento> dbEvento = new GestionDataBase<>(Evento.class);
        EventoRepository eventoRepository = new EventoRepository(dbEvento);
        EventoService eventoService = new EventoService(eventoRepository);
        GestionDataBase<Inscripcion> dbInscripcion = new GestionDataBase<>(Inscripcion.class);
        InscripcionRepository inscripcionRepository = new InscripcionRepository(dbInscripcion);
        InscripcionService inscripcionService = new InscripcionService(inscripcionRepository, eventoRepository);
        InscripcionController inscripcionController = new InscripcionController(inscripcionService, eventoService);
        EventoController eventoController = new EventoController(eventoService, inscripcionService);

        Javalin app = Javalin.create(config -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            config.jsonMapper(new JavalinJackson(mapper, true));

            config.fileRenderer((filePath, model, ctx) -> {
                try {
                    InputStream stream = App.class.getResourceAsStream(filePath);
                    if (stream == null) throw new RuntimeException("Archivo no encontrado: " + filePath);
                    return new String(stream.readAllBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            config.routes.apiBuilder(() -> {
                usuarioController.registrarRutas();
                eventoController.registrarRutas();
                inscripcionController.registrarRutas();
            });
        });

        int port = Integer.parseInt(
                System.getenv().getOrDefault("PORT", "7070")
        );

        app.start(port);
    }
}