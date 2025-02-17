package app.controllers.responses;

import app.entities.GastoPorPersona;
import app.entities.Transaccion;

import java.io.Serializable;
import java.util.ArrayList;

public class CalcularGastosResponse implements Serializable {
    public ArrayList<GastoPorPersona> gastoPorPersonas;
    public ArrayList<Transaccion> transacciones;

    public CalcularGastosResponse(ArrayList<GastoPorPersona> gastoPorPersonas, ArrayList<Transaccion> transacciones)
    {
        this.gastoPorPersonas = new ArrayList<>(gastoPorPersonas);
        this.transacciones = new ArrayList<>(transacciones);
    }
}
