package app.repositories.impl;

import app.entities.Usuario;
import app.repositories.JpaUserRepository;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("jpa")
public class JpaUserRepositoryWrapper implements UserRepository {

    @Autowired
    private JpaUserRepository jpaUserRepository;


    @Override
    public Optional<Usuario> find(int id) {
        return jpaUserRepository.findById((long) id);
    }

    @Override
    public Optional<Usuario> findBy(String name, String password) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return jpaUserRepository.save(usuario);
    }

    @Override
    public void delete(int id) {
        jpaUserRepository.deleteById((long)id);
    }
}
