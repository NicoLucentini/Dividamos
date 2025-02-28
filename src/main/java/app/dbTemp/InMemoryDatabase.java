package app.dbTemp;

import app.entities.Gasto;
import app.entities.Grupo;
import app.entities.Usuario;
import app.repositories.impl.InMemoryGastoRepository;
import app.repositories.impl.InMemoryGrupoRepository;
import app.repositories.impl.InMemoryUserRepository;

import java.util.ArrayList;

public class InMemoryDatabase {

    public static ArrayList<Grupo> grupos = new ArrayList<>();
    public static ArrayList<Gasto> gastos = new ArrayList<>();
    public static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

    public static void clearDatabase(){
        grupos.clear();
        gastos.clear();
        usuarios.clear();

        InMemoryGrupoRepository.ID_GRUPO = 0L;
        InMemoryGastoRepository.ID_GASTOS = 0L;
        InMemoryUserRepository.ID_USERS = 0L;
    }
}
