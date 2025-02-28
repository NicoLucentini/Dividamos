package app.dtos;

import app.entities.Gasto;
import app.entities.Grupo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GrupoDto implements Serializable {
    public String nombre;
    public ArrayList<String> participantes;
    public ArrayList<GastoDto> gastos;
    public Long id;

    public GrupoDto(){}
    public GrupoDto (String nombreGrupo, String... participantes){
        this.nombre = nombreGrupo;
        this.participantes = new ArrayList<String>( Arrays.asList(participantes));
    }
    public GrupoDto (String nombreGrupo, ArrayList<String> participantes){
        this.nombre = nombreGrupo;
        this.participantes = new ArrayList<String>(participantes);
    }

    public Grupo toGrupo(){
        Grupo grupo = new Grupo();
        grupo.nombre = nombre;
        grupo.participantes = new ArrayList<>(participantes);
        grupo.gastos = new ArrayList<Gasto>();
        return grupo;
    }
    public static GrupoDto fromGrupo(Grupo grupo){
        GrupoDto dto = new GrupoDto(grupo.nombre, grupo.participantes);
        dto.id = grupo.id;
        dto.gastos = grupo.gastos.stream()
                .map(GastoDto::fromGasto)
                .collect(Collectors.toCollection(ArrayList::new));
        return dto;
    }
}