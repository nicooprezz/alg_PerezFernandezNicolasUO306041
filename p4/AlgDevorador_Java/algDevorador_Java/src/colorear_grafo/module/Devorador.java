package colorear_grafo.module;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Clase principal que coordina la lectura del grafo desde un fichero JSON,
// la ejecución del algoritmo de coloreo y la escritura de la solución en otro JSON.
public class Devorador {
	public static void main(String[] args) {
		// Instancia el parser de JSON de la librería json-simple
		JSONParser parser = new JSONParser();
		// Abre el fichero de entrada con el grafo; el try-with-resources garantiza que se cierre
		try (FileReader reader = new FileReader("files/grafo1.json")) {
			// Parsea el contenido del fichero como un objeto JSON raíz
			JSONObject jsonObject = (JSONObject) parser.parse(reader);

			//@SuppressWarnings("unchecked")
			//Map<String, List<String>> grafo = (Map<String, List<String>>) jsonObject.get("grafo");
			// Extrae el objeto JSON que contiene el grafo como mapa de adyacencia
			JSONObject grafoJson = (JSONObject) jsonObject.get("grafo");
			// Convierte el JSONObject a un Map<String, List<String>> compatible con el algoritmo
			Map<String, List<String>> grafo = convertJsonToString(grafoJson);

			// Ejecuta el algoritmo voraz y obtiene la asignación nodo -> color
			Map<String, String> solucion = ColoreoGrafo.realizarVoraz(grafo);
			// Abre (o crea) el fichero de salida y escribe la solución serializada como JSON
			try (FileWriter file = new FileWriter("files/solucion.json")) {
				file.write(new JSONObject(solucion).toJSONString());
			}

			// Muestra la solución por consola si fue encontrada
			if (solucion != null) {
				System.out.println("Solución encontrada: " + solucion);
			} else {
				System.out.println("No se encontró solución.");
			}
		} catch (IOException | ParseException e) {
			// Captura errores de lectura/escritura del fichero y de parseo del JSON
			e.printStackTrace();
		}
	}

	// Convierte un JSONObject de json-simple (que almacena vecinos como List<Long>)
	// a un Map<String, List<String>> con los identificadores de nodo como cadenas de texto.
	public static Map<String, List<String>> convertJsonToString(JSONObject grafoJson) {
		// Mapa resultante con los nodos como claves y sus listas de vecinos como valores
		Map<String, List<String>> grafo = new HashMap<>();

		// Recorremos cada nodo del JSONObject
		for (Object key : grafoJson.keySet()) {
			String nodo = (String) key;
			List<String> vecinosString = new ArrayList<>();

			// Sacamos la lista de números (Longs)
			// json-simple deserializa los números como Long, por eso se usa List<?>
			List<?> vecinosOriginales = (List<?>) grafoJson.get(key);

			// Convertimos cada número a String y lo metemos en la nueva lista
			for (Object vecino : vecinosOriginales) {
				vecinosString.add(String.valueOf(vecino));
			}

			// Guardamos el nodo con su lista de vecinos ya convertida a String
			grafo.put(nodo, vecinosString);
		}

		// Devuelve el grafo con todos sus nodos y vecinos en formato String
		return grafo;
	}
}
