package app.services;

import app.entities.Gasto;
import app.entities.GastoPorPersona;
import app.entities.Grupo;
import app.entities.Transaccion;
import app.repositories.GrupoRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrupoService {

    private GrupoRepository grupoRepository;

    public GrupoService(GrupoRepository grupoRepository){
        this.grupoRepository = grupoRepository;
    }

    public Optional<Grupo> findByNombre(String nombre) {
        return grupoRepository.findByNombre(nombre);
    }

    public Optional<Grupo> findById(int idGrupo) {
        return grupoRepository.findById((long)idGrupo);
    }

    public Grupo agregar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }


    public ArrayList<GastoPorPersona> liquidarGastos(Grupo grupo) {

        return grupo.liquidarGastos();
    }

    public ArrayList<Transaccion> calcularTransacciones(ArrayList<GastoPorPersona> gastos){

        ArrayList<Map.Entry<String, Float>> deudores = new ArrayList<>();

        ArrayList<Map.Entry<String, Float>> acreedores = new ArrayList<>();

        // 2. Crear listas de deudores (-) y acreedores (+)

        for (GastoPorPersona gasto : gastos) {
            if (gasto.gasto < 0)
                deudores.add(new AbstractMap.SimpleEntry<>(gasto.nombre, gasto.gasto));
            else if (gasto.gasto > 0)
                acreedores.add(new AbstractMap.SimpleEntry<>(gasto.nombre, gasto.gasto));
        }

        // Ordenar deudores (de menor a mayor) y acreedores (de mayor a menor)
        deudores.sort((a, b) -> Float.compare(a.getValue(), b.getValue()));
        acreedores.sort((a, b) -> Float.compare(b.getValue(), a.getValue()));

        // 3. Procesar transacciones
        ArrayList<Transaccion> transacciones = new ArrayList<>();
        int i = 0, j = 0;

        while (i < deudores.size() && j < acreedores.size()) {
            String deudor = deudores.get(i).getKey();
            String acreedor = acreedores.get(j).getKey();
            float deuda = -deudores.get(i).getValue();
            float credito = acreedores.get(j).getValue();
            float pago = Math.min(deuda, credito);

            // Registrar transacción
            transacciones.add(new Transaccion(deudor, acreedor, pago));

            // Actualizar saldos
            deudores.get(i).setValue(deudores.get(i).getValue() + pago);
            acreedores.get(j).setValue(acreedores.get(j).getValue() - pago);

            // Avanzar si el saldo llega a 0
            if (deudores.get(i).getValue() == 0) i++;
            if (acreedores.get(j).getValue() == 0) j++;
        }

        // 4. Imprimir resultado
        System.out.println("Transacciones mínimas necesarias:");
        for (Transaccion t : transacciones) {
            System.out.println(t);
        }

        return  transacciones;
    }

    public List<Grupo> findAll() {
        return grupoRepository.findAll();
    }


}
