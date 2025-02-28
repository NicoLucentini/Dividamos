import app.controllers.GrupoController;
import app.dbTemp.InMemoryDatabase;
import app.dtos.GrupoDto;
import app.entities.Gasto;
import app.entities.Grupo;
import app.repositories.impl.InMemoryGastoRepository;
import app.repositories.impl.InMemoryGrupoRepository;
import app.services.GastoService;
import app.services.GrupoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.security.Guard;
import java.util.ArrayList;

public class ControllerTest {

    private GrupoController grupoController;

    public ControllerTest(){

    }

    @Before
    public void setup(){
        grupoController = new GrupoController(
                new GrupoService(new InMemoryGrupoRepository()),
                new GastoService(new InMemoryGastoRepository())
        );

        InMemoryDatabase.clearDatabase();
    }
    @Test
    public void agregarParticipanteInexistente(){
        grupoController.grupoService.agregar(new Grupo("Prueba", "Nicolas", "Franco"));
        grupoController.agregarParticipante(1,"Julieta");
        Assert.assertEquals(3, InMemoryDatabase.grupos.getFirst().participantes.size());
    }
    @Test
    public void agregarParticipanteDuplicado(){
        grupoController.grupoService.agregar(new Grupo("Prueba", "Nicolas", "Franco"));
        ResponseEntity<String> response = grupoController.agregarParticipante(1,"Nicolas");
        Assert.assertEquals(2, InMemoryDatabase.grupos.getFirst().participantes.size());
        Assert.assertEquals(HttpStatusCode.valueOf(400),response.getStatusCode());
    }
    @Test
    public void agregarGrupoNoExistente(){
        GrupoDto dto = new GrupoDto("Prueba1", "Nicolas", "Julieta");

        ResponseEntity<String> response = grupoController.crearGrupo(dto);

        Assert.assertEquals(1, InMemoryDatabase.grupos.size());
        Assert.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
    @Test
    public void agregarGrupoConParticipantesDuplicados(){
        GrupoDto dto = new GrupoDto("Prueba1", "Nicolas", "Julieta", "Julieta");

        ResponseEntity<String> response = grupoController.crearGrupo(dto);

        Assert.assertEquals(0, InMemoryDatabase.grupos.size());
        Assert.assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
    }
    @Test
    public void agregarGrupoConNombreGrupoDuplicado(){
        GrupoDto dto1 = new GrupoDto("Prueba1", "Nicolas", "Julieta");
        InMemoryDatabase.grupos.add(dto1.toGrupo());

        GrupoDto dto = new GrupoDto("Prueba1", "Nicolas", "Julieta");

        ResponseEntity<String> response = grupoController.crearGrupo(dto);

        Assert.assertEquals(1, InMemoryDatabase.grupos.size());
        Assert.assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
    }
    @Test
    public void eliminarParticipanteExistente(){
        GrupoDto dto1 = new GrupoDto("Prueba1", "Nicolas", "Julieta");
        grupoController.grupoService.agregar(dto1.toGrupo());

        ResponseEntity<String> response = grupoController.eliminarParticipante(1, "Nicolas");

        Assert.assertEquals(1, InMemoryDatabase.grupos.get(0).participantes.size());
        Assert.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
    @Test
    public void eliminarParticipanteNoExistente(){
        GrupoDto dto1 = new GrupoDto("Prueba1", "Nicolas", "Julieta");
        InMemoryDatabase.grupos.add(dto1.toGrupo());

        ResponseEntity<String> response = grupoController.eliminarParticipante(1, "NoExisteParticipante");

        Assert.assertEquals(2, InMemoryDatabase.grupos.get(0).participantes.size());
        Assert.assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
    }
    @Test
    public void eliminarParticipanteEnGrupoNoExistente(){
        GrupoDto dto1 = new GrupoDto("Prueba1", "Nicolas", "Julieta");
        InMemoryDatabase.grupos.add(dto1.toGrupo());

        ResponseEntity<String> response = grupoController.eliminarParticipante(2, "NoExisteParticipante");

        Assert.assertEquals(2, InMemoryDatabase.grupos.get(0).participantes.size());
        Assert.assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
    }
    @Test
    public void chequearDuplicados(){

        ArrayList<String> data = new ArrayList<String>();
        data.add("Nicolas");
        data.add("Mauri");
        data.add("Juli");
        data.add("Nicolas");

        Assert.assertTrue(GrupoController.duplicateString(data));
    }
    @Test
    public void chequearDuplicadosSinMayusculas(){

        ArrayList<String> data = new ArrayList<String>();
        data.add("nicolas");
        data.add("Mauri");
        data.add("Juli");
        data.add("Nicolas");

        Assert.assertFalse(GrupoController.duplicateString(data));
    }







}
