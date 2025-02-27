package app.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class Gasto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public float monto;
    public String detalle;
    public String nombrePagador;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    public ArrayList<String> nombresPrestados = new ArrayList<>();
    public Long idGrupo;
    public Gasto()
    {

    }
    public Gasto (String detalle, String nombrePagador, float monto, String... nombrePrestados){

        this.detalle = detalle;
        this.nombrePagador = nombrePagador;
        this.monto = monto;
        this.nombresPrestados = new ArrayList<String>(Arrays.asList(nombrePrestados));
    }
    public float valorPromedio(){
        return monto / (nombresPrestados.size() + 1);
    }

}
