package org.example;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor @RequiredArgsConstructor @Data @ToString(includeFieldNames = false)
public class Ronda {
    // Atributos
    @NonNull @Getter @Setter
    private Integer numero;
    @Getter @Setter
    protected ArrayList<Partido> partidos;

    // Métodos
    @Override
    public String toString() {
        return "Ronda " + numero +
                ": " + partidos;
    }
}