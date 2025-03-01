package app.controllers;

import app.dtos.UsuarioDto;
import app.entities.Usuario;
import app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping("/crear/{nombre}/{password}")
    public ResponseEntity<String> crearUsuario(@PathVariable("nombre") String nombre,@PathVariable("password") String password){
        userService.crearUsuario(nombre, password);
        return ResponseEntity.ok("Agregado correctamente");
    }
    @GetMapping("/login/{nombre}/{password}")
    public ResponseEntity<String> loginUsuario(@PathVariable("nombre") String nombre,@PathVariable("password") String password){

        if(userService.loginUsuario(nombre, password).isPresent())
            return ResponseEntity.ok("Login correcto");

        return ResponseEntity.badRequest().body("Usuario o contraseña incorrecta");
    }

    @PostMapping("/crear")
    public ResponseEntity<Usuario> crear(@RequestBody UsuarioDto usuario){
        if(userService.findByEmail(usuario.email).isPresent())
            return ResponseEntity.badRequest().build();

        Usuario user = userService.crear(usuario);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody UsuarioDto usuario){

        Usuario user = userService.login(usuario);
        if(user != null)
            return ResponseEntity.ok(user);

        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<String> findByEmail(@PathVariable("email") String email){

       Optional<Usuario> usuario =  userService.findByEmail(email);
       if(usuario.isEmpty())
       {
         return  ResponseEntity.status(401).body("bad request");
       }
       else {
           return ResponseEntity.ok("Found");
       }
    }
}
