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
        laberinto = cargarTablero(nFichero);
        // Guarda el tamaño del laberinto para usarlo en las validaciones de posición
        length = laberinto.length;
    }


    private void resolver(){
        // Posición de entrada: esquina superior izquierda (fila 0, columna 0)
        int fila = 0;
        int col = 0;

        //CASO BASE
        // Comprueba si la celda inicial ya es la celda final (laberinto de tamaño 1x1)
        if(length == 1)
            return;

        // Inicializa la matriz de estados con todas las celdas sin visitar
        estados[][] estado = new estados[length][length];
        for(int i = 0; i < length; i++)
            for(int j = 0; j < length; j++)
                estado[i][j] = estados.CERO;

        // Listas para acumular el camino actual y todos los caminos encontrados
        List<int[]> caminoActual = new ArrayList<>();
        List<List<int[]>> todosCaminos = new ArrayList<>();

        // Inicia la exploración recursiva desde la celda de entrada
        resolverRec(fila, col, estado, caminoActual, todosCaminos);

        // Imprime el número total de caminos y cada uno de ellos
        System.out.println("Número de caminos encontrados: " + todosCaminos.size());
        for(int i = 0; i < todosCaminos.size(); i++){
            System.out.println("Camino " + (i+1) + ":");
            for(int[] pos : todosCaminos.get(i))
                System.out.println("  (" + pos[0] + ", " + pos[1] + ")");
        }
    }

    // Método recursivo que explora todos los caminos desde (fila, col) hasta la salida.
    // Usa backtracking: marca la celda actual como UNO, explora los vecinos accesibles
    // y restaura el estado CERO al retroceder para permitir que otros caminos la visiten.
    private void resolverRec(int fila, int col, estados[][] estado,
                              List<int[]> caminoActual, List<List<int[]>> todosCaminos){
        // Caso base: se ha alcanzado la celda de salida (esquina inferior derecha)
        if(fila == length - 1 && col == length - 1){
            // Añade la posición final y guarda una copia del camino completo
            caminoActual.add(new int[]{fila, col});
            todosCaminos.add(new ArrayList<>(caminoActual));
            // Elimina la posición final para continuar explorando otros caminos
            caminoActual.remove(caminoActual.size() - 1);
            return;
        }

        // Marca la celda actual como parte del camino en exploración
        estado[fila][col] = estados.UNO;
        // Añade la posición actual al camino en construcción
        caminoActual.add(new int[]{fila, col});

        // Vectores de desplazamiento: abajo, derecha, arriba, izquierda
        int[] dFila = { 1,  0, -1,  0};
        int[] dCol  = { 0,  1,  0, -1};

        for(int d = 0; d < 4; d++){
            int nFila = fila + dFila[d];
            int nCol  = col  + dCol[d];
            // Avanza a la celda vecina si está dentro del laberinto, es transitable y aún no ha sido visitada
            if(isValid(nFila, nCol) && laberinto[nFila][nCol] != 0 && estado[nFila][nCol] == estados.CERO)
                resolverRec(nFila, nCol, estado, caminoActual, todosCaminos);
        }

        // Restaura el estado de la celda y elimina su posición del camino (backtracking)
        estado[fila][col] = estados.CERO;
        caminoActual.remove(caminoActual.size() - 1);
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
