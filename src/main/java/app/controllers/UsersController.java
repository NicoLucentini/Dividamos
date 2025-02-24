package app.controllers;

import app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
