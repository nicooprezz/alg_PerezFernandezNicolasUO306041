package module;

import java.util.LinkedList;
import java.util.List;

// Resuelve el problema del ferry mediante programación dinámica.
// El barco tiene dos carriles (babor y estribor) de igual longitud.
// El objetivo es maximizar el número de vehículos que embarcan distribuyéndolos entre ambos carriles.
public class Ferry {

    private int boatLength; // longitud de los carriles del barco
    private List<Integer> vehicles; // lista de vehiculos
    // dp[i][l] = true indica que es posible haber cargado los primeros i vehículos
    // con exactamente l metros acumulados en el carril de babor
    private boolean[][] dp; // matriz con las posibles soluciones
    private int[] sumatorio; // suma acumulada de las longitudes de los vehiculos
    private List<Step> path; // variable para guardar el camino seleccionado

    public Ferry(int boatLength, List<Integer> vehicles) {
        this.boatLength = boatLength;
        this.vehicles = vehicles;
        // La tabla dp tiene (n+1) filas (de 0 a n vehículos) y (boatLength+1) columnas (de 0 a boatLength)
        this.dp = new boolean[vehicles.size() + 1][boatLength + 1];
        // El array de sumatorios tiene n+1 posiciones; sumatorio[i] = suma de las longitudes de los primeros i vehículos
        this.sumatorio = new int[vehicles.size() + 1];
        // Con 0 vehículos la suma acumulada es 0
        this.sumatorio[0] = 0;
        // Calcula los sumatorios prefijos de las longitudes de los vehículos
        calcularSumatorio();
        // Lista enlazada para reconstruir el camino de la solución óptima
        this.path = new LinkedList<Step>();
    }

    // Precalcula la suma acumulada (prefijos) de las longitudes de los vehículos.
    // sumatorio[i] = longitud del vehículo 1 + ... + longitud del vehículo i.
    private void calcularSumatorio() {
        for (int i = 1; i <= vehicles.size(); i++) {
            // Añade la longitud del i-ésimo vehículo al total acumulado anterior
            this.sumatorio[i] = sumatorio[i - 1] + vehicles.get(i - 1);
        }
    }

    // Ejecuta el algoritmo, imprime los datos de entrada, la tabla de cálculo y una asignación posible.
    public void run() {
        algoritmoFerry();
        printData();
        printSolutionTable();
        printPossibleAssignation();
    }

    // Rellena la tabla dp mediante programación dinámica de abajo hacia arriba.
    // Invariante: dp[i][l] = true <=> es posible cargar los primeros i vehículos
    //             con l metros en babor y (sumatorio[i] - l) metros en estribor,
    //             cumpliendo que ambas longitudes no superen boatLength.
    private void algoritmoFerry() {
        // Ponemos la primera posicion a true siempre y el resto de la fila sera false
        // Estado inicial: con 0 vehículos y 0 metros en babor el estado siempre es alcanzable
        dp[0][0] = true;

        // Procesamos cada vehículo uno a uno (i va de 1 hasta el número total de vehículos)
        for (int i = 1; i <= vehicles.size(); i++) {
            // Recorremos las longitudes posibles de babor para el estado anterior (fila i-1)
            for (int l = boatLength; l >= 0; l--) {
                // Solo procesamos estados que ya son alcanzables en la fila anterior
                if (!dp[i - 1][l]) continue;

                // meter coche en babor
                // Si el vehículo i cabe en babor (l + su longitud <= longitud del carril),
                // marcamos el nuevo estado con la longitud de babor extendida
                if (l + vehicles.get(i - 1) <= boatLength) {
                    dp[i][l + vehicles.get(i - 1)] = true;
                }
                // meter coche en estribor
                // La longitud de estribor se calcula como sumatorio[i] - l_babor.
                // Si el vehículo cabe en estribor, la longitud de babor no cambia
                if (sumatorio[i] - l <= boatLength) {
                    dp[i][l] = true;
                }
            }
        }
    }

    /**
     * Devuelve el numero máximo de vehiculos posibles.
     * l (siendo l <= boatLength) con dp[i][l] = true es el máximo número de coches que pueden entrar.
     */
    // Busca la fila más alta con algún true en la tabla dp.
    // La primera fila (empezando desde la última) que tenga al menos un estado alcanzable
    // corresponde al número máximo de vehículos que caben en el ferry.
    public int getMaximumNumberOfVehicles() {
        for (int i = vehicles.size(); i >= 0; i--) {
            for (int l = 0; l <= boatLength; l++) {
                // Devuelve i en cuanto encuentra un estado alcanzable para i vehículos
                if (dp[i][l]) return i;
            }
        }
        return 0;
    }

    // Imprime por consola la longitud del barco, los vehículos disponibles y el máximo calculado.
    public void printData() {
        System.out.printf("Longitud de los carriles de babor y estribor del ferry: %d\n", boatLength);
        System.out.printf("Los vehiculos tienen las siguientes longitudes:\n");
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.printf("\tVehiculo %d: %d\n", i + 1, vehicles.get(i));
        }
        System.out.printf("\nNumero maximo de vehiculos: %d\n", getMaximumNumberOfVehicles());
    }

    // Reconstruye e imprime una asignación concreta de vehículos a babor y estribor.
    // Parte del estado con el máximo de vehículos y sigue el camino de la tabla dp hacia atrás.
    public void printPossibleAssignation() {
        boolean found = false;
        System.out.printf("\nAsignacion posible:\n");
        // Parte de la fila del máximo de vehículos y busca el primer estado alcanzable
        for (int i = getMaximumNumberOfVehicles(); i > 0; i--) {
            if (found) break;
            for (int p = 0; p <= boatLength; p++) {
                if (found) break;
                if (dp[i][p]) {
                    found = true;
                    // Inicia la reconstrucción del camino desde este estado (i vehículos, p metros en babor)
                    processAssignation(i, p);
                }
            }
        }
    }

    // Reconstruye recursivamente la secuencia de decisiones (babor/estribor) que llevaron
    // al estado (vehicleIndex, portLength). Al llegar a (0, 0) imprime el camino acumulado en path.
    private void processAssignation(int vehicleIndex, int portLength) {
        // Caso base: todos los vehículos han sido asignados, imprime el camino
        if (vehicleIndex == 0 && portLength == 0) {
            printPath();
            return;
        }

        // Comprueba si el vehículo actual fue asignado a estribor:
        // el estado anterior tiene la misma longitud de babor y el exceso va a estribor
        if (dp[vehicleIndex - 1][portLength] && sumatorio[vehicleIndex] - portLength <= boatLength) {
            // Registra el movimiento a estribor y retrocede al estado anterior
            path.addFirst(new Step(vehicleIndex - 1, portLength, vehicleIndex, portLength, vehicleIndex, "estribor"));
            processAssignation(vehicleIndex - 1, portLength);
            return;
        }

        // Si no fue a estribor, el vehículo fue asignado a babor:
        // la longitud anterior de babor era portLength - longitud del vehículo actual
        int previousPortLength = portLength - vehicles.get(vehicleIndex - 1);
        if (previousPortLength >= 0 && dp[vehicleIndex - 1][previousPortLength]) {
            // Registra el movimiento a babor y retrocede al estado anterior
            path.addFirst(new Step(vehicleIndex - 1, previousPortLength, vehicleIndex, portLength, vehicleIndex, "babor"));
            processAssignation(vehicleIndex - 1, previousPortLength);
        }
    }

    // Imprime la tabla dp completa con T (true) o F (false) para cada estado (vehículo, longitud babor).
    public void printSolutionTable() {
        System.out.printf("\nTabla de calculo:\n");

        // Cabecera con los valores de longitud de babor de 0 a boatLength
        System.out.printf("%4s", "V/L");
        for (int i = 0; i <= boatLength; i++) {
            System.out.printf("%4d", i);
        }
        System.out.printf("\n");

        // Imprime fila por fila: cada fila corresponde a haber considerado i vehículos
        for (int i = 0; i <= vehicles.size(); i++) {
            System.out.printf("%4d", i);
            for (int l = 0; l <= boatLength; l++) {
                if (dp[i][l]) {
                    System.out.printf("%4s", "T");
                } else {
                    System.out.printf("%4s", "F");
                }
            }
            System.out.printf("\n");
        }
    }

    // Recorre el camino guardado en path e imprime la asignación de cada vehículo
    // con las longitudes acumuladas de ambos carriles tras cada colocación.
    private void printPath() {
        // Contadores de metros acumulados en cada carril conforme se asignan los vehículos
        int portLength = 0;
        int starboardLength = 0;
        for (var step : path) {
            // Actualiza el contador del carril al que fue asignado el vehículo en este paso
            if (step.movement().equals("babor")) {
                portLength += vehicles.get(step.vehicle() - 1);
            } else {
                starboardLength += vehicles.get(step.vehicle() - 1);
            }
            // Imprime el detalle del movimiento: vehículo, longitud, estado anterior, estado actual y carril
            System.out.printf("Vehiculo %d (longitud %d) -- Desde (%d, %d) -- Hasta (%d, %d) -- Posicion: %s -- Longitud babor: %d -- Longitud estribor: %d\n",
                    step.vehicle(), vehicles.get(step.vehicle() - 1),
                    step.previousI(), step.previousL(),
                    step.currentI(), step.currentL(),
                    step.movement(), portLength, starboardLength);
        }
    }
}

// este fragmento de código va a continuación del } que cierra la clase Ferry
// Registro inmutable que representa un paso en la asignación de un vehículo al ferry.
// Almacena el estado anterior y el actual (índice de vehículo y longitud de babor),
// el número del vehículo y el carril destino ("babor" o "estribor").
record Step(int previousI, int previousL,
        int currentI, int currentL,
        int vehicle, String movement) {
}
