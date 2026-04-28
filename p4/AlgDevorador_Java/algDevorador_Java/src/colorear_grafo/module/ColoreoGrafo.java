package colorear_grafo.module;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

// Implementación del algoritmo voraz (greedy) de coloreo de grafos.
// Asigna colores a los nodos garantizando que dos nodos adyacentes nunca compartan el mismo color.
public class ColoreoGrafo {

    // Aplica el algoritmo voraz de coloreo al grafo recibido como mapa de adyacencia.
    // Para cada nodo, asigna el primer color de la paleta que no utilice ninguno de sus vecinos.
    public static Map<String, String> realizarVoraz(Map<String, List<String>> grafo) {
        // Paleta de colores disponibles; el algoritmo los prueba de izquierda a derecha
        String[] colors =  {"red", "blue", "green", "yellow", "orange", "purple",
            "cyan", "magenta", "lime"};

        //"nodo" -> "color"
        // Mapa resultado que asocia cada nodo con su color definitivo
        Map<String, String> coloredMap = new HashMap<>();

        //Iteramos sobre el set de claves parea ver los vecinos de cada grafo
        List<String> neighbors;

        // Procesamos cada nodo del grafo en el orden de iteración del mapa
        for(String node : grafo.keySet()) {
            //Le pedimos al mapa sacar los vecinos del nodo actual
            neighbors = grafo.get(node);

            //Creamos un hashset y sacamos los colores de los vecinos
            // Colores que ya están ocupados por los vecinos del nodo actual
            HashSet<String> neighborColors  = getNeighborColors(coloredMap, neighbors);

            // Recorremos la paleta y asignamos el primer color que no use ningún vecino
            for (int i = 0; i < colors.length; i++) {
                String candidateColor = colors[i];

                // Si el color candidato no está en uso por ningún vecino, lo asignamos y salimos del bucle
                if(!neighborColors.contains(candidateColor)){
                    coloredMap.put(node, candidateColor);
                    break;
                }
            }
        }
        // Devuelve la asignación completa nodo -> color
        return coloredMap;
    }

    // Recorre los vecinos del nodo actual y recoge los colores que ya tienen asignados.
    // Los vecinos aún sin colorear (valor null en el mapa) son ignorados.
    private static HashSet<String> getNeighborColors(Map<String,String> coloredMap, List<String> neighbors) {
        // Conjunto de colores ocupados por los vecinos; se usa HashSet para búsqueda en O(1)
        HashSet<String> neighborColors = new HashSet<>();

        for(String neighbor : neighbors){
                // Recupera el color del vecino; devuelve null si todavía no ha sido coloreado
                String neighborColor = coloredMap.get(neighbor);

                // Solo registramos el color si el vecino ya fue procesado
                if(neighborColor != null){
                    neighborColors.add(neighborColor);
                }
            }
        return neighborColors;
    }

}
