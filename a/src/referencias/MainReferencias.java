package referencias;

import java.io.IOException;
import java.util.Scanner;

public class MainReferencias {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Obtiene el tamaño de página y las dimensiones de la matriz
            System.out.println("Ingrese el tamaño de la página:");
            int tamanoPagina = Integer.parseInt(scanner.nextLine());

            System.out.println("Ingrese el número de filas de la matriz de datos:");
            int numeroDeFilas = Integer.parseInt(scanner.nextLine());

            System.out.println("Ingrese el número de columnas de la matriz de datos:");
            int numeroDeColumnas = Integer.parseInt(scanner.nextLine());

            // Instancia el generador de referencias y genera el archivo
            GeneradorReferencias generador = new GeneradorReferencias(tamanoPagina, numeroDeFilas, numeroDeColumnas);
            generador.generarArchivoReferencias("referencias.txt");

            System.out.println("Archivo de referencias generado exitosamente.");

        } catch (NumberFormatException e) {
            System.err.println("Se ha introducido un valor no numérico inválido. Por favor, asegúrese de introducir números enteros.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            scanner.close();
        }
    }
}