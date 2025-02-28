package app.repositories;

import app.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaGrupoRepository extends JpaRepository<Grupo,Long> {
    Optional<Grupo> findByNombre(String nombre);
}
