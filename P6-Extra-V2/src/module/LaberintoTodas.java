package module;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Clase que resuelve un laberinto mediante backtracking buscando todos los caminos posibles.
// El laberinto se representa como una matriz de enteros leída desde un fichero de texto.
public class LaberintoTodas {
    // Enumerado que representa los posibles estados de una celda durante la exploración:
    // CERO = sin visitar, UNO = en el camino actual, DOS = visitada y descartada
    private enum estados{CERO, UNO, DOS};

    // Matriz que almacena el laberinto cargado desde el fichero
    private int[][] laberinto;
    // Número de filas (y columnas) del laberinto cuadrado
    private int length;

    // Constructor: carga el laberinto desde el fichero de prueba indicado por número
    public LaberintoTodas(int nFichero){
        cargarTablero(nFichero);
        // Guarda el tamaño del laberinto para usarlo en las validaciones de posición
        length = laberinto.length;
    }


    private void resolver(){
        // Posición de salida: esquina superior izquierda (fila 0, columna 0)
        int fila = 0;
        int col = 0;

        //CASO BASE
        // Comprueba si la celda inicial ya es la celda final (laberinto de tamaño 1x1)
        if(laberinto[fila][col] == laberinto[laberinto.length][laberinto.length])
            return;




    }

    // Lee el laberinto desde el fichero "files/casoN.txt" y lo almacena en la matriz laberinto.
    // Cada línea del fichero representa una fila; los valores de cada celda están separados por espacios.
    private int[][] cargarTablero(int nFicheroPrueba){
        // Convierte el número de prueba a String para construir el nombre del fichero
        String nFichero = String.valueOf(nFicheroPrueba);
        String ruta = "files/caso" + nFichero + ".txt";

        // Lista temporal donde se acumulan las filas antes de convertir a matriz
        List<int[]> filas = new ArrayList<>();

        try (Scanner sc = new Scanner(new FileReader(ruta))) {
            // Lee el fichero línea a línea hasta que no haya más contenido
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                // Ignora las líneas vacías
                if (linea.isEmpty()) continue;
                // Divide la línea en tokens separados por uno o más espacios
                String[] partes = linea.split("\\s+");
                int[] fila = new int[partes.length];
                // Convierte cada token a entero y lo almacena en la fila
                for (int i = 0; i < partes.length; i++) {
                    fila[i] = Integer.parseInt(partes[i]);
                }
                // Añade la fila convertida a la lista de filas
                filas.add(fila);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Convierte la lista de filas a una matriz bidimensional y la devuelve
        return filas.toArray(new int[0][]);
    }

    // Comprueba si la celda (fila, columna) está dentro de los límites del laberinto.
    // Devuelve true si ambas coordenadas son no negativas y menores que el tamaño del laberinto.
    private boolean isValid(int fila, int columna){
        return (fila >= 0 && fila < laberinto.length) &&
        (columna >= 0 && columna < laberinto.length);
    }
}
