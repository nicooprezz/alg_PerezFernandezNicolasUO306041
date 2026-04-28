import java.util.ArrayList;
import java.util.List;


/**
 * Implementaremos en esta clase una resolución para el Problema de las N-Reinas utilizando un algoritmo de Backtracking.
 * El objetivo es colocar N reinas en un tablero de ajedrez de NxN sin que ninguna se ataque entre sí; es decir, que no compartan fila, columna ni diagonal.
 * Vamos a evitar recorrer el tablero iterativamente para validar posiciones.
 * En su lugar, vamos a registrar las columnas y diagonales que ya están bajo ataque utilizando tres arreglos booleanos:
 * uno para las columnas, otro para las diagonales principales (de arriba a abajo, izquierda a derecha) y otro para las diagonales secundarias (de arriba a abajo, derecha a izquierda).
 */
public class NReinas_UO {

    private List<List<String>> soluciones; // cada lista interna de Strings representa un tablero completo donde cada String es una fila.

    private boolean[] columnas; // indica si una columna específica (de 0 a N-1) ya tiene una reina.
    private boolean[] diagonalesPrincipales; // de arriba a abajo, izda a decha (\)
    private boolean[] diagonalesSecundarias; // de arriba a abajo, decha a izda (/)

    /**
     * Método principal que inicia el algoritmo.
     * @param n de tipo int. El tamaño del tablero y el número de reinas.
     * @return soluciones, de tipo List<List<String>>. Una lista con todas las configuraciones válidas del tablero
     */
    public List<List<String>> resolverNReinas(int n) {
        // Inicializa la lista que acumulará todas las soluciones válidas encontradas
        soluciones = new ArrayList<>();

        // Inicializamos los rastreadores de ataques en columnas y diagonales
        // n columnas posibles (de 0 a n-1)
        columnas = new boolean[n];
        // Hay 2*n-1 diagonales de tipo '\' en un tablero de n×n
        diagonalesPrincipales = new boolean[2 * n -1];
        // Hay 2*n-1 diagonales de tipo '/' en un tablero de n×n
        diagonalesSecundarias = new boolean[2 * n - 1];

        // Inicializar el tablero vacío con puntos '.' (os doy un método)
        char[][] tablero = crearTablero(n);

        // Iniciar el backtracking desde la primera fila (fila 0)
        // Iniciar el backtracking
        backtracking(0, n, tablero);

        // Devuelve todas las soluciones encontradas
        return soluciones;
    }



    /**
     * Método de backtracking para colocar las reinas en el tablero.
     * @param fila, de tipo int. La fila actual en la que intentamos colocar una reina.
     * @param n,    de tipo int. El tamaño del tablero y el número de reinas.
     * @param tablero,  de tipo char[][]. La representación actual del tablero con las reinas colocadas.
     */
    private void backtracking(int fila, int n, char[][] tablero) {
        // Caso base: si logramos llegar a la fila n, colocamos todas las reinas con éxito
        if(fila == n){
            // Convierte el tablero actual a lista de Strings y lo añade como solución válida
            soluciones.add(construirSolucion(tablero));
            return;
        }

        // Prueba cada columna de la fila actual como posición candidata para la reina
        for (int col = 0; col < n; col++) {

            // Fórmulas matemáticas para identificar la diagonal a la que pertenece una celda
            // El índice de la diagonal '\' se obtiene restando la columna a la fila
            // y desplazando (n-1) para evitar índices negativos
            int d1 = fila - col + (n - 1); // Diagonal principal (\)
            // El índice de la diagonal '/' es la suma de fila y columna
            int d2 = fila + col;           // Diagonal secundaria (/)

            // Si la columna o alguna de las dos diagonales ya está bajo ataque, esta posición no es válida
            if (columnas[col] || diagonalesPrincipales[d1] || diagonalesSecundarias[d2]) continue;


            // 1. Avanzar -> Colocar la reina (meter un 'Q') y marcar la columna y diagonales como atacadas
            tablero[fila][col] = 'Q';
            columnas[col] = true;
            diagonalesPrincipales[d1] = true;
            diagonalesSecundarias[d2] = true;

            // 2. backtracking
            // Llama recursivamente para intentar colocar la reina de la fila siguiente
            backtracking(fila + 1, n, tablero);

            // 3. Retroceder
            // Deshace la colocación para explorar otras columnas en esta misma fila
            tablero[fila][col] = '.';
            columnas[col] = false;
            diagonalesPrincipales[d1] = false;
            diagonalesSecundarias[d2] = false;

        }
    }

    /**
     * Método auxiliar para crear un tablero vacío representado como una matriz de caracteres
     * @param n, de tipo int. El tamaño del tablero (NxN)
     * @return tablero, de tipo char[][]. Un tablero inicializado con '.' indicando casillas vacias
     */
     private char[][] crearTablero(int n) {
        char[][] tablero = new char[n][n];
        // Rellena todas las casillas con '.' para representar que están vacías
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tablero[i][j] = '.';
            }
        }
        return tablero;
    }

    /**
    * Método auxiliar para convertir la matriz de caracteres en una lista de Strings
    */
    private List<String> construirSolucion(char[][] tablero) {
        List<String> solucion = new ArrayList<>();
        // Convierte cada fila del tablero (array de chars) en un String y lo añade a la lista
        for (char[] fila : tablero) {
            solucion.add(new String(fila));
        }
        return solucion;
    }

    /**
     * Método auxiliar para pintar el tablero por consola
     */
    private static void pintarTablero(List<List<String>> resultado) {
        // Itera sobre todas las soluciones encontradas y las imprime numeradas
        for (int i = 0; i < resultado.size(); i++) {
            System.out.println("Solución " + (i + 1) + ":");
            // Imprime cada fila del tablero como una línea de texto
            for (String fila : resultado.get(i)) {
                System.out.println(fila);
            }
            System.out.println();
        }
    }

    /**
     *  Método principal para ejecutar el programa
     *  @param args, el primer argumento es el valor de N, o se usará 4 por defecto.
     */
    public static void main(String[] args) {
        NReinas_UO algoritmo = new NReinas_UO();
        // Si se pasa N como argumento de línea de comandos se usa ese valor; si no, 8 por defecto
        int n = args != null && args.length > 0 ? Integer.parseInt(args[0]) : 8;
        List<List<String>> resultado = algoritmo.resolverNReinas(n);

        // Imprime el número total de soluciones encontradas para el tablero de tamaño n
        System.out.println("Se encontraron " + resultado.size() + " soluciones para N = " + n + "\n");

        pintarTablero(resultado);
    }


}
