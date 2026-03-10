package com.codewithmosh.repository;

import com.codewithmosh.model.Inscripcion;
import com.codewithmosh.model.Evento;
import com.codewithmosh.model.Usuario;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class InscripcionRepository {

    private final GestionDataBase<Inscripcion> db;

    public InscripcionRepository(GestionDataBase<Inscripcion> db) {
        this.db = db;
    }

    public Inscripcion crear(Inscripcion inscripcion) {
        return db.crear(inscripcion);
    }

    public Inscripcion editar(Inscripcion inscripcion) {
        return db.editar(inscripcion);
    }

    public Inscripcion findById(Long id) {
        return db.find(id);
    }

    public Optional<Inscripcion> findByUsuarioYEvento(Usuario usuario, Evento evento) {
        EntityManager em = db.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT i FROM Inscripcion i WHERE i.usuario = :usuario AND i.evento = :evento AND i.activa = true",
                            Inscripcion.class)
                    .setParameter("usuario", usuario)
                    .setParameter("evento", evento)
                    .getResultStream()
                    .findFirst();
        } finally {
            em.close();
        }
    }

    public List<Inscripcion> findByUsuario(Usuario usuario) {
        EntityManager em = db.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT i FROM Inscripcion i WHERE i.usuario = :usuario AND i.activa = true",
                            Inscripcion.class)
                    .setParameter("usuario", usuario)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public long contarPorEvento(Evento evento) {
        EntityManager em = db.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT COUNT(i) FROM Inscripcion i WHERE i.evento = :evento AND i.activa = true",
                            Long.class)
                    .setParameter("evento", evento)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public Optional<Inscripcion> findByToken(String token) {
        EntityManager em = db.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT i FROM Inscripcion i WHERE i.token = :token",
                            Inscripcion.class)
                    .setParameter("token", token)
                    .getResultStream()
                    .findFirst();
        } finally {
            em.close();
        }
    }

    public List<Inscripcion> findByEvento(Long eventoId) {
        EntityManager em = db.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT i FROM Inscripcion i WHERE i.evento.id = :eventoId AND i.activa = true",
                            Inscripcion.class)
                    .setParameter("eventoId", eventoId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}