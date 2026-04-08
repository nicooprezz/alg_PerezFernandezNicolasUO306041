import java.util.Scanner;

/* Esta clase mide tiempos del metodo realizarVoraz
leyendo los ficheros del disco duro.
En el comando de ejecución hay que pasar como argumento 
el número de repeticiones deseado. */

public class NReinas_UOTiempos {

    public static void main(String arg[]) {
        long t1, t2;
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduce el número máximo repeticiones (sobre 15) y pulsa Enter: ");
        int repeticiones = teclado.nextInt();
        teclado.close();
        NReinas_UO algoritmoReinas = new NReinas_UO();
        long t = 0;

        for (int i = 1; i <= repeticiones; i++) {
                    
            t1 = System.currentTimeMillis();
            
            algoritmoReinas.resolverNReinas(i); 
                
            t2 = System.currentTimeMillis();
            t = t + (t2 - t1);
        }

        System.out.println("\tTiempo=" + t + "  \tRepeticiones=" + repeticiones);
            
        
    }
}