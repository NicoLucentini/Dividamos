package app.dtos;

import app.entities.Gasto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class GastoDto implements Serializable {
    public Long id;
    public float monto;
    public String detalle;
    public String nombrePagador;
    public ArrayList<String> nombresPrestados;
    public Long idGrupo;

    public GastoDto(){}

    public GastoDto(Long id,
                    float monto,
                    String detalle,
                    String nombrePagador,
                    ArrayList<String> nombresPrestados,
                    Long idGrupo) {
        this.id = id;
        this.monto = monto;
        this.detalle = detalle;
        this.nombrePagador = nombrePagador;
        this.nombresPrestados = new ArrayList<>(nombresPrestados);
        this.idGrupo = idGrupo;
    }


    public GastoDto (String detalle, String nombrePagador, int monto, String... nombrePrestados) {
        this.detalle = detalle;
        this.nombrePagador = nombrePagador;
        this.monto = monto;
        this.nombresPrestados = new ArrayList<>(Arrays.asList(nombrePrestados));
    }
    public GastoDto (String detalle, String nombrePagador, float monto, ArrayList<String> nombrePrestados) {
        this.detalle = detalle;
        this.nombrePagador = nombrePagador;
        this.monto = monto;
        this.nombresPrestados = new ArrayList<>(nombrePrestados);
    }



    public Gasto toGasto(){
        Gasto gasto = new Gasto();
        gasto.monto = monto;
        gasto.nombresPrestados = new ArrayList<>(nombresPrestados);
        gasto.detalle = detalle;
        gasto.nombrePagador = nombrePagador;
        gasto.id = id;
        gasto.idGrupo = idGrupo;

        return gasto;
    }
    public Gasto toGastoEditable(){
        Gasto gasto = new Gasto();
        gasto.monto = monto;
        gasto.nombresPrestados = new ArrayList<>(nombresPrestados);
        gasto.detalle = detalle;
        gasto.nombrePagador = nombrePagador;
        return gasto;
    }
    public static GastoDto fromGasto(Gasto gasto){
        return new GastoDto(gasto.id,gasto.monto,gasto.detalle,gasto.nombrePagador,gasto.nombresPrestados, gasto.idGrupo);
    }
}