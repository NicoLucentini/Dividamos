import app.controllers.GrupoController;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class ControllerTest {

    GrupoController grupoController;

    public ControllerTest(){
        grupoController = new GrupoController();
    }

    @Test
    public void agregarParticipanteInexistente(){
        grupoController.grupos.add(GrupoTest.crearGrupo("Prueba", "Nicolas", "Franco"));
        grupoController.agregarParticipante("Prueba","Julieta");
        Assert.assertEquals(3, grupoController.grupos.getFirst().participantes.size());
    }
    @Test
    public void agregarParticipanteDuplicado(){
        grupoController.grupos.add(GrupoTest.crearGrupo("Prueba", "Nicolas", "Franco"));
        ResponseEntity<String> response = grupoController.agregarParticipante("Prueba","Nicolas");
        Assert.assertEquals(2, grupoController.grupos.getFirst().participantes.size());
        Assert.assertEquals(HttpStatusCode.valueOf(400),response.getStatusCode());
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
