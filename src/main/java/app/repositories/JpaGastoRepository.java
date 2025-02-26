package app.repositories;

import app.entities.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGastoRepository extends JpaRepository<Gasto, Long> {
}
