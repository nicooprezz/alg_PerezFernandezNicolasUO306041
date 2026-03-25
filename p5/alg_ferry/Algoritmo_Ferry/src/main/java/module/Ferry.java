package module;

import java.util.LinkedList;
import java.util.List;

public class Ferry {

    private int boatLength; // longitud de los carriles del barco
    private List<Integer> vehicles; // lista de vehiculos
    private boolean[][] dp; // matriz con las posibles soluciones
    private int[] sumatorio; // suma acumulada de las longitudes de los vehiculos
    private List<Step> path; // variable para guardar el camino seleccionado

    public Ferry(int boatLength, List<Integer> vehicles) {
        this.boatLength = boatLength;
        this.vehicles = vehicles;
        this.dp = new boolean[vehicles.size() + 1][boatLength + 1];
        this.sumatorio = new int[vehicles.size() + 1];
        this.sumatorio[0] = 0;
        calcularSumatorio();
        this.path = new LinkedList<Step>();
    }

    private void calcularSumatorio() {
        for (int i = 1; i <= vehicles.size(); i++) {
            this.sumatorio[i] = sumatorio[i - 1] + vehicles.get(i - 1);
        }
    }

    public void run() {
        algoritmoFerry();
        printData();
        printSolutionTable();
        printPossibleAssignation();
    }

    private void algoritmoFerry() {
        // Ponemos la primera posicion a true siempre y el resto de la fila sera false
        dp[0][0] = true;

        for (int i = 1; i <= vehicles.size(); i++) {
            for (int l = boatLength; l >= 0; l--) {
                if (!dp[i - 1][l]) continue;

                // meter coche en babor
                if (l + vehicles.get(i - 1) <= boatLength) {
                    dp[i][l + vehicles.get(i - 1)] = true;
                }
                // meter coche en estribor
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
    public int getMaximumNumberOfVehicles() {
        for (int i = vehicles.size(); i >= 0; i--) {
            for (int l = 0; l <= boatLength; l++) {
                if (dp[i][l]) return i;
            }
        }
        return 0;
    }

    public void printData() {
        System.out.printf("Longitud de los carriles de babor y estribor del ferry: %d\n", boatLength);
        System.out.printf("Los vehiculos tienen las siguientes longitudes:\n");
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.printf("\tVehiculo %d: %d\n", i + 1, vehicles.get(i));
        }
        System.out.printf("\nNumero maximo de vehiculos: %d\n", getMaximumNumberOfVehicles());
    }

    public void printPossibleAssignation() {
        boolean found = false;
        System.out.printf("\nAsignacion posible:\n");
        for (int i = getMaximumNumberOfVehicles(); i > 0; i--) {
            if (found) break;
            for (int p = 0; p <= boatLength; p++) {
                if (found) break;
                if (dp[i][p]) {
                    found = true;
                    processAssignation(i, p);
                }
            }
        }
    }

    private void processAssignation(int vehicleIndex, int portLength) {
        if (vehicleIndex == 0 && portLength == 0) {
            printPath();
            return;
        }

        if (dp[vehicleIndex - 1][portLength] && sumatorio[vehicleIndex] - portLength <= boatLength) {
            path.addFirst(new Step(vehicleIndex - 1, portLength, vehicleIndex, portLength, vehicleIndex, "estribor"));
            processAssignation(vehicleIndex - 1, portLength);
            return;
        }

        int previousPortLength = portLength - vehicles.get(vehicleIndex - 1);
        if (previousPortLength >= 0 && dp[vehicleIndex - 1][previousPortLength]) {
            path.addFirst(new Step(vehicleIndex - 1, previousPortLength, vehicleIndex, portLength, vehicleIndex, "babor"));
            processAssignation(vehicleIndex - 1, previousPortLength);
        }
    }

    public void printSolutionTable() {
        System.out.printf("\nTabla de calculo:\n");

        System.out.printf("%4s", "V/L");
        for (int i = 0; i <= boatLength; i++) {
            System.out.printf("%4d", i);
        }
        System.out.printf("\n");

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

    private void printPath() {
        int portLength = 0;
        int starboardLength = 0;
        for (var step : path) {
            if (step.movement().equals("babor")) {
                portLength += vehicles.get(step.vehicle() - 1);
            } else {
                starboardLength += vehicles.get(step.vehicle() - 1);
            }
            System.out.printf("Vehiculo %d (longitud %d) -- Desde (%d, %d) -- Hasta (%d, %d) -- Posicion: %s -- Longitud babor: %d -- Longitud estribor: %d\n",
                    step.vehicle(), vehicles.get(step.vehicle() - 1),
                    step.previousI(), step.previousL(),
                    step.currentI(), step.currentL(),
                    step.movement(), portLength, starboardLength);
        }
    }
}

// este fragmento de código va a continuación del } que cierra la clase Ferry
record Step(int previousI, int previousL,
        int currentI, int currentL,
        int vehicle, String movement) {
}
