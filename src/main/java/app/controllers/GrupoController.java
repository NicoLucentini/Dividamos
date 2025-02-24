package app.controllers;

import app.controllers.responses.CalcularGastosResponse;
import app.dbTemp.InMemoryDatabase;
import app.dtos.GastoDto;
import app.dtos.GrupoDto;
import app.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/grupos")
public class GrupoController {


    @Autowired
    public UsersController usersController;

    @PostMapping("/crearGrupo")
    public ResponseEntity<String> crearGrupo(@RequestBody GrupoDto grupoDto){

        if(duplicateString(grupoDto.participantes))
            return ResponseEntity.badRequest().body("El Participante ya existe");

        if(InMemoryDatabase.grupos.stream().anyMatch(x -> x.nombre.equals(grupoDto.nombre)))
            return ResponseEntity.badRequest().body("El nombre del grupo ya existe");

        InMemoryDatabase.grupos.add(grupoDto.toGrupo());

        return ResponseEntity.ok("Grupo creado correctamente");
    }

    @GetMapping("/get/{idUser}")
    public ResponseEntity<ArrayList<GrupoDto>> obtenerGrupos(@PathVariable("idUser") int idUser){

        Optional<Usuario> res = InMemoryDatabase.usuarios.stream().filter(x->x.id == idUser).findFirst();
        if(res.isEmpty())
        {
            return ResponseEntity.badRequest().body(null);
        }

        ArrayList<Grupo> gruposFiltered = InMemoryDatabase.grupos.stream().filter(x-> x.participantes.contains(res.get().nombre))
                .collect(Collectors.toCollection(ArrayList::new));
        return ResponseEntity.ok(
                gruposFiltered.stream()
                        .map(GrupoDto::fromGrupo).collect(Collectors.toCollection(ArrayList::new))
        );

    }
    @GetMapping("/getGastos/{idUser}/{idGrupo}")
    public ResponseEntity<ArrayList<GastoDto>> obtenerGastos(@PathVariable("idUser") int id, @PathVariable("idGrupo") int idGrupo){

        Optional<Usuario> res = InMemoryDatabase.usuarios.stream().filter(x->x.id == id).findFirst();

        if(res.isEmpty())
            return ResponseEntity.badRequest().body(null);

        Optional<Grupo> grupo = findGrupoByID(idGrupo);

        if(grupo.isEmpty())
            return ResponseEntity.badRequest().body(null);

        return ResponseEntity.ok(
                grupo.get().gastos.stream()
                        .map(GastoDto::fromGasto).collect(Collectors.toCollection(ArrayList::new))
        );

    }

    @PostMapping("/agregarParticipante/{idGrupo}/{participante}")
    public ResponseEntity<String> agregarParticipante(@PathVariable("idGrupo") int idGrupo, @PathVariable("participante") String participante){
        Optional<Grupo> response = InMemoryDatabase.grupos.stream().filter(x->x.id == idGrupo).findFirst();

        if(response.isEmpty())
            return ResponseEntity.badRequest().body("No se ha encontrado el grupo");

        Grupo grupo = response.get();
        if(grupo.participantes.contains(participante))
            return ResponseEntity.badRequest().body("El Participante ya existe");

        grupo.participantes.add(participante);
        return ResponseEntity.ok("El participante: " + participante + " ha sido agregado al grupo "+ grupo.nombre);
    }

    @DeleteMapping("/eliminarParticipante/{idGrupo}/{participante}")
    public ResponseEntity<String> eliminarParticipante(@PathVariable("idGrupo") int idGrupo, @PathVariable("participante") String participante){

        Optional<Grupo> g =findGrupoByID(idGrupo);
        if(g.isEmpty())
            return ResponseEntity.badRequest().body("El nombre del grupo ya existe");

        Grupo grupo1 = g.get();
        if(!grupo1.participantes.contains(participante))
            return ResponseEntity.badRequest().body("El nombre del participante no existe");

        grupo1.participantes.remove(participante);
        return ResponseEntity.ok("Participante Eliminado: " + participante);

    }

    @PostMapping("/agregarGasto/{idGrupo}")
    public ResponseEntity<String> agregarGasto(@PathVariable("idGrupo") int idGrupo, @RequestBody GastoDto gastoDto){
       Optional<Grupo> response = findGrupoByID(idGrupo);

       if(response.isEmpty())
           return ResponseEntity.badRequest().body("No se ha encontrado el grupo");

       Grupo grupo = response.get();

       if(!grupo.participantes.containsAll(gastoDto.nombresPrestados) || !grupo.participantes.contains(gastoDto.nombrePagador))
           return ResponseEntity.badRequest().body("Hay algun participante que no esta en el grupo");

       grupo.gastos.add(gastoDto.toGasto());

       return ResponseEntity.ok().body("Agregado correctamente");
    }

    @PutMapping("/editarGasto/{idGrupo}/{id}")
    public ResponseEntity<String> editarGasto(@PathVariable("idGrupo") int idGrupo, @PathVariable("id") int id, @RequestBody GastoDto gastoDto){
        Optional<Grupo> response = findGrupoByID(idGrupo);

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

    @DeleteMapping("/eliminarGasto/{idGrupo}/{id}")
    public ResponseEntity<String> eliminarGasto(@PathVariable("idGrupo") int idGrupo, @PathVariable("id") int id){

        Optional<Grupo> response = findGrupoByID(idGrupo);
        if(response.isEmpty())
            return ResponseEntity.badRequest().body("No se ha encontrado el grupo");

        Optional<Gasto> gasto = response.get().gastos.stream().filter(x-> x.id == id).findFirst();

        if(gasto.isEmpty())
            return ResponseEntity.badRequest().body("No se ha encontrado el gasto");

        response.get().gastos.removeIf(x-> x.id == id);

        return ResponseEntity.ok("Gasto editado correctamente");
    }
    @GetMapping("/calcularGastos/{idGrupo}")
    public ResponseEntity<CalcularGastosResponse> calcularGastos(@PathVariable("idGrupo") int idGrupo){
        Optional<Grupo> response =findGrupoByID(idGrupo);

        if(response.isEmpty())
            return ResponseEntity.badRequest().body(null);

        Grupo grupo = response.get();

        ArrayList<GastoPorPersona> gastoPorPersonas = grupo.liquidarGastos();
        ArrayList<Transaccion> transacciones = grupo.calcularTransacciones(gastoPorPersonas);

        CalcularGastosResponse calcularGastosResponse = new CalcularGastosResponse(gastoPorPersonas, transacciones);

        return ResponseEntity.ok(calcularGastosResponse);
    }

    private Optional<Grupo> findGrupoByID(int id){
        return InMemoryDatabase.grupos.stream().filter(x->x.id == id).findFirst();
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


