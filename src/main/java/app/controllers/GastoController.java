package app.controllers;

import app.dtos.GastoDto;
import app.entities.Gasto;
import app.services.GastoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        Gasto real = response.get();
        Gasto gasto = gastoDto.toGastoEditable();
        gasto.id = id;
        gasto.idGrupo = real.idGrupo;

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
