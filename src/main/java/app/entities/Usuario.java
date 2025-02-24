package app.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    public static Long USERS_ID = 0L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nombre;
    public String password;

    public Usuario()    {}
    public Usuario(Long id, String nombre, String password) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
    }

    public Usuario(String nombre, String password){
        //USERS_ID++;
        //this.id = USERS_ID;
        this.nombre = nombre;
        this.password = password;
    }

    public Usuario(String nombre){
       this(0L,nombre, "");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
