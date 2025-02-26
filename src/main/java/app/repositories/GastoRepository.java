package app.repositories;

import app.entities.Gasto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GastoRepository {
    Optional<Gasto> findById(Long id);
    Gasto save(Gasto gasto);
    void delete(Long id);
}
