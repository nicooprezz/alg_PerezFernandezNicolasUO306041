package colorear_grafo.module;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ColoreoGrafo {

    public static Map<String, String> realizarVoraz(Map<String, List<String>> grafo) {
        String[] colors =  {"red", "blue", "green", "yellow", "orange", "purple", 
            "cyan", "magenta", "lime"};
        
        //"nodo" -> "color"
        Map<String, String> coloredMap = new HashMap<>();

        //Iteramos sobre el set de claves parea ver los vecinos de cada grafo
        List<String> neighbors;

        for(String node : grafo.keySet()) {
            //Le pedimos al mapa sacar los vecinos del nodo actual
            neighbors = grafo.get(node);

            //Creamos un hashset y sacamos los colores de los vecinos
            HashSet<String> neighborColors  = getNeighborColors(coloredMap, neighbors);
            
            for (int i = 0; i < colors.length; i++) {
                String candidateColor = colors[i];

                if(!neighborColors.contains(candidateColor)){
                    coloredMap.put(node, candidateColor);
                    break;
                }
            }
        }
        return coloredMap;
    }

    private static HashSet<String> getNeighborColors(Map<String,String> coloredMap, List<String> neighbors) {
        HashSet<String> neighborColors = new HashSet<>();

        for(String neighbor : neighbors){
                String neighborColor = coloredMap.get(neighbor);

                if(neighborColor != null){
                    neighborColors.add(neighborColor);
                }
            }
        return neighborColors;
    }
    
}
