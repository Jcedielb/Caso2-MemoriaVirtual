package datos;

public class ActualizadorBitR implements Runnable {
    private final SimuladorDePaginacion simulador;

    public ActualizadorBitR(SimuladorDePaginacion simulador) {
        this.simulador = simulador;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(4); // Espera durante 4 milisegundos
                simulador.actualizarBitRConcurrente(); // Invoca el método de actualización
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Asegura que el estado de interrupción se mantenga
        }
    }
}
