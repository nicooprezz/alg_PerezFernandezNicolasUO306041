import java.io.IOException;
import java.util.Arrays;

import module.Ferry;
import module.LectorFicheros;

public class App {
    public static void main(String[] args) {

        Ferry ferry = crearFerry("test02.txt");
        ferry.run();
    }


    private static Ferry crearFerry(String nombreFichero) {
        try {
            LectorFicheros.DatosFerry datos = LectorFicheros.leerVehiculos(nombreFichero);
            return new Ferry(datos.longitudBarco, datos.vehiculos);
        } catch (IOException e) {
            System.err.println("Error al leer el fichero: " + e.getMessage()
                    + ". \nCREANDO FERRY POR DEFECTO...");
            return new Ferry(10, Arrays.asList(3, 5, 2, 4));
        }
    }
}
