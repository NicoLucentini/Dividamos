import app.entities.Gasto;
import app.entities.GastoPorPersona;
import app.entities.Grupo;
import app.repositories.impl.InMemoryGrupoRepository;
import app.services.GrupoService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class GrupoTest {
    public GrupoTest(){}

    @Test
    public void liquidarGastosDondeTodosParticipan(){
        Grupo grupo = new Grupo("Prueba1", "Nicolas", "Nicolas1","Nicolas2");

        grupo.gastos = new ArrayList<>();

        Gasto gasto1 = crearGasto("Coomida", "Nicolas", 1200, "Nicolas1", "Nicolas2");
        Gasto gasto2 = crearGasto("Bebidas", "Nicolas2", 600, "Nicolas1", "Nicolas");
        Gasto gasto3 = crearGasto("Nafta", "Nicolas1", 2100, "Nicolas2", "Nicolas");

        grupo.gastos.add(gasto1);
        grupo.gastos.add(gasto2);
        grupo.gastos.add(gasto3);

        ArrayList<GastoPorPersona> gastos = grupo.liquidarGastos();

        for(GastoPorPersona gasto : gastos){
            System.out.println("Total persona " + gasto.nombre + ", valor " + gasto.gasto);
        }

        Assert.assertEquals(-100, (float) gastos.stream().filter(x -> x.nombre == "Nicolas").findFirst().get().gasto, 0.0);
        Assert.assertEquals(800, (float) gastos.stream().filter(x -> x.nombre == "Nicolas1").findFirst().get().gasto, 0.0);
        Assert.assertEquals(-700, (float) gastos.stream().filter(x -> x.nombre == "Nicolas2").findFirst().get().gasto, 0.0);
    }

    @Test
    public void liquidarGastosDondeUnoNoAporto(){
        Grupo grupo = new Grupo("Prueba1", "Nicolas", "Nicolas1","Nicolas2");

        grupo.gastos = new ArrayList<>();

        Gasto gasto1 = crearGasto("Coomida", "Nicolas", 1200, "Nicolas1", "Nicolas2");
        Gasto gasto2 = crearGasto("Bebidas", "Nicolas1", 600, "Nicolas", "Nicolas2");
        Gasto gasto3 = crearGasto("Nafta", "Nicolas1", 2100, "Nicolas2", "Nicolas");

        grupo.gastos.add(gasto1);
        grupo.gastos.add(gasto2);
        grupo.gastos.add(gasto3);

        ArrayList<GastoPorPersona> gastos = grupo.liquidarGastos();

        for(GastoPorPersona gasto : gastos){
            System.out.println("Total persona " + gasto.nombre + ", valor " + gasto.gasto);
        }

        Assert.assertEquals(-100, (float) gastos.stream().filter(x -> x.nombre == "Nicolas").findFirst().get().gasto, 0.0);
        Assert.assertEquals(1400, (float) gastos.stream().filter(x -> x.nombre == "Nicolas1").findFirst().get().gasto, 0.0);
        Assert.assertEquals(-1300, (float) gastos.stream().filter(x -> x.nombre == "Nicolas2").findFirst().get().gasto, 0.0);
    }

    @Test
    public void liquidarGastosDondeUnoNoParticipa(){
        Grupo grupo = new Grupo("Prueba1", "Nicolas", "Nicolas1","Nicolas2");

        grupo.gastos = new ArrayList<>();

        Gasto gasto1 = crearGasto("Coomida", "Nicolas", 1200, "Nicolas1");
        Gasto gasto3 = crearGasto("Nafta", "Nicolas1", 2000,  "Nicolas");

        grupo.gastos.add(gasto1);
        grupo.gastos.add(gasto3);

        ArrayList<GastoPorPersona> gastos = grupo.liquidarGastos();

        for(GastoPorPersona gasto : gastos){
            System.out.println("Total persona " + gasto.nombre + ", valor " + gasto.gasto);
        }

        Assert.assertEquals(-400, (float) gastos.stream().filter(x -> x.nombre == "Nicolas").findFirst().get().gasto, 0.0);
        Assert.assertEquals(400, (float) gastos.stream().filter(x -> x.nombre == "Nicolas1").findFirst().get().gasto, 0.0);
    }

    @Test
    public void ordenarPagos(){

        Grupo grupo = new Grupo("Prueba1", "Nicolas", "Nicolas1","Nicolas2", "Nicolas3","Nicolas4");

        grupo.gastos = new ArrayList<>();

        Gasto gasto1 = crearGasto("Coomida", "Nicolas", 1200, "Nicolas1", "Nicolas2");
        Gasto gasto2 = crearGasto("Bebidas", "Nicolas2", 600, "Nicolas1", "Nicolas");
        Gasto gasto3 = crearGasto("Nafta", "Nicolas1", 2100, "Nicolas2", "Nicolas");
        Gasto gasto4 = crearGasto("Bebidas2", "Nicolas4", 5000, "Nicolas1", "Nicolas", "Nicolas3", "Nicolas2");
        Gasto gasto5 = crearGasto("Nafta2", "Nicolas1", 4000, "Nicolas2", "Nicolas", "Nicolas4", "Nicolas3");

        grupo.gastos.add(gasto1);
        grupo.gastos.add(gasto2);
        grupo.gastos.add(gasto3);
        grupo.gastos.add(gasto4);
        grupo.gastos.add(gasto5);

        ArrayList<GastoPorPersona> gastos = grupo.liquidarGastos();
        for(GastoPorPersona gasto : gastos){
            System.out.println("Total persona " + gasto.nombre + ", valor " + gasto.gasto);
        }
        new GrupoService(new InMemoryGrupoRepository()).calcularTransacciones(gastos);
    }

    private Gasto crearGasto(String detalle, String nombrePagador, float monto, String... nombrePrestados){
        Gasto gasto = new Gasto();
        gasto.detalle = detalle;
        gasto.nombrePagador = nombrePagador;
        gasto.monto = monto;
        gasto.nombresPrestados = new ArrayList<String>(Arrays.asList(nombrePrestados));
        return gasto;
    }

}
