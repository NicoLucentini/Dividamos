package app.repositories.impl;

import app.entities.Gasto;
import app.repositories.GastoRepository;
import app.repositories.JpaGastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("jpa")
public class JpaGastoRepositoryWrapper implements GastoRepository {

    @Autowired
    private JpaGastoRepository jpaGastoRepository;

    @Override
    public Optional<Gasto> findById(Long id) {
        return jpaGastoRepository.findById(id);
    }

    @Override
    public Gasto save(Gasto gasto) {
        return jpaGastoRepository.save(gasto);
    }

    @Override
    public void delete(Long id) {
        jpaGastoRepository.deleteById(id);
    }
}
