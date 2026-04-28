package module;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Utilidad de lectura de ficheros de entrada para el problema del ferry.
// Los ficheros tienen dos líneas: la primera con la longitud del barco
// y la segunda con los tamaños de los vehículos separados por espacios.
public class LectorFicheros {

    // Clase interna inmutable que agrupa los datos leídos del fichero:
    // la longitud de los carriles del barco y la lista de longitudes de vehículos.
    public static class DatosFerry {
        public final int longitudBarco;
        public final List<Integer> vehiculos;

        public DatosFerry(int longitudBarco, List<Integer> vehiculos) {
            this.longitudBarco = longitudBarco;
            this.vehiculos = vehiculos;
        }
    }

    // Lee el fichero indicado y devuelve un objeto DatosFerry con la longitud del barco y los vehículos.
    // El fichero se busca dentro de la carpeta "files/".
    public static DatosFerry leerVehiculos(String nombreFichero) throws IOException {
        // Antepone el directorio base al nombre del fichero
        nombreFichero = "files/" + nombreFichero;
        // Abre el fichero con un BufferedReader para lectura eficiente línea a línea
        BufferedReader br = new BufferedReader(new FileReader(nombreFichero));

        // Primera línea: longitud de los carriles del ferry (babor y estribor tienen la misma)
        int boatLength = Integer.parseInt(br.readLine().trim());

        // Segunda línea: tamaños de los vehículos separados por espacios
        List<Integer> carsLength = new ArrayList<>();
        String[] tokens = br.readLine().trim().split(" ");
        // Convierte cada token de texto a un entero y lo añade a la lista
        for (String token : tokens) {
            carsLength.add(Integer.parseInt(token));
        }

        // Cierra el fichero para liberar el recurso
        br.close();
        // Devuelve los datos encapsulados en un objeto DatosFerry
        return new DatosFerry(boatLength, carsLength);
    }
}
