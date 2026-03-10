package com.codewithmosh.service;

import com.codewithmosh.model.Evento;
import com.codewithmosh.model.Inscripcion;
import com.codewithmosh.model.Usuario;
import com.codewithmosh.repository.InscripcionRepository;
import com.codewithmosh.repository.EventoRepository;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final EventoRepository eventoRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository, EventoRepository eventoRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.eventoRepository = eventoRepository;
    }

    public Inscripcion inscribir(Usuario usuario, Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId);

        if (evento == null) throw new RuntimeException("Evento no encontrado");
        if (evento.getCancelado()) throw new RuntimeException("El evento está cancelado");
        if (!evento.getPublicado()) throw new RuntimeException("El evento no está publicado");


        long inscritos = inscripcionRepository.contarPorEvento(evento);
        if (inscritos >= evento.getCupoMaximo()) {
            throw new RuntimeException("El evento está lleno");
        }

        inscripcionRepository.findByUsuarioYEvento(usuario, evento).ifPresent(i -> {
            throw new RuntimeException("Ya estás inscrito en este evento");
        });

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setEvento(evento);
        inscripcion.setFechaInscripcion(LocalDateTime.now());
        inscripcion.setActiva(true);
        inscripcion.setToken(UUID.randomUUID().toString());

        return inscripcionRepository.crear(inscripcion);
    }

    public void cancelarInscripcion(Usuario usuario, Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId);
        if (evento == null) throw new RuntimeException("Evento no encontrado");

        if (LocalDateTime.now().isAfter(evento.getFechaHora())) {
            throw new RuntimeException("No puedes cancelar después de la fecha del evento");
        }

        Inscripcion inscripcion = inscripcionRepository
                .findByUsuarioYEvento(usuario, evento)
                .orElseThrow(() -> new RuntimeException("No estás inscrito en este evento"));

        inscripcion.setActiva(false);
        inscripcionRepository.editar(inscripcion);
    }

    public List<Inscripcion> obtenerInscripcionesUsuario(Usuario usuario) {
        return inscripcionRepository.findByUsuario(usuario);
    }

    public String validarAsistencia(Long eventoId, Long usuarioId, String token) {
        Inscripcion inscripcion = inscripcionRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("QR inválido"));

        if (!inscripcion.getEvento().getId().equals(eventoId))
            throw new RuntimeException("QR no corresponde a este evento");

        if (!inscripcion.getUsuario().getIdUser().equals(usuarioId))
            throw new RuntimeException("QR inválido");

        if (Boolean.TRUE.equals(inscripcion.getAsistio())){
            throw new RuntimeException("Asistencia ya registrada");
        }

        inscripcion.setAsistio(true);
        inscripcionRepository.editar(inscripcion);
        return "Asistencia registrada correctamente";
    }

    public int totalInscritos(Long eventoId) {
        return inscripcionRepository.findByEvento(eventoId).size();
    }

    public int totalAsistentes(Long eventoId) {
        return (int) inscripcionRepository.findByEvento(eventoId)
                .stream()
                .filter(i -> Boolean.TRUE.equals(i.getAsistio()))
                .count();
    }
}