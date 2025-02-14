package app.controllers;

import app.entities.Gasto;
import app.entities.GastoPorPersona;
import app.entities.Grupo;
import app.entities.Transaccion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;


@RestController
@RequestMapping("/grupos")
public class GrupoController {

    public ArrayList<Grupo> grupos = new ArrayList<>();

    @PostMapping("/crearGrupo")
    public void crearGrupo(@RequestBody GrupoDto grupoDto){
        grupos.add(grupoDto.toGrupo());

        for(Grupo g : grupos){
            System.out.println("grupo " + g.nombre);
            for(String participante : g.participantes){
                System.out.println("Particpante: " + participante);
            }
        }

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

       System.out.println("Gasto " + gastoDto.detalle + ", pagador " + gastoDto.nombrePagador + ", monto" + gastoDto.monto);
       for(String prestados : gastoDto.nombresPrestados){
           System.out.println("Prestados: " + prestados);
       }

       return ResponseEntity.ok().body("Agregado correctamente");
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




}
class CalcularGastosResponse implements Serializable{
    public ArrayList<GastoPorPersona> gastoPorPersonas;
    public ArrayList<Transaccion> transacciones;

    public CalcularGastosResponse(ArrayList<GastoPorPersona> gastoPorPersonas, ArrayList<Transaccion> transacciones)
    {
        this.gastoPorPersonas = new ArrayList<>(gastoPorPersonas);
        this.transacciones = new ArrayList<>(transacciones);
    }
}
class GrupoDto implements Serializable {
    public String nombre;
    public ArrayList<String> participantes;

    public Grupo toGrupo(){
        Grupo grupo = new Grupo();
        grupo.nombre = nombre;
        grupo.participantes = new ArrayList<>(participantes);
        grupo.gastos = new ArrayList<>();
        return grupo;
    }
}
class GastoDto implements Serializable{
    public float monto;
    public String detalle;
    public String nombrePagador;
    public ArrayList<String> nombresPrestados;

    public Gasto toGasto(){
        Gasto gasto = new Gasto();
        gasto.monto = monto;
        gasto.nombresPrestados = new ArrayList<>(nombresPrestados);
        gasto.detalle = detalle;
        gasto.nombrePagador = nombrePagador;
        return gasto;
    }
}
