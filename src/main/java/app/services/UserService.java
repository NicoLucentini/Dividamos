package app.services;

import app.dtos.UsuarioDto;
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

    public Optional<Usuario> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public Optional<Usuario> find(int id){
        return userRepository.find(id);
    }

    public Usuario crearUsuario(String nombre, String password) {
        return userRepository.save(new Usuario(nombre,password));
    }

    public Optional<Usuario> loginUsuario(String nombre, String password) {
       return userRepository.findBy(nombre,password);
    }

    public Usuario crear(UsuarioDto usuario) {
        Usuario u = new Usuario(usuario.nombre, usuario.password, usuario.email);
        return userRepository.save(u);
    }

    public Usuario login(UsuarioDto usuario) {
        Optional<Usuario> u  = userRepository.findByEmail(usuario.email);
        if(u.isPresent() && u.get().password.equals(usuario.password))
            return u.get();
        return null;
    }
}
