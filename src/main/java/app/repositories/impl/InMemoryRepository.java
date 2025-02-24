package app.repositories.impl;

import app.dbTemp.InMemoryDatabase;
import app.entities.Usuario;
import app.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryRepository implements UserRepository {

    @Override
    public Optional<Usuario> find(int id) {
        return InMemoryDatabase.usuarios.stream().filter(x->x.id == id).findFirst();
    }

    @Override
    public Optional<Usuario> findBy(String name, String password) {
        return InMemoryDatabase.usuarios.stream().filter(x-> x.nombre.equals(name) && x.password.equals(password)).findFirst();
    }

    @Override
    public Usuario save(Usuario usuario) {
       InMemoryDatabase.usuarios.add(usuario);
       return usuario;
    }

    @Override
    public void delete(int id) {
        Optional<Usuario> usuario = find(id);
        usuario.ifPresent(value -> InMemoryDatabase.usuarios.remove(value));
    }
}
