import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeneradorReferencias {
    private int tamanoPagina;
    private int filas;
    private int columnas;
    private int tamanoFiltro = 3; // El filtro es de 3x3, pero aquí solo necesitamos su tamaño lineal

    public GeneradorReferencias(int tamanoPagina, int filas, int columnas) {
        this.tamanoPagina = tamanoPagina;
        this.filas = filas;
        this.columnas = columnas;
    }

    public void generarArchivoReferencias(String nombreArchivo) throws IOException {
        List<String> referencias = generarReferencias();
        int numeroPaginas = calcularNumeroPaginas();
        int numeroReferencias = referencias.size();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("TP=" + tamanoPagina + "\n");
            writer.write("NF=" + filas + "\n");
            writer.write("NC=" + columnas + "\n");
            writer.write("NF_NC_Filtro=" + tamanoFiltro + "\n");
            writer.write("NR=" + numeroReferencias + "\n");
            writer.write("NP=" + numeroPaginas + "\n");
            for (String referencia : referencias) {
                writer.write(referencia + "\n");
            }
        }
    }

    private List<String> generarReferencias() {
        List<String> referencias = new ArrayList<>();
        int[][] filtro = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}; // Ejemplo de filtro de 3x3

        // Offset inicial para los datos y resultados en la memoria
        int offsetDatos = filtro.length * filtro[0].length * 4; // 3x3 filtro, cada entero = 4 bytes
        int offsetResultados = offsetDatos + (filas * columnas * 4); // Datos después del filtro

        // Generar referencias para el procesamiento del filtro
        for (int i = 1; i < filas - 1; i++) {
            for (int j = 1; j < columnas - 1; j++) {
                for (int fi = -1; fi <= 1; fi++) {
                    for (int fj = -1; fj <= 1; fj++) {
                        // Referencia a la matriz de datos
                        int posicionDatos = ((i + fi) * columnas + (j + fj)) * 4 + offsetDatos;
                        int paginaDatos = posicionDatos / tamanoPagina;
                        int desplazamientoDatos = posicionDatos % tamanoPagina;
                        referencias.add(String.format("M[%d][%d],%d,%d,R", i + fi, j + fj, paginaDatos, desplazamientoDatos));

                        // Referencia al filtro
                        int posicionFiltro = ((fi + 1) * 3 + (fj + 1)) * 4; // +1 para ajustar índice base 0
                        int paginaFiltro = posicionFiltro / tamanoPagina;
                        int desplazamientoFiltro = posicionFiltro % tamanoPagina;
                        referencias.add(String.format("F[%d][%d],%d,%d,R", fi + 1, fj + 1, paginaFiltro, desplazamientoFiltro));
                    }
                }
                // Referencia a la matriz de resultados
                int posicionResultado = (i * columnas + j) * 4 + offsetResultados;
                int paginaResultado = posicionResultado / tamanoPagina;
                int desplazamientoResultado = posicionResultado % tamanoPagina;
                referencias.add(String.format("R[%d][%d],%d,%d,W", i, j, paginaResultado, desplazamientoResultado));
            }
        }
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                // Solo para bordes, excluyendo el proceso central de filtrado
                if (i == 0 || i == filas - 1 || j == 0 || j == columnas - 1) {
                    int resultIndex = ((3 * 3) + (filas * columnas) + (i * columnas + j)) * 4;
                    int paginaResult = resultIndex / tamanoPagina;
                    int offsetResult = resultIndex % tamanoPagina;
                    referencias.add(String.format("R[%d][%d],%d,%d,W", i, j, paginaResult, offsetResult));
                }
            }
        }
        return referencias;
    }


    private int calcularNumeroPaginas() {
        int[][] filtro = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}; // Ejemplo de filtro de 3x3
        int totalBytesFiltro = filtro.length * filtro[0].length * 4; // Tamaño del filtro
        int totalBytesDatos = filas * columnas * 4; // Tamaño de la matriz de datos
        int totalBytesResultados = filas * columnas * 4; // Tamaño de la matriz de resultados
        int totalBytes = totalBytesFiltro + totalBytesDatos + totalBytesResultados;
        return (int) Math.ceil((double) totalBytes / tamanoPagina);
    }
}