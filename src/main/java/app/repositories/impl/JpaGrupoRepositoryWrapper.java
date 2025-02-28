package app.repositories.impl;

import app.entities.Grupo;
import app.repositories.GrupoRepository;
import app.repositories.JpaGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaGrupoRepositoryWrapper implements GrupoRepository {

    @Autowired
    private JpaGrupoRepository jpaGrupoRepository;

    @Override
    public Optional<Grupo> findById(Long id) {
        return jpaGrupoRepository.findById(id);
    }

    @Override
    public Grupo save(Grupo grupo) {
        return jpaGrupoRepository.save(grupo);
    }

    @Override
    public void delete(Long id) {
        jpaGrupoRepository.deleteById(id);
    }

    @Override
    public Optional<Grupo> findByNombre(String nombre) {
        return jpaGrupoRepository.findByNombre(nombre);
    }

    @Override
    public List<Grupo> findAll() {
        return jpaGrupoRepository.findAll();
    }
}
