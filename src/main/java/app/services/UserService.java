package app.services;

import app.entities.Usuario;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void crearUsuario(String nombre, String password) {
        userRepository.save(new Usuario(nombre,password));
    }

    public Optional<Usuario> loginUsuario(String nombre, String password) {
       return userRepository.findBy(nombre,password);
    }
}
