import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Resolución del problema de bin packing mediante backtracking con poda por cota inferior (lower bound).
// A diferencia del backtracking puro, calcula en cada nodo del árbol cuántos contenedores
// se necesitarán como mínimo para los objetos restantes y poda si esa cota supera la mejor solución conocida.
public class AlmacenajeContenedoresRyP {
    // Capacidad máxima de cada contenedor
    static int capacidadC;
    // Array de tamaños de los objetos a distribuir
    static int[] conjuntoS;
    // Número mínimo de contenedores encontrado hasta el momento (mejor solución global)
    static int mejorK;
    // Asignación actual en exploración: asignacion[i] = índice del contenedor que recibe el objeto i
    static int[] asignacion;
    // Mejor asignación encontrada (copia de asignacion cuando se mejora mejorK)
    static int[] mejorAsignacion;
    // Ocupación actual de cada contenedor: contenedoresActuales[c] = suma de objetos asignados al contenedor c
    static int[] contenedoresActuales;

    // Función recursiva de backtracking con poda por cota inferior.
    // indexObject: índice del próximo objeto a asignar.
    // numContenedoresUsados: número de contenedores abiertos hasta el momento.
    public static void resolver(int indexObject, int numContenedoresUsados) {
        // Calcula la suma de los tamaños de los objetos que quedan por asignar
        int sumaRestante = 0;
        for (int i = indexObject; i < conjuntoS.length; i++)
            sumaRestante += conjuntoS[i];

        // Lower bound: espacio libre en contenedores ya abiertos puede absorber parte de sumaRestante
        // Calcula el espacio libre total en los contenedores ya abiertos
        int espacioLibre = 0;
        for (int i = 0; i < numContenedoresUsados; i++)
            espacioLibre += capacidadC - contenedoresActuales[i];

        // Volumen de objetos restantes que no cabe en los contenedores actuales y necesita nuevos contenedores
        int necesario = Math.max(0, sumaRestante - espacioLibre);
        // Cota inferior: mínimo de contenedores nuevos requeridos (división entera redondeada hacia arriba)
        int lowerBound = (necesario + capacidadC - 1) / capacidadC;

        // Poda: si contenedores ya usados + cota inferior >= mejor solución conocida,
        // esta rama no puede mejorarla y se abandona
        if (numContenedoresUsados + lowerBound >= mejorK)
            return;

        // Caso base: todos los objetos asignados; actualiza la mejor solución si se usaron menos contenedores
        if (indexObject == conjuntoS.length) {
            mejorK = numContenedoresUsados;
            // Guarda la asignación actual como la mejor encontrada hasta ahora
            System.arraycopy(asignacion, 0, mejorAsignacion, 0, conjuntoS.length);
            return;
        }

        // Ramificación 1: intenta añadir el objeto a un contenedor ya abierto que tenga espacio suficiente
        for (int i = 0; i < numContenedoresUsados; i++) {
            if (contenedoresActuales[i] + conjuntoS[indexObject] <= capacidadC) {
                // Añade el objeto al contenedor i (avanzar)
                contenedoresActuales[i] += conjuntoS[indexObject];
                asignacion[indexObject] = i;
                // Llama recursivamente con el siguiente objeto
                resolver(indexObject + 1, numContenedoresUsados);
                // Retrocede: quita el objeto del contenedor i para explorar otras opciones
                contenedoresActuales[i] -= conjuntoS[indexObject];
            }
        }

        // Ramificación 2: abre un nuevo contenedor para el objeto actual.
        // Solo se hace si abrir uno más no iguala ya la mejor solución conocida
        if (numContenedoresUsados + 1 < mejorK) {
            // Coloca el objeto solo en el nuevo contenedor (avanzar)
            contenedoresActuales[numContenedoresUsados] = conjuntoS[indexObject];
            asignacion[indexObject] = numContenedoresUsados;
            // Llama recursivamente incrementando el contador de contenedores usados
            resolver(indexObject + 1, numContenedoresUsados + 1);
            // Retrocede: vacía el nuevo contenedor
            contenedoresActuales[numContenedoresUsados] = 0;
        }
    }

    // Punto de entrada desde línea de comandos: lee el nombre del fichero del primer argumento e imprime resultados.
    public static void main(String[] args) throws NumberFormatException, IOException {
        main(args[0], true);
    }

    // Versión parametrizada: carga el fichero, inicializa variables y lanza el backtracking.
    // El parámetro imprimir controla si se muestra la solución por consola (útil para mediciones de tiempo).
    public static void main(String fichero, boolean imprimir) throws NumberFormatException, IOException {
        // Abre el fichero y lee la capacidad de los contenedores de la primera línea
        BufferedReader br = new BufferedReader(new FileReader(fichero));
        capacidadC = Integer.parseInt(br.readLine().trim());
        // Segunda línea: tamaños de los objetos separados por espacios
        String[] tokens = br.readLine().trim().split("\\s+");
        br.close();

        // Inicializa el array de objetos con los tamaños leídos
        int n = tokens.length;
        conjuntoS = new int[n];
        for (int i = 0; i < n; i++)
            conjuntoS[i] = Integer.parseInt(tokens[i]);

        // Cota superior inicial: en el peor caso cada objeto va en un contenedor propio
        mejorK = n;
        // Arrays de tamaño n para almacenar la ocupación de contenedores y las asignaciones
        contenedoresActuales = new int[n];
        asignacion = new int[n];
        mejorAsignacion = new int[n];
        for (int i = 0; i < n; i++)
            mejorAsignacion[i] = i; // solución inicial válida: cada objeto en su propio contenedor

        // Lanza el backtracking con ningún objeto asignado y ningún contenedor abierto
        resolver(0, 0);

        // Si se solicitó, imprime la distribución óptima encontrada
        if (imprimir)
            mostrarResultados();
    }

    // Imprime la distribución óptima: para cada contenedor, lista los objetos que contiene.
    private static void mostrarResultados() {
        System.out.println("Lista de contenedores y objetos contenidos:");
        for (int i = 0; i < mejorK; i++) {
            System.out.print("Contenedor " + (i + 1) + ": ");
            // Imprime los objetos cuya asignación óptima corresponde al contenedor i
            for (int j = 0; j < conjuntoS.length; j++)
                if (mejorAsignacion[j] == i)
                    System.out.print(conjuntoS[j] + " ");
            System.out.println();
        }
        System.out.println("El número de contenedores necesario es " + mejorK + ".");
    }
}
