import java.io.IOException;
import java.util.Arrays;

import module.Ferry;
import module.LectorFicheros;

// Punto de entrada de la aplicación del ferry.
// Carga los datos de un fichero de prueba, construye el problema y ejecuta el algoritmo.
public class App {
    public static void main(String[] args) {

        // Crea el objeto Ferry a partir del fichero de prueba y ejecuta la distribución de vehículos
        Ferry ferry = crearFerry("test02.txt");
        ferry.run();
    }


    // Lee el fichero indicado y construye un objeto Ferry con la longitud del barco y los vehículos.
    // Si la lectura falla, crea un ferry por defecto con valores de ejemplo.
    private static Ferry crearFerry(String nombreFichero) {
        try {
            // Delega la lectura del fichero en LectorFicheros y extrae los datos del ferry
            LectorFicheros.DatosFerry datos = LectorFicheros.leerVehiculos(nombreFichero);
            // Instancia el ferry con la longitud del carril y la lista de vehículos leídos
            return new Ferry(datos.longitudBarco, datos.vehiculos);
        } catch (IOException e) {
            // Si no se puede leer el fichero, avisa por consola y usa datos de ejemplo
            System.err.println("Error al leer el fichero: " + e.getMessage()
                    + ". \nCREANDO FERRY POR DEFECTO...");
            // Ferry de respaldo: carril de 10 m con vehículos de 3, 5, 2 y 4 metros
            return new Ferry(10, Arrays.asList(3, 5, 2, 4));
        }
    }
}
