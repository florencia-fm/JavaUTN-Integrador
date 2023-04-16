package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        String rutaPronostico = "src/main/resources/pronostico.csv";
        String rutaResultados = "src/main/resources/resultados.csv";
        ArrayList resultados = new ArrayList<>();
        ArrayList pronosticos = new ArrayList<>();
        resultados = leerResultados(rutaResultados);
        pronosticos = leerPronostico(rutaPronostico);
        compararResultados(pronosticos, resultados);
    }

    // Función para leer los resultados
    private static ArrayList leerResultados(String rutaResultados) throws IOException {
        Path pathResultados = Paths.get(rutaResultados);
        int contadorDeRondas = 0;
        ArrayList<Ronda> rondas = new ArrayList<>();
        ArrayList<Partido> partidos = new ArrayList<>();
        try {
            for (String line: Files.readAllLines(pathResultados).stream().skip(1).toArray(String[]::new)) {
                String[] datos = line.split(",");
                int numRonda = Integer.parseInt(datos[0]);

                if (contadorDeRondas == 0) {
                    contadorDeRondas = numRonda;
                }

                if (contadorDeRondas != numRonda) {
                    Ronda ronda = new Ronda((numRonda - 1), partidos);
                    rondas.add(ronda);
                    partidos = new ArrayList<>();
                    contadorDeRondas = numRonda;
                }

                Equipo equipo1 = new Equipo (datos[1]);
                Equipo equipo2 = new Equipo (datos[4]);
                Partido partido = new Partido(equipo1, equipo2);
                partido.setGolesEquipo1(Integer.parseInt(datos[2]));
                partido.setGolesEquipo2(Integer.parseInt(datos[3]));
                partido.resultado(Integer.parseInt(datos[2]), Integer.parseInt(datos[3]));
                partidos.add(partido);
            }
            Ronda ronda = new Ronda(contadorDeRondas, partidos);
            rondas.add(ronda);

        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo");
        }
        return rondas;
    }

    // Función para leer el pronóstico
    private static ArrayList leerPronostico(String rutaPronostico) throws IOException {
        String participante = "";
        ArrayList<Persona> personas = new ArrayList<>();
        ArrayList<Pronostico> pronosticos = new ArrayList<>();
        int puntos = 0;
        try {
            Path pathPronostico = Paths.get(rutaPronostico);
            for (String line: Files.readAllLines(pathPronostico).stream().skip(1).toArray(String[]::new)) {
                String[] datos = line.split(",");

                if (participante.equals("")) {
                    participante = datos[0];
                };

                if (!datos[0].equals(participante)){
                    Persona persona = new Persona(participante, pronosticos, 0);
                    personas.add(persona);
                    pronosticos = new ArrayList<>();
                    participante = datos[0];
                }

                if (datos[0].equals(participante)) {
                    Equipo equipo1 = new Equipo (datos[1]);
                    Equipo equipo2 = new Equipo (datos[5]);
                    Partido partido = new Partido(equipo1, equipo2);
                    String gana1 = datos[2];
                    String empata = datos[3];
                    String gana2 = datos[4];
                    boolean[] ganador = {false, false, false};
                    if (gana1.equals("X")) {
                        ganador[0] = true;
                        Pronostico pronostico = new Pronostico(partido, equipo1, ResultadoEnum.GANA_EQUIPO1);
                        pronosticos.add(pronostico);

                    } else if (empata.equals("X")) {
                        ganador[1] = true;
                        Pronostico pronostico = new Pronostico(partido, null, ResultadoEnum.EMPATE);
                        pronosticos.add(pronostico);

                    } else if (gana2.equals("X")) {
                        ganador[2] = true;
                        Pronostico pronostico = new Pronostico(partido, equipo2, ResultadoEnum.GANA_EQUIPO2);
                        pronosticos.add(pronostico);

                    }
                }
            }
            Persona persona = new Persona(participante, pronosticos, 0);
            personas.add(persona);

        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo");
        }
        return personas;
    }

    // Comprar resultados vs pronósticos
    private static void compararResultados(ArrayList pronosticos, ArrayList resultados) {
        try{
            System.out.println("Puntos por persona:");
            for (int i = 0; i < pronosticos.size(); i++) {
                Persona persona = (Persona) pronosticos.get(i);
                for (int j = 0; j < persona.getPronosticos().size(); j++) {
                    Pronostico pronostico = (Pronostico) persona.getPronosticos().get(j);
                    for (int k = 0; k < resultados.size(); k++) {
                        Ronda ronda = (Ronda) resultados.get(k);
                        for (int l = 0; l < ronda.getPartidos().size(); l++) {
                            Partido partido = (Partido) ronda.getPartidos().get(l);
                            if (pronostico.getPartido().getEquipo1().getNombre().equals(partido.getEquipo1().getNombre()) && pronostico.getPartido().getEquipo2().getNombre().equals(partido.getEquipo2().getNombre())) {
                                if (pronostico.getResultado().equals(ResultadoEnum.GANA_EQUIPO1) && partido.resultado(partido.getGolesEquipo1(), partido.getGolesEquipo2()).equals(ResultadoEnum.GANA_EQUIPO1)) {
                                    persona.setPuntos(persona.getPuntos() + 1);
                                } else if (pronostico.getResultado().equals(ResultadoEnum.EMPATE) && partido.resultado(partido.getGolesEquipo1(), partido.getGolesEquipo2()).equals(ResultadoEnum.EMPATE)) {
                                    persona.setPuntos(persona.getPuntos() + 1);
                                } else if (pronostico.getResultado().equals(ResultadoEnum.GANA_EQUIPO2) && partido.resultado(partido.getGolesEquipo1(), partido.getGolesEquipo2()).equals(ResultadoEnum.GANA_EQUIPO2)) {
                                    persona.setPuntos(persona.getPuntos() + 1);
                                }
                            }
                        }
                    }
                }
                System.out.println(persona.getNombre() + ": " + persona.getPuntos());
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
