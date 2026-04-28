package colorear_grafo.time;

import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import colorear_grafo.module.*;

/* Esta clase mide tiempos del metodo realizarVoraz
leyendo los ficheros del disco duro.
En el comando de ejecución hay que pasar como argumento
el número de repeticiones deseado. */

// Clase de medición de rendimiento del algoritmo voraz de coloreo de grafos.
// Lee grafos de distintos tamaños (desde n=4 hasta n=65536 duplicando en cada paso)
// y mide el tiempo total acumulado de múltiples repeticiones para estimar la complejidad empírica.
public class DevoradorTiempos {

    public static void main(String arg[]) {
        // Variables para marcar el inicio y el fin de cada ejecución
        long t1, t2;
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduce el número de repeticiones (ej. 1000) y pulsa Enter: ");
        // Lee el número de veces que se repetirá el algoritmo para cada tamaño de entrada
        int repeticiones = teclado.nextInt();
        teclado.close();

        // Parser de JSON para leer los ficheros de grafos
        JSONParser parser = new JSONParser();

        // Itera sobre tamaños de entrada en potencias de 2: 4, 8, 16, ..., 65536
        for (int n = 4; n <= 65536; n *= 2) {
            // Acumulador del tiempo total invertido en las repeticiones para este n
            long t = 0;
            // Construye la ruta al fichero del grafo de tamaño n
            String ruta = "files/files_repo/g" + n + ".json";

            try (FileReader reader = new FileReader(ruta)) {
                // Parsea el fichero JSON y extrae el objeto de grafo
                JSONObject jsonObject = (JSONObject) parser.parse(reader);
                JSONObject grafoJson = (JSONObject) jsonObject.get("grafo");
                // Convierte el grafo JSON al formato Map<String, List<String>> que usa el algoritmo
                Map<String, List<String>> grafo = Devorador.convertJsonToString(grafoJson);

                // Ejecuta el algoritmo el número de veces indicado, acumulando el tiempo
                for (int i = 1; i <= repeticiones; i++) {

                    // Marca el instante antes de ejecutar el algoritmo
                    t1 = System.currentTimeMillis();

                    // Ejecuta el coloreo voraz sobre el grafo (el resultado no se almacena en medición)
                    ColoreoGrafo.realizarVoraz(grafo);

                    // Marca el instante después de la ejecución y suma la diferencia al acumulador
                    t2 = System.currentTimeMillis();
                    t = t + (t2 - t1);
                }

                // Imprime el tamaño, el tiempo total acumulado y el número de repeticiones
                System.out.println("n=" + n + "\tTiempo=" + t + "  \tRepeticiones=" + repeticiones);

            } catch (Exception e) {
                // Si el fichero no existe para ese tamaño, lo indica y continúa con el siguiente
                System.out.println("n=" + n + "\t[Fichero no encontrado: " + ruta + "]");
            }
        }
    }
}
