package org.example;

import lombok.*;
import java.util.ArrayList;

@AllArgsConstructor @RequiredArgsConstructor @Data @ToString(includeFieldNames = false)
public class Persona {
    @NonNull @Getter @Setter
    private String nombre;
    @Getter @Setter
    private ArrayList<Pronostico> pronosticos;
    @Getter @Setter
    private Integer puntos;

}
