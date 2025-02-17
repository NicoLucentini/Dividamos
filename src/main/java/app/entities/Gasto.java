package app.entities;

import java.util.ArrayList;
import java.util.Arrays;

public class Gasto {

    public static int ID_GASTOS = 0;
    public int id;
    public float monto;
    public String detalle;
    public String nombrePagador;
    public ArrayList<String> nombresPrestados = new ArrayList<>();

    public Gasto()
    {
        ID_GASTOS++;
        this.id = ID_GASTOS;
    }
    public Gasto (String detalle, String nombrePagador, float monto, String... nombrePrestados){
        ID_GASTOS++;
        this.id = ID_GASTOS;
        this.detalle = detalle;
        this.nombrePagador = nombrePagador;
        this.monto = monto;
        this.nombresPrestados = new ArrayList<String>(Arrays.asList(nombrePrestados));
    }
    public float valorPromedio(){
        return monto / (nombresPrestados.size() + 1);
    }

}
