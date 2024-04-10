package datos;

import java.io.IOException;
import java.util.Scanner; // Importa la clase Scanner

public class MainDatos {
    public static void main(String[] args) {
        // Crea un objeto Scanner para leer la entrada del teclado
        Scanner scanner = new Scanner(System.in);

        // Solicita al usuario el número de marcos de página
        System.out.println("Ingrese el número de marcos de página:");
        int numMarcos = scanner.nextInt(); // Lee un entero

        // Consumir el fin de línea después de leer el entero
        scanner.nextLine();

        // Solicita al usuario el nombre del archivo de referencias
        System.out.println("Ingrese el nombre del archivo de referencias:");
        String nombreArchivo = scanner.nextLine(); // Lee una línea de texto

        // Crea una instancia de SimuladorPaginacion con el número de marcos especificado
        SimuladorDePaginacion simulador = new SimuladorDePaginacion(numMarcos);

        // Intenta cargar las referencias desde el archivo y procesarlas
        try {
            simulador.cargarReferencias(nombreArchivo);
            simulador.imprimirEstadisticas(); // Imprime las estadísticas de la simulación
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        // Cierra el scanner
        scanner.close();
    }
}
