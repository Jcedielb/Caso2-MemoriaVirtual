package datos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class SimuladorDePaginacion {
    private Map<Integer, Pagina> paginasEnRAM; // Mapeo de números de página a objetos Pagina
    private PriorityQueue<Pagina> colaEnvejecimiento; // Para determinar la página más antigua basada en el contador
    private int numMarcos; // Número de marcos de página disponibles en RAM
    private int hits; // Contador de hits
    private int misses; // Contador de misses

    public SimuladorDePaginacion(int numMarcos) {
        this.numMarcos = numMarcos;
        this.hits = 0;
        this.misses = 0;
        this.paginasEnRAM = new HashMap<>();
        this.colaEnvejecimiento = new PriorityQueue<>((p1, p2) -> Long.compare(p1.getContador(), p2.getContador()));
    }

    // Lee las referencias de páginas desde un archivo y simula el acceso a estas
    public void cargarReferencias(String nombreArchivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Ignorar líneas que no contienen referencias de página
                if (linea.startsWith("M[") || linea.startsWith("F[") || linea.startsWith("R[")) {
                    String[] partes = linea.split(","); // Divide la línea en partes
                    if (partes.length >= 3) {
                        int numPagina = Integer.parseInt(partes[1]); // Extrae el número de página
                        accederPagina(numPagina); // Simula el acceso a la página
                    }
                }
            }
        }
    }

    // Simula el acceso a una página
    private void accederPagina(int numPagina) {
        if (paginasEnRAM.containsKey(numPagina)) {
            hits++;
            Pagina pagina = paginasEnRAM.get(numPagina);
            pagina.setBitR(true); // Marca el bit R ya que la página fue accedida
        } else {
            misses++;
            if (paginasEnRAM.size() == numMarcos) {
                reemplazarPagina(); // Necesitamos reemplazar una página si no hay marcos libres
            }
            Pagina nuevaPagina = new Pagina(numPagina);
            nuevaPagina.setBitR(true);
            paginasEnRAM.put(numPagina, nuevaPagina);
            colaEnvejecimiento.add(nuevaPagina);
        }
        actualizarContadores(); // Actualiza los contadores de todas las páginas en RAM
    }

    // Reemplaza la página más antigua en RAM con una nueva página
    private void reemplazarPagina() {
        Pagina paginaParaRemover = colaEnvejecimiento.poll();
        if (paginaParaRemover != null) {
            paginasEnRAM.remove(paginaParaRemover.getNumero());
        }
    }

    // Actualiza los contadores de envejecimiento de todas las páginas en RAM
    private void actualizarContadores() {
        for (Pagina pagina : paginasEnRAM.values()) {
            pagina.actualizarContador();
        }
    }

    // Método para imprimir las estadísticas del simulador
    public void imprimirEstadisticas() {
        System.out.println("Total de Hits: " + hits);
        System.out.println("Total de Misses: " + misses);
        System.out.println("Porcentaje de Hits: " + (double) hits / (hits + misses) * 100 + "%");
        System.out.println("Porcentaje de Misses: " + (double) misses / (hits + misses) * 100 + "%");
    }

    // Main para probar el simulador (opcional)
    public static void main(String[] args) throws IOException {
        SimuladorDePaginacion simulador = new SimuladorDePaginacion(16); // Supongamos 4 marcos de página para el ejemplo
        simulador.cargarReferencias("referencias.txt"); // Suponiendo que el archivo se llama referencias.txt
        simulador.imprimirEstadisticas();
    }

    public void actualizarBitRConcurrente() {
        for (Pagina pagina : paginasEnRAM.values()) {
            pagina.actualizarContador();    }
}
}
