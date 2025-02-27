package app.repositories.impl;

import app.dbTemp.InMemoryDatabase;
import app.entities.Gasto;
import app.repositories.GastoRepository;

import java.util.ArrayList;
import java.util.Optional;

public class InMemoryGastoRepository implements GastoRepository {

    public static Long ID_GASTOS = 0L;

    @Override
    public Optional<Gasto> findById(Long id) {
        return InMemoryDatabase.gastos.stream()
                .filter(gasto -> gasto.id == id)
                .findFirst();
    }

    @Override
    public Gasto save(Gasto gasto) {
        Optional<Gasto> response = findById(gasto.id);
        if(response.isEmpty()) {
            gasto.id = ++ID_GASTOS;
            InMemoryDatabase.gastos.add(gasto);
        }
        else {
            Gasto oldGasto = response.get();
            oldGasto.monto = gasto.monto;;
            oldGasto.detalle = gasto.detalle;
            oldGasto.nombrePagador = gasto.nombrePagador;
            oldGasto.nombresPrestados = new ArrayList<>(gasto.nombresPrestados);
        }
        return gasto;
    }

    @Override
    public void delete(Long id) {
        if(findById(id).isPresent())
            InMemoryDatabase.gastos.removeIf(x-> x.id == id);
    }
}
