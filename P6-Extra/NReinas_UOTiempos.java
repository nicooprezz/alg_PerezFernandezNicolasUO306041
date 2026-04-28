import java.util.Scanner;

/* Esta clase mide tiempos del metodo realizarVoraz
leyendo los ficheros del disco duro.
En el comando de ejecución hay que pasar como argumento
el número de repeticiones deseado. */

// Clase de medición de rendimiento del algoritmo de N-Reinas.
// Resuelve el problema para tableros de tamaño 1×1 hasta N×N (siendo N el valor introducido)
// y mide el tiempo acumulado total de todas las ejecuciones para observar el crecimiento del coste.
public class NReinas_UOTiempos {

    public static void main(String arg[]) {
        // Variables para marcar el inicio y fin de cada ejecución del algoritmo
        long t1, t2;
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduce el número máximo repeticiones (sobre 15) y pulsa Enter: ");
        // Número máximo N: el algoritmo se ejecutará para tableros de tamaño 1 hasta N
        int repeticiones = teclado.nextInt();
        teclado.close();
        // Instancia del algoritmo N-Reinas reutilizada en todas las iteraciones
        NReinas_UO algoritmoReinas = new NReinas_UO();
        // Acumulador del tiempo total de todas las ejecuciones
        long t = 0;

        // Para cada valor de i, resuelve el problema de las i-reinas en un tablero de i×i
        for (int i = 1; i <= repeticiones; i++) {

            // Marca el instante antes de resolver el problema de tamaño i
            t1 = System.currentTimeMillis();

            // Ejecuta el algoritmo para un tablero de i×i (el resultado no se usa aquí)
            algoritmoReinas.resolverNReinas(i);

            // Marca el instante de fin y acumula el tiempo transcurrido
            t2 = System.currentTimeMillis();
            t = t + (t2 - t1);
        }

        // Imprime el tiempo total acumulado y el número máximo de tablero procesado
        System.out.println("\tTiempo=" + t + "  \tRepeticiones=" + repeticiones);


    }
}
