package module;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LaberintoTodas {
    private enum estados{CERO, UNO, DOS};

    private int[][] laberinto;
    private int length;

    public LaberintoTodas(int nFichero){
        cargarTablero(nFichero);
        length = laberinto.length;
    }

    
    private void resolver(){
        int fila = 0;
        int col = 0;

        //CASO BASE
        if(laberinto[fila][col] == laberinto[laberinto.length][laberinto.length])
            return;




    }

    private int[][] cargarTablero(int nFicheroPrueba){
        String nFichero = String.valueOf(nFicheroPrueba);
        String ruta = "files/caso" + nFichero + ".txt";

        List<int[]> filas = new ArrayList<>();

        try (Scanner sc = new Scanner(new FileReader(ruta))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split("\\s+");
                int[] fila = new int[partes.length];
                for (int i = 0; i < partes.length; i++) {
                    fila[i] = Integer.parseInt(partes[i]);
                }
                filas.add(fila);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filas.toArray(new int[0][]);
    }

    private boolean isValid(int fila, int columna){
        return (fila >= 0 && fila < laberinto.length) &&
        (columna >= 0 && columna < laberinto.length);
    }
}
