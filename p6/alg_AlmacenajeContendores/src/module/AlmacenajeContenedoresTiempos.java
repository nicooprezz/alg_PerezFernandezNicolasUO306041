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

public class AlmacenajeContenedoresTiempos {

    public static void main(String[] args) {
        long t1, t2;
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduce el número de repeticiones (ej. 10) y pulsa Enter: ");
        int repeticiones = teclado.nextInt();
        teclado.close();

        PrintStream silencio = new PrintStream(OutputStream.nullOutputStream());

        for (int n = 0; n <= 9; n++) {
            long t = 0;
            String ruta = "CasosPrueba/test0" + n + ".txt";

            try (Scanner sc = new Scanner(new FileReader(ruta))) {
                int capacidad = sc.nextInt();
                sc.nextLine();
                String[] parts = sc.nextLine().trim().split("\\s+");
                Integer[] objetos = new Integer[parts.length];
                for (int i = 0; i < parts.length; i++)
                    objetos[i] = Integer.parseInt(parts[i]);

                for (int i = 1; i <= repeticiones; i++) {
                    Integer[] copia = Arrays.copyOf(objetos, objetos.length);
                    AlmacenajeContenedores sol = new AlmacenajeContenedores(capacidad, copia);

                    t1 = System.currentTimeMillis();

                    PrintStream original = System.out;
                    System.setOut(silencio);
                    sol.resolver();
                    System.setOut(original);

                    t2 = System.currentTimeMillis();
                    t = t + (t2 - t1);
                }

                System.out.println("n=" + n + "\tTiempo=" + t + "  \tRepeticiones=" + repeticiones);

            } catch (Exception e) {
                System.out.println("n=" + n + "\t[Fichero no encontrado: " + ruta + "]");
            }
        }
    }
}
