package colorear_grafo.time;

import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import colorear_grafo.module.*;

/* Esta clase mide tiempos del metodo realizarVoraz
leyendo los ficheros del disco duro.
En el comando de ejecución hay que pasar como argumento 
el número de repeticiones deseado. */

public class DevoradorTiempos {

    public static void main(String arg[]) {
        long t1, t2;
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduce el número de repeticiones (ej. 1000) y pulsa Enter: ");
        int repeticiones = teclado.nextInt();
        teclado.close();
        
        JSONParser parser = new JSONParser();

        for (int n = 4; n <= 65536; n *= 2) {
            long t = 0;
            String ruta = "files/files_repo/g" + n + ".json"; 
            
            try (FileReader reader = new FileReader(ruta)) {
                JSONObject jsonObject = (JSONObject) parser.parse(reader);
                JSONObject grafoJson = (JSONObject) jsonObject.get("grafo");
                Map<String, List<String>> grafo = Devorador.convertJsonToString(grafoJson);

                for (int i = 1; i <= repeticiones; i++) {
                    
                    t1 = System.currentTimeMillis();
                    
                    ColoreoGrafo.realizarVoraz(grafo); 
                    
                    t2 = System.currentTimeMillis();
                    t = t + (t2 - t1);
                }

                System.out.println("n=" + n + "\tTiempo=" + t + "  \tRepeticiones=" + repeticiones);

            } catch (Exception e) {
                System.out.println("n=" + n + "\t[Fichero no encontrado: " + ruta + "]");
            }
        }
    }
}
