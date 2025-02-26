package app.services;

import app.entities.Gasto;
import app.repositories.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;

public class GastoService {


    private final GastoRepository gastoRepository;

    public GastoService(GastoRepository gastoRepository)
    {
        this.gastoRepository = gastoRepository;
    }
    public Optional<Gasto> findById(Long id) {
        return gastoRepository.findById(id);
    }

    public void delete(long id) {
        gastoRepository.delete(id);
    }

    public Gasto agregar(Gasto gasto) {
        return gastoRepository.save(gasto);
    }
}
