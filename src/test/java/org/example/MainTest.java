package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.example.Main.*;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    String rutaPronostico = "src/main/resources/pronostico.csv";
    String rutaResultados = "src/main/resources/resultados.csv";
    ArrayList resultados = new ArrayList<>();
    ArrayList pronosticos = new ArrayList<>();
    ArrayList participantes = new ArrayList<>();

    @Test
    void main() throws IOException {
        resultados = leerResultados(rutaResultados);
        assertNotNull(resultados);

        pronosticos = leerPronostico(rutaPronostico);
        assertNotNull(pronosticos);

        participantes = compararResultados(pronosticos, resultados);
        for (int i = 0; i < participantes.size(); i++) {
            Persona participante = (Persona) participantes.get(i);
            assertNotNull(participante);
            assertNotNull(participante.getNombre());
            assertNotNull(participante.getPuntos());
            assertNotNull(participante.getRondas());
            assertEquals(2, participante.getRondas());
        }
    }
}