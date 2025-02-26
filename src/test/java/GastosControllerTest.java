import app.controllers.GastoController;
import app.controllers.GrupoController;
import app.dbTemp.InMemoryDatabase;
import app.dtos.GastoDto;
import app.entities.Gasto;
import app.entities.Grupo;
import app.repositories.impl.InMemoryGastoRepository;
import app.services.GastoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class GastosControllerTest {

    private GrupoController grupoController;
    private GastoController gastosController;
    private GastoService gastoService;

    public GastosControllerTest(){}

    @Before
    public void setup(){

        gastoService = new GastoService(new InMemoryGastoRepository());
        grupoController = new GrupoController(gastoService);
        gastosController = new GastoController(gastoService);
        InMemoryDatabase.grupos.clear();
        InMemoryDatabase.gastos.clear();
        InMemoryGastoRepository.ID_GASTOS = 0;
        Grupo.ID_GRUPO = 0;

    }

    @Test
    public void editarGastoExistente(){
        Grupo grupo = new Grupo("Prueba1", "Nicolas", "Julieta", "Franco");
        InMemoryDatabase.grupos.add(grupo);
        grupoController.agregarGasto(1, GastoDto.fromGasto(new Gasto("Detalle", "Nicolas",100, "Julieta")));

        //Request
        GastoDto dto = new GastoDto("Detalle", "Nicolas", 55, "Julieta", "Franco");

        ResponseEntity<String> response = gastosController.editarGasto( 1, dto);

        Assert.assertEquals(55f,InMemoryDatabase.gastos.get(0).monto, 0.0);
        Assert.assertEquals(2, InMemoryDatabase.gastos.get(0).nombresPrestados.size());

        Assert.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
    @Test
    public void eliminarGastoExistente(){

        Grupo grupo = new Grupo("Prueba1", "Nicolas", "Julieta", "Franco");
        InMemoryDatabase.grupos.add(grupo);
        grupoController.agregarGasto(1, GastoDto.fromGasto(new Gasto("Detalle", "Nicolas",100, "Julieta")));

        ResponseEntity<String> response = gastosController.eliminarGasto(1);

        Assert.assertEquals(0, InMemoryDatabase.gastos.size());
        Assert.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
    @Test
    public void eliminarGastoNoExistente(){

        Grupo grupo = new Grupo("Prueba1", "Nicolas", "Julieta", "Franco");
        grupo.gastos.add(new Gasto("Detalle", "Nicolas",100, "Julieta"));
        InMemoryDatabase.grupos.add(grupo);

        ResponseEntity<String> response = gastosController.eliminarGasto( 2);

        Assert.assertEquals(1, InMemoryDatabase.grupos.get(0).gastos.size());
        Assert.assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
    }
}
