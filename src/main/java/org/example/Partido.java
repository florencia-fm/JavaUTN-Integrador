package org.example;

import lombok.*;

@AllArgsConstructor @RequiredArgsConstructor @Data @ToString(includeFieldNames = false)
public class Partido {
    // Atributos
    @NonNull @Getter @Setter
    private Equipo equipo1;
    @NonNull @Getter @Setter
    private Equipo equipo2;
    @Getter @Setter
    private Integer golesEquipo1;
    @Getter @Setter
    private Integer golesEquipo2;

    // Métodos
     public ResultadoEnum resultado(int golesEquipo1, int golesEquipo2) {
         if (golesEquipo1 > golesEquipo2) {
             return ResultadoEnum.GANA_EQUIPO1;
         } else if (golesEquipo2 > golesEquipo1) {
             return ResultadoEnum.GANA_EQUIPO2;
         } else {
             return ResultadoEnum.EMPATE;
         }
     }

    @Override
    public String toString() {
        return equipo1.getNombre() +
                " - " + equipo2.getNombre() +
                ", resultado: " + golesEquipo1 +
                " - " + golesEquipo2;
    }

}
