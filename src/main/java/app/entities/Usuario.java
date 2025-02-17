package app.entities;

public class Usuario {

    public static int USERS_ID = 0;
    public int id;
    public String nombre;
    public String password;

    public Usuario(String nombre, String password){
        USERS_ID++;
        this.id = USERS_ID;
        this.nombre = nombre;
        this.password = password;
    }
    public Usuario(String nombre){
       this(nombre, "");
    }
}
