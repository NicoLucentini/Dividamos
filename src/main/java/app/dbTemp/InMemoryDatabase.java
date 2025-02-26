package app.dbTemp;

import app.entities.Gasto;
import app.entities.Grupo;
import app.entities.Usuario;

import java.util.ArrayList;

public class InMemoryDatabase {


    public static ArrayList<Grupo> grupos = new ArrayList<>();
    public static ArrayList<Gasto> gastos = new ArrayList<>();
    public static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
}
