package module;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Resolución del problema de empaquetado en contenedores (bin packing) mediante backtracking.
// Dada una capacidad de contenedor y un conjunto de objetos, encuentra la distribución
// que minimiza el número de contenedores necesarios para almacenarlos todos.
public class AlmacenajeContenedores{

    private int capacidadC;      // Capacidad máxima de cada contenedor
    private Integer[] conjuntoS; // Array de tamaños de objetos a distribuir
    private int mejorK; // Número mínimo de contenedores encontrado hasta el momento

    // Mejor distribución hallada: lista de contenedores, cada uno con los objetos que contiene
    private List<List<Integer>> mejorDistribucion = new ArrayList<List<Integer>>();

    // Constructor: inicializa la capacidad, ordena los objetos de mayor a menor (heurística greedy)
    // y fija la cota superior inicial a n contenedores (peor caso: un contenedor por objeto).
    public AlmacenajeContenedores(int c, Integer[] toS) {
        this.capacidadC = c;
        this.conjuntoS = toS;
        // Ordenar de mayor a menor mejora la poda: los objetos grandes se colocan primero
        Arrays.sort(this.conjuntoS, Collections.reverseOrder());
        // Cota superior inicial: en el peor caso se necesita un contenedor por cada objeto
        this.mejorK = conjuntoS.length;
    }

    // Lee los datos de entrada desde un fichero (capacidad + lista de objetos),
    // crea la instancia y ejecuta el algoritmo de backtracking.
    public static void main(String[] args){

        try (Scanner sc = new Scanner(new FileReader(args[0]))) {
            // Primera línea: capacidad de los contenedores
            int c = sc.nextInt();
            sc.nextLine();
            // Segunda línea: tamaños de los objetos separados por espacios
            String[] parts = sc.nextLine().split(" ");
            Integer[] toS = new Integer[parts.length];

            int i = 0;
            // Convierte los tokens de texto a enteros
            for(String s: parts){
                toS[i] = Integer.parseInt(s);
                i++;
            }

            // Construye la instancia y lanza la resolución
            AlmacenajeContenedores sol = new AlmacenajeContenedores(c, toS);
            sol.resolver();
        } catch (NumberFormatException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Inicializa la estructura de contenedores vacía y lanza el backtracking desde el objeto 0.
    public void resolver() {
        // Ordernar conjuntos descendentemente con el compareTo de Integer

        // backtracking
        // Lista de contenedores actualmente en uso durante la exploración
        List<List<Integer>> contenedores = new ArrayList<List<Integer>>();
        backTracking(0, contenedores);
        // mostrar solucion
        mostrarSolucion();
    }

    // Muestra por consola la mejor distribución encontrada y el número mínimo de contenedores.
    private void mostrarSolucion() {
        System.out.println("Lista de contenedores y objetos contenidos:");
        for(int i = 0; i < mejorDistribucion.size(); i++){
            System.out.print("\tContenedor " + (i+1) + ": ");
            for(Integer obj: mejorDistribucion.get(i)){
                System.out.print(obj + " ");
            }
            System.out.println();
        }
        System.out.println("El número de contenedores necesario es " + mejorK + ".");
    }

    // Realiza una copia profunda de la lista de contenedores para guardar la mejor solución
    // sin que futuras modificaciones del backtracking alteren la copia guardada.
    private List<List<Integer>> copiar(List<List<Integer>> contenedores){
        ArrayList<List<Integer>> copia = new ArrayList<List<Integer>>();
        for(List<Integer> bin: contenedores){
            // Crea una nueva lista con los mismos objetos para que sea independiente del estado actual
            copia.add(new ArrayList<Integer>(bin));
        }
        return copia;
    }

    // Algoritmo de backtracking que asigna el objeto en posición indexObject a algún contenedor.
    // Explora primero los contenedores existentes (ramificación) y luego la apertura de uno nuevo.
    private void backTracking(int indexObject, List<List<Integer>> contenedores){
        // Caso base (todos los objetos están colocados)
        if(indexObject == conjuntoS.length){ // Aquí compruebas que has llegado al final de la iteración, una solución
            // Si la solución actual usa menos contenedores que la mejor conocida, la reemplaza
            if(contenedores.size() < mejorK){
                mejorK = contenedores.size();
                mejorDistribucion = copiar(contenedores);
            }
            return; // Acaba con la ejecución del backtracking en este punto, si agoto
        }
        // Podamos: si size de contenedores > mejorK
        // Poda: si ya hay tantos o más contenedores abiertos que la mejor solución conocida,
        // esta rama no puede mejorarla y se descarta
        if(contenedores.size() >= mejorK)
            return;

        // Probar a meter en contenedores existentes
        // Ramificación: intenta colocar el objeto en cada contenedor que tenga espacio suficiente
        for(int i = 0; i < contenedores.size(); i++){
            if(sum(contenedores.get(i)) + conjuntoS[indexObject] <= capacidadC){
                // Avanzar (probar temporalmente a meter un objeto en un contenedor que existe)
                // Coloca el objeto en el contenedor i
                contenedores.get(i).add(conjuntoS[indexObject]);
                // Llama recursivamente para colocar el siguiente objeto
                backTracking(indexObject+1, contenedores);
                // Si encuentro un estado mejor, lo almaceno
                // Retroceder: elimina el objeto recién añadido para explorar otras opciones
                contenedores.get(i).remove(contenedores.get(i).size()-1);

            }
        }

        // Intentar meterlo en uno nuevo
        // Rama de apertura de un nuevo contenedor: coloca el objeto solo en un contenedor vacío
        List<Integer> nuevoContenedor = new ArrayList<Integer>();
        nuevoContenedor.add(conjuntoS[indexObject]);
        contenedores.add(nuevoContenedor);
        // Avanzo
        backTracking(indexObject+1, contenedores);
        // Elimino el último
        // Retroceder: elimina el contenedor recién creado
        contenedores.remove(contenedores.size()-1);

    }

    // Devuelve el número mínimo de contenedores encontrado por el algoritmo.
    public int getMejorK() { return mejorK; }

    // Calcula la suma de los tamaños de los objetos en un contenedor dado.
    // Se usa para comprobar si el siguiente objeto cabe sin superar la capacidad.
    private Integer sum(List<Integer> list) {
        Integer sum = 0;
        for(Integer i: list){
            sum += i;
        }
        return sum;
    }
}
