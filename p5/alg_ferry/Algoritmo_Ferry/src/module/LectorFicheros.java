package module;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorFicheros {

    public static class DatosFerry {
        public final int longitudBarco;
        public final List<Integer> vehiculos;

        public DatosFerry(int longitudBarco, List<Integer> vehiculos) {
            this.longitudBarco = longitudBarco;
            this.vehiculos = vehiculos;
        }
    }

    public static DatosFerry leerVehiculos(String nombreFichero) throws IOException {
        nombreFichero = "files/" + nombreFichero;
        BufferedReader br = new BufferedReader(new FileReader(nombreFichero));

        int boatLength = Integer.parseInt(br.readLine().trim());

        List<Integer> carsLength = new ArrayList<>();
        String[] tokens = br.readLine().trim().split("\\s+");
        for (String token : tokens) {
            carsLength.add(Integer.parseInt(token));
        }

        br.close();
        return new DatosFerry(boatLength, carsLength);
    }
}