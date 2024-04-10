package datos;

public class MarcoDePagina {
    private int idMarco;
    private Pagina pagina; // La página actualmente asignada a este marco
    private boolean ocupado; // Indica si el marco tiene una página asignada
    private boolean bitR;
    private int contadorEdad;
    // Constructor
    public MarcoDePagina(int idMarco) {
        this.idMarco = idMarco;
        this.pagina = null; // Inicialmente, el marco está vacío
        this.ocupado = false;
        this.bitR = false; // Todos los marcos comienzan sin haber sido accedidos recientemente.
        this.contadorEdad = 0; // La edad inicial de cada marco es 0.
    }

    // Getters y setters
    public int getIdMarco() {
        return idMarco;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
        this.ocupado = (pagina != null); // Si se asigna una página, el marco está ocupado
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void liberar() {
        this.pagina = null;
        this.ocupado = false;
    }

    @Override
    public String toString() {
        return "MarcoDePagina{" +
                "idMarco=" + idMarco +
                ", ocupado=" + ocupado +
                ", pagina=" + (pagina == null ? "Ninguna" : pagina.toString()) +
                '}';
    }

    public boolean getBitR() {
        return bitR;
    }

    public void setBitR(boolean bitR) {
        this.bitR = bitR;
    }

    public int getContadorEdad() {
        return contadorEdad;
    }

    public void incrementarContadorEdad() {
        this.contadorEdad++;
    }

    public void resetContadorEdad() {
        this.contadorEdad = 0;
    }
}

