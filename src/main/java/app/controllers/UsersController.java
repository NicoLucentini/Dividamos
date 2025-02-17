package app.controllers;

import app.entities.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsersController {

    public ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

    @PostMapping("/crear/{nombre}/{password}")
    public ResponseEntity<String> crearUsuario(@PathVariable("nombre") String nombre,@PathVariable("password") String password){
        usuarios.add(new Usuario(nombre, password));
        return ResponseEntity.ok("Agregado correctamente");
    }
    @GetMapping("/login/{nombre}/{password}")
    public ResponseEntity<String> loginUsuario(@PathVariable("nombre") String nombre,@PathVariable("password") String password){
        Optional<Usuario> response = usuarios.stream().filter(x -> x.nombre.equals(nombre) && x.password.equals(password)).findFirst();

        if(response.isEmpty())
            return ResponseEntity.badRequest().body("Usuario o contraseña incorrecta");

        return ResponseEntity.ok("Registado correctamente");
    }


}
