package org.example;

import lombok.*;

@AllArgsConstructor @RequiredArgsConstructor @Data @ToString(includeFieldNames = false)
public class Equipo {
    @NonNull @Getter @Setter
    private String nombre;
    @Getter @Setter
    private String descripcion;
}
