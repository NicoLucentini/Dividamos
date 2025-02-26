package app.repositories.impl;

import app.dbTemp.InMemoryDatabase;
import app.entities.Usuario;
import app.repositories.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("in-memory")
public class InMemoryUserRepository implements UserRepository {


    public static Long USERS_ID = 0L;

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
        usuario.setId(++USERS_ID);
       InMemoryDatabase.usuarios.add(usuario);
       return usuario;
    }

    @Override
    public void delete(int id) {
        Optional<Usuario> usuario = find(id);
        usuario.ifPresent(value -> InMemoryDatabase.usuarios.remove(value));
    }
}
