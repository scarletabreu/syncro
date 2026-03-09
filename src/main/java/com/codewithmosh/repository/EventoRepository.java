package com.codewithmosh.repository;
import com.codewithmosh.model.Evento;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EventoRepository {

    private final GestionDataBase<Evento> db;

    public EventoRepository(GestionDataBase<Evento> db) {
        this.db = db;
    }

    public Evento crear(Evento evento) {
        return db.crear(evento);
    }

    public Evento editar(Evento evento) {
        return db.editar(evento);
    }

    public boolean eliminar(Long id) {
        return db.eliminar(id);
    }

    public Evento findById(Long id) {
        return db.find(id);
    }

    public List<Evento> findAll() {
        return db.findAll();
    }

    public List<Evento> findPublicados() {
        EntityManager em = db.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT e FROM Evento e WHERE e.publicado = true AND e.cancelado = false",
                            Evento.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
