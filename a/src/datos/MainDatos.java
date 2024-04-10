package datos;
import java.io.IOException;
import java.util.Scanner;

public class MainDatos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el número de marcos de página:");
        int numMarcos = scanner.nextInt();
        scanner.nextLine(); // Consumir el fin de línea

        System.out.println("Ingrese el nombre del archivo de referencias:");
        String nombreArchivo = scanner.nextLine();

        // Crea el simulador de paginación con el número de marcos especificado
        SimuladorDePaginacion simulador = new SimuladorDePaginacion(numMarcos);

        // Inicia el thread para actualizar el bit R cada 4 milisegundos
        Thread threadActualizarBitR = new Thread(new ActualizadorBitR(simulador));
        threadActualizarBitR.start();

        try {
            // Cargar referencias desde el archivo y procesarlas
            simulador.cargarReferencias(nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } finally {
            // Una vez finalizado el procesamiento de referencias, interrumpir y esperar la finalización del thread de actualización del bit R
            threadActualizarBitR.interrupt();
            try {
                threadActualizarBitR.join(); // Espera a que el thread de actualización del bit R termine
            } catch (InterruptedException e) {
                System.err.println("Thread interrumpido: " + e.getMessage());
                Thread.currentThread().interrupt(); // Restablece el estado de interrupción
            }

            // Imprime las estadísticas de la simulación
            simulador.imprimirEstadisticas();
        }

        scanner.close();
    }
}
