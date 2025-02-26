package app.controllers;

import app.dbTemp.InMemoryDatabase;
import app.dtos.GastoDto;
import app.entities.Gasto;
import app.services.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/gastos")
public class GastoController {


    private final GastoService gastoService;

    public GastoController(GastoService gastoService){
        this.gastoService = gastoService;
    }


    @PutMapping("/editarGasto/{id}")
    public ResponseEntity<String> editarGasto(@PathVariable("id") long id, @RequestBody GastoDto gastoDto){
        Optional<Gasto> response =  gastoService.findById(id);

        if(response.isEmpty())
            return ResponseEntity.badRequest().body("No se ha encontrado el gasto");

        Gasto gasto = gastoDto.toGasto();
        gasto.id = (int)id;
        gastoService.agregar(gasto);

        return ResponseEntity.ok("Gasto editado correctamente");
    }



    @DeleteMapping("/eliminarGasto/{id}")
    public ResponseEntity<String> eliminarGasto(@PathVariable("id") long id){

        Optional<Gasto> response =  gastoService.findById(id);

        if(response.isEmpty())
            return ResponseEntity.badRequest().body("No se ha encontrado el gasto");

        gastoService.delete(id);



        return ResponseEntity.ok("Gasto editado correctamente");
    }

}
