package module;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

// Suite de pruebas JUnit para verificar el algoritmo del ferry.
// Cada test carga un fichero de casos de prueba, ejecuta el algoritmo
// y comprueba que el número máximo de vehículos coincide con el resultado esperado.
public class FerryTests {
	// Longitud de los carriles del barco leída del fichero de prueba
	private int length;
	// Lista de longitudes de los vehículos a distribuir
	private List<Integer> vehicles;

	@Test
	public void test00() {
		System.out.printf("\n\nCASE 00\n");
		// Carga los datos de entrada del fichero de prueba 00
		loadData("files/test00.txt");
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		// Verifica que el número máximo de vehículos es el esperado para este caso
		assertEquals(5, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}

	@Test
	public void test01() {
		System.out.printf("\n\nCASE 01\n");
		loadData("files/test01.txt");
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(2, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}

	@Test
	public void test02() {
		System.out.printf("\n\nCASE 02\n");
		loadData("files/test02.txt");
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(4, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}

	@Test
	public void test03() {
		System.out.printf("\n\nCASE 03\n");
		loadData("files/test03.txt");
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(4, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}

	@Test
	public void test04() {
		System.out.printf("\n\nCASE 04\n");
		loadData("files/test04.txt");
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(4, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}

	@Test
	public void test05() {
		System.out.printf("\n\nCASE 05\n");
		loadData("files/test05.txt");
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(3, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}

	@Test
	public void test06() {
		System.out.printf("\n\nCASE 06\n");
		loadData("files/test06.txt");
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(10, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}

	@Test
	public void test07() {
		System.out.printf("\n\nCASE 07\n");
		loadData("files/test07.txt");
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(11, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}

	// Carga la longitud del barco y los tamaños de los vehículos desde el fichero indicado.
	// La primera línea contiene la longitud del carril y la segunda los vehículos separados por espacios.
	private void loadData(String file) {
		this.vehicles = new ArrayList<Integer>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			// Primera línea: longitud de los carriles del ferry
			this.length = Integer.valueOf(reader.readLine());
			// Segunda línea: tamaños de los vehículos separados por espacio
			for (String s : reader.readLine().split(" ")) {
				this.vehicles.add(Integer.valueOf(s));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Cierra el lector en el bloque finally para garantizar la liberación del recurso
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
