package app.controllers;

import app.controllers.responses.CalcularGastosResponse;
import app.dtos.GastoDto;
import app.dtos.GrupoDto;
import app.entities.Gasto;
import app.entities.GastoPorPersona;
import app.entities.Grupo;
import app.entities.Transaccion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/grupos")
public class GrupoController {

    public ArrayList<Grupo> grupos = new ArrayList<>();

    @PostMapping("/crearGrupo")
    public ResponseEntity<String> crearGrupo(@RequestBody GrupoDto grupoDto){

        if(duplicateString(grupoDto.participantes))
            return ResponseEntity.badRequest().body("El Participante ya existe");

        if(grupos.stream().anyMatch(x -> x.nombre.equals(grupoDto.nombre)))
            return ResponseEntity.badRequest().body("El nombre del grupo ya existe");

        grupos.add(grupoDto.toGrupo());

        for(Grupo g : grupos){
            System.out.println("grupo " + g.nombre);
            for(String participante : g.participantes){
                System.out.println("Particpante: " + participante);
            }
        }
        return ResponseEntity.ok("Grupo creado correctamente");


    }
    @PostMapping("/agregarParticipante/{nombreGrupo}/{participante}")
    public ResponseEntity<String> agregarParticipante(@PathVariable("nombreGrupo") String nombreGrupo, @PathVariable("participante") String participante){
        Optional<Grupo> response = grupos.stream().filter(x->x.nombre.equals(nombreGrupo)).findFirst();

        if(response.isEmpty())
            return ResponseEntity.badRequest().body("No se ha encontrado el grupo");

        Grupo grupo = response.get();
        if(grupo.participantes.contains(participante))
            return ResponseEntity.badRequest().body("El Participante ya existe");

        grupo.participantes.add(participante);
        return ResponseEntity.ok("El participante: " + participante + " ha sido agregado al grupo "+ nombreGrupo);
    }

    @DeleteMapping("/eliminarParticipante/{grupo}/{participante}")
    public ResponseEntity<String> eliminarParticipante(@PathVariable("grupo") String grupo, @PathVariable("participante") String participante){

        Optional<Grupo> g = grupos.stream().filter(x->x.nombre.equals(grupo)).findFirst();
        if(g.isEmpty())
            return ResponseEntity.badRequest().body("El nombre del grupo ya existe");

        Grupo grupo1 = g.get();
        if(!grupo1.participantes.contains(participante))
            return ResponseEntity.badRequest().body("El nombre del participante no existe");

        grupo1.participantes.remove(participante);
        return ResponseEntity.ok("Participante Eliminado: " + participante);

    }

    @PostMapping("/agregarGasto/{nombreGrupo}")
    public ResponseEntity<String> agregarGasto(@PathVariable("nombreGrupo") String nombreGrupo, @RequestBody GastoDto gastoDto){
       Optional<Grupo> response = grupos.stream().filter(x->x.nombre.equals(nombreGrupo)).findFirst();

       if(response.isEmpty())
           return ResponseEntity.badRequest().body("No se ha encontrado el grupo");

       Grupo grupo = response.get();

       if(!grupo.participantes.containsAll(gastoDto.nombresPrestados) || !grupo.participantes.contains(gastoDto.nombrePagador))
           return ResponseEntity.badRequest().body("Hay algun participante que no esta en el grupo");

       grupo.gastos.add(gastoDto.toGasto());

       return ResponseEntity.ok().body("Agregado correctamente");
    }

    @PutMapping("/editarGasto/{grupo}/{id}")
    public ResponseEntity<String> editarGasto(@PathVariable("grupo") String grupo, @PathVariable("id") int id, @RequestBody GastoDto gastoDto){
        Optional<Grupo> response = grupos.stream().filter(x->x.nombre.equals(grupo)).findFirst();

        if(response.isEmpty())
            return ResponseEntity.badRequest().body("No se ha encontrado el grupo");

        Optional<Gasto> gasto = response.get().gastos.stream().filter(x-> x.id == id).findFirst();

        if(gasto.isEmpty())
            return ResponseEntity.badRequest().body("No se ha encontrado el gasto");

        Gasto g = gasto.get();
        g.monto = gastoDto.monto;;
        g.detalle = gastoDto.detalle;
        g.nombrePagador = gastoDto.nombrePagador;
        g.nombresPrestados = new ArrayList<>(gastoDto.nombresPrestados);

        return ResponseEntity.ok("Gasto editado correctamente");
    }

    @DeleteMapping("/eliminarGasto/{grupo}/{id}")
    public ResponseEntity<String> eliminarGasto(@PathVariable("grupo") String grupo, @PathVariable("id") int id){
        Optional<Grupo> response = grupos.stream().filter(x->x.nombre.equals(grupo)).findFirst();

        if(response.isEmpty())
            return ResponseEntity.badRequest().body("No se ha encontrado el grupo");

        Optional<Gasto> gasto = response.get().gastos.stream().filter(x-> x.id == id).findFirst();

        if(gasto.isEmpty())
            return ResponseEntity.badRequest().body("No se ha encontrado el gasto");

        response.get().gastos.removeIf(x-> x.id == id);

        return ResponseEntity.ok("Gasto editado correctamente");
    }
    @GetMapping("/calcularGastos/{nombreGrupo}")
    public ResponseEntity<CalcularGastosResponse> calcularGastos(@PathVariable("nombreGrupo") String nombreGrupo){
        Optional<Grupo> response = grupos.stream().filter(x->x.nombre.equals(nombreGrupo)).findFirst();

        if(response.isEmpty())
            return ResponseEntity.badRequest().body(null);

        Grupo grupo = response.get();

        ArrayList<GastoPorPersona> gastoPorPersonas = grupo.liquidarGastos();
        ArrayList<Transaccion> transacciones = grupo.calcularTransacciones(gastoPorPersonas);

        CalcularGastosResponse calcularGastosResponse = new CalcularGastosResponse(gastoPorPersonas, transacciones);

        return ResponseEntity.ok(calcularGastosResponse);
    }


    public static boolean duplicateString(ArrayList<String> values){

        Set<String> uniqueNames = new HashSet<>();

        for (String name : values) {
            if (!uniqueNames.add(name)) {
               return  true;
            }
        }
        return false;
    }


}


