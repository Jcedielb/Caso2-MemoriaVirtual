import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Bienvenido a Memoria Virtual:");
        System.out.println("1. Generación de las referencias");
        System.out.println("2. Calcular datos");
        System.out.print("Seleccione una opción: ");
        
        String opcion = scanner.nextLine();
        
        switch (opcion) {
            case "1":
                referencias.MainReferencias.main(args);
                break;
            case "2":
                datos.MainDatos.main(args);
                break;
            default:
                System.out.println("Opción inválida. Por favor, seleccione 1 o 2.");
                break;
        }
        
        scanner.close();
    }
}
