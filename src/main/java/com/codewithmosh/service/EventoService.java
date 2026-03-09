package com.codewithmosh.service;

import com.codewithmosh.model.Evento;
import com.codewithmosh.model.Usuario;
import com.codewithmosh.repository.EventoRepository;
import java.util.List;

public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public Evento crear(Evento evento) {
        return eventoRepository.crear(evento);
    }

    public Evento editar(Evento evento) {
        return eventoRepository.editar(evento);
    }

    public Evento findById(Long id) {
        Evento evento = eventoRepository.findById(id);
        if (evento == null) throw new RuntimeException("Evento no encontrado: " + id);
        return evento;
    }

    public List<Evento> obtenerTodos() {
        return eventoRepository.findAll();
    }

    public List<Evento> obtenerPublicados() {
        return eventoRepository.findPublicados();
    }

    public Evento publicar(Long id) {
        Evento evento = findById(id);
        evento.setPublicado(true);
        return eventoRepository.editar(evento);
    }

    public Evento despublicar(Long id) {
        Evento evento = findById(id);
        evento.setPublicado(false);
        return eventoRepository.editar(evento);
    }

    public Evento cancelar(Long id) {
        Evento evento = findById(id);
        evento.setCancelado(true);
        return eventoRepository.editar(evento);
    }
}