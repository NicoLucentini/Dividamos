package app.entities;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "grupos")
public class Grupo {
    public String nombre;
    public ArrayList<String> participantes = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "idGrupo", referencedColumnName = "id")
    public List<Gasto> gastos = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Grupo(){}

    public Grupo(String nombre, String... participantes){

        this.nombre = nombre;
        this.participantes = new ArrayList<>(Arrays.asList(participantes));
    }

    public ArrayList<GastoPorPersona> liquidarGastos(){
        ArrayList<GastoPorPersona> gastosPorPersona = new ArrayList<>();

        for(String participante : participantes ){
            gastosPorPersona.add(new GastoPorPersona(participante));
        }

        for(Gasto gasto : gastos){
            gastosPorPersona.stream().filter(x-> x.nombre.equals(gasto.nombrePagador))
                    .findFirst().get()
                    .agregarGasto(gasto.monto - gasto.valorPromedio());

            for(String nombrePrestado : gasto.nombresPrestados){

                gastosPorPersona.stream().filter(x-> x.nombre.equals(nombrePrestado))
                        .findFirst()
                        .get()
                        .agregarGasto(-gasto.valorPromedio());
            }
        }

        return gastosPorPersona;
    }



}

