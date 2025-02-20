package app.dtos;

import app.entities.Gasto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class GastoDto implements Serializable {
    public float monto;
    public String detalle;
    public String nombrePagador;
    public ArrayList<String> nombresPrestados;

    public GastoDto(){}
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
        return gasto;
    }
    public static GastoDto fromGasto(Gasto gasto){
        GastoDto dto = new GastoDto(gasto.detalle,gasto.nombrePagador,gasto.monto,gasto.nombresPrestados);
        return dto;
    }
}