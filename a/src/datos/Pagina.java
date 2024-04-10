package datos;
public class Pagina {
    private int numero; // Número identificador de la página
    private boolean bitR; // Bit de referencia para el algoritmo de envejecimiento
    private long contador; // Contador de envejecimiento para la página

    // Constructor de la clase
    public Pagina(int numero) {
        this.numero = numero;
        this.bitR = false; // Inicialmente, el bit de referencia es false
        this.contador = 0; // Inicialmente, el contador de envejecimiento es 0
    }

    // Getter para el número de página
    public int getNumero() {
        return numero;
    }

    // Getter para el bit de referencia
    public boolean getBitR() {
        return bitR;
    }

    // Setter para el bit de referencia
    public void setBitR(boolean bitR) {
        this.bitR = bitR;
    }

    // Getter para el contador de envejecimiento
    public long getContador() {
        return contador;
    }

    // Método para actualizar el contador de envejecimiento
    // Este método deberá ser llamado periódicamente para simular el envejecimiento
    public void actualizarContador() {
        // Desplaza el contador una posición a la derecha para simular el paso del tiempo
        contador = contador >> 1;

        // Si el bit R está activo, se añade el valor más alto posible al contador
        // Esto indica que la página ha sido referenciada recientemente
        if (bitR) {
            contador = contador | (1L << (Long.SIZE - 1)); // Asume que Long.SIZE es el tamaño del contador
            bitR = false; // Restablece el bit R después de la actualización
        }
    }

    // Método toString para mostrar información de la página (opcional)
    @Override
    public String toString() {
        return "Pagina{" +
               "numero=" + numero +
               ", bitR=" + bitR +
               ", contador=" + contador +
               '}';
    }
}
