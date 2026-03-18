package colorear_grafo.module;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Devorador {
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		try (FileReader reader = new FileReader("files/grafo1.json")) {
			JSONObject jsonObject = (JSONObject) parser.parse(reader);

			//@SuppressWarnings("unchecked")
			//Map<String, List<String>> grafo = (Map<String, List<String>>) jsonObject.get("grafo");
			JSONObject grafoJson = (JSONObject) jsonObject.get("grafo");
			Map<String, List<String>> grafo = convertJsonToString(grafoJson);

			Map<String, String> solucion = ColoreoGrafo.realizarVoraz(grafo);
			try (FileWriter file = new FileWriter("files/solucion.json")) {
				file.write(new JSONObject(solucion).toJSONString());
			}

			if (solucion != null) {
				System.out.println("Solución encontrada: " + solucion);
			} else {
				System.out.println("No se encontró solución.");
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, List<String>> convertJsonToString(JSONObject grafoJson) {
		Map<String, List<String>> grafo = new HashMap<>();
		
		for (Object key : grafoJson.keySet()) {
			String nodo = (String) key;
			List<String> vecinosString = new ArrayList<>();
			
			// Sacamos la lista de números (Longs)
			List<?> vecinosOriginales = (List<?>) grafoJson.get(key);
			
			// Convertimos cada número a String y lo metemos en la nueva lista
			for (Object vecino : vecinosOriginales) {
				vecinosString.add(String.valueOf(vecino)); 
			}
			
			// Guardamos el nodo con su lista de vecinos ya convertida a String
			grafo.put(nodo, vecinosString);
		}

		return grafo;
	}
}
