package org.example;

import lombok.*;
import java.util.ArrayList;

@AllArgsConstructor @RequiredArgsConstructor @Data
public class Persona {
    @NonNull @Getter @Setter
    private String nombre;
    @Getter @Setter
    private ArrayList<Pronostico> pronosticos;
    @Getter @Setter
    private Integer puntos;
    @Getter @Setter
    private Integer rondas;

    @Override
    public String toString() {
        return "- " + nombre + ":\nPuntos: "
                + puntos + "\nRondas: " + rondas + "\n";
    }
}
