package app.repositories;

import app.entities.Grupo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoRepository {
    Optional<Grupo> findById(Long id);
    Grupo save(Grupo grupo);
    void delete(Long id);
    Optional<Grupo> findByNombre(String nombre);

    List<Grupo> findAll();
}
