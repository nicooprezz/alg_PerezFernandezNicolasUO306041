package module;

import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/* Esta clase mide tiempos del metodo resolver
leyendo los ficheros del disco duro.
En el comando de ejecución hay que pasar como argumento
el número de repeticiones deseado. */

// Clase de medición de rendimiento del algoritmo de almacenaje en contenedores (backtracking).
// Ejecuta el algoritmo sobre los casos de prueba test00 a test09, repitiendo cada uno
// el número de veces indicado para obtener el tiempo total acumulado por caso.
public class AlmacenajeContenedoresTiempos {

    public static void main(String[] args) {
        // Variables para capturar instantes de tiempo antes y después de cada ejecución
        long t1, t2;
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduce el número de repeticiones (ej. 10) y pulsa Enter: ");
        // Número de veces que se ejecutará el algoritmo por cada caso de prueba
        int repeticiones = teclado.nextInt();
        teclado.close();

        // PrintStream que descarta toda la salida; se usa para silenciar el algoritmo durante la medición
        PrintStream silencio = new PrintStream(OutputStream.nullOutputStream());

        // Itera sobre los 10 casos de prueba (test00.txt a test09.txt)
        for (int n = 0; n <= 9; n++) {
            // Acumulador de tiempo total para este caso de prueba
            long t = 0;
            // Construye la ruta al fichero del caso de prueba n
            String ruta = "alg_AlmacenajeContendores/CasosPrueba/test0" + n + ".txt";

            try (Scanner sc = new Scanner(new FileReader(ruta))) {
                // Primera línea: capacidad de los contenedores
                int capacidad = sc.nextInt();
                sc.nextLine();
                // Segunda línea: lista de tamaños de objetos separados por espacios
                String[] parts = sc.nextLine().trim().split("\\s+");
                Integer[] objetos = new Integer[parts.length];
                // Convierte los tokens a enteros
                for (int i = 0; i < parts.length; i++)
                    objetos[i] = Integer.parseInt(parts[i]);

                // Ejecuta el algoritmo el número de repeticiones indicado, midiendo cada vez
                for (int i = 1; i <= repeticiones; i++) {
                    // Copia el array de objetos para que cada repetición parta del mismo estado inicial
                    Integer[] copia = Arrays.copyOf(objetos, objetos.length);
                    AlmacenajeContenedores sol = new AlmacenajeContenedores(capacidad, copia);

                    // Marca el instante de inicio
                    t1 = System.currentTimeMillis();

                    // Redirige la salida estándar al sumidero para evitar que los prints alteren el tiempo
                    PrintStream original = System.out;
                    System.setOut(silencio);
                    sol.resolver();
                    // Restaura la salida estándar original
                    System.setOut(original);

                    // Marca el instante de fin y acumula la diferencia
                    t2 = System.currentTimeMillis();
                    t = t + (t2 - t1);
                }

                // Imprime el número de caso, el tiempo total acumulado y las repeticiones realizadas
                System.out.println("n=" + n + "\tTiempo=" + t + "  \tRepeticiones=" + repeticiones);

            } catch (Exception e) {
                // El fichero del caso no existe; lo indica y continúa con el siguiente
                System.out.println("n=" + n + "\t[Fichero no encontrado: " + ruta + "]");
            }
        }
    }
}
