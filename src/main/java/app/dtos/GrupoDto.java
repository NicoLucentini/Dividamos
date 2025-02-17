package app.dtos;

import app.entities.Grupo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class GrupoDto implements Serializable {
    public String nombre;
    public ArrayList<String> participantes;

    public GrupoDto (String nombreGrupo, String... participantes){
        this.nombre = nombreGrupo;
        this.participantes = new ArrayList<String>( Arrays.asList(participantes));
    }

    public Grupo toGrupo(){
        Grupo grupo = new Grupo();
        grupo.nombre = nombre;
        grupo.participantes = new ArrayList<>(participantes);
        grupo.gastos = new ArrayList<>();
        return grupo;
    }
}