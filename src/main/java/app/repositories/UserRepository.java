package app.repositories;

import app.entities.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
     Optional<Usuario> find(int id);
     Optional<Usuario> findBy(String name, String password);
     Usuario save(Usuario usuario);
     void delete(int id);
}
