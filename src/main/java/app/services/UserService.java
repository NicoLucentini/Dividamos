package app.services;

import app.entities.Usuario;
import app.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // ✅ Best practice: Constructor injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Usuario crearUsuario(String nombre, String password) {
        return userRepository.save(new Usuario(nombre,password));
    }

    public Optional<Usuario> loginUsuario(String nombre, String password) {
       return userRepository.findBy(nombre,password);
    }
}
