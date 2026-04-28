package colorear_grafo;

import colorear_grafo.module.Devorador;

public class App {
    public static void main(String[] args) throws Exception {
        // 1. Capturamos el tiempo justo antes de empezar (en nanosegundos)
        long startTime = System.nanoTime();

        // 2. Ejecutamos el programa (lectura de JSON + algoritmo + escritura)
        Devorador.main(args);

        // 3. Capturamos el tiempo justo al terminar
        long endTime = System.nanoTime();

        // 4. Calculamos la diferencia
        long durationNano = endTime - startTime;

        // Lo convertimos a milisegundos para que sea más fácil de leer
        double durationMs = durationNano / 1_000_000.0;

        // 5. Imprimimos el log de rendimiento
        System.out.println("\n=========================================");
        System.out.println("⏱️  TEST DE RENDIMIENTO (TIEMPO TOTAL)");
        System.out.println("=========================================");
        System.out.printf("Tiempo en milisegundos: %.4f ms%n", durationMs);
        System.out.println("Tiempo en nanosegundos: " + durationNano + " ns");
        System.out.println("=========================================\n");
    }
}
