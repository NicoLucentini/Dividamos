package app.repositories.impl;

import app.dbTemp.InMemoryDatabase;
import app.entities.Grupo;
import app.repositories.GrupoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("in-memory")
public class InMemoryGrupoRepository implements GrupoRepository {

    public static Long ID_GRUPO = 0L;

    @Override
    public Optional<Grupo> findById(Long id) {
        return InMemoryDatabase.grupos.stream().filter(x-> x.id == id).findFirst();
    }

    @Override
    public Grupo save(Grupo grupo) {
        if(grupo.id != null){
            Optional<Grupo> response = findById((long)grupo.id);

            if(response.isPresent()){
                Grupo oldGrupo = response.get();

                oldGrupo.gastos = new ArrayList<>(grupo.gastos);
                oldGrupo.participantes = new ArrayList<>(grupo.participantes);
                oldGrupo.nombre = grupo.nombre;
                return oldGrupo;
            }
        }

        grupo.id = ++ID_GRUPO;
        InMemoryDatabase.grupos.add(grupo);
        return grupo;
    }

    @Override
    public void delete(Long id) {
        InMemoryDatabase.grupos.removeIf(x->x.id == id);
    }

    @Override
    public Optional<Grupo> findByNombre(String nombre) {
        return InMemoryDatabase.grupos.stream().filter(x-> x.nombre.equals(nombre)).findFirst();
    }

    @Override
    public List<Grupo> findAll() {
        return InMemoryDatabase.grupos;
    }
}
