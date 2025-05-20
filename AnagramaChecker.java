import java.util.Arrays;
import java.util.Scanner;

public class AnagramaChecker {
    // Método no estático dentro de la clase
    public boolean sonAnagramas(String palabra1, String palabra2) {
        // Convertir a minúsculas y eliminar espacios
        palabra1 = palabra1.toLowerCase().replaceAll("\\s+", "");
        palabra2 = palabra2.toLowerCase().replaceAll("\\s+", "");

        // Si la longitud es diferente, no pueden ser anagramas
        if (palabra1.length() != palabra2.length()) {
            return false;
        }

        // Convertir a arreglos de caracteres y ordenar
        char[] letras1 = palabra1.toCharArray();
        char[] letras2 = palabra2.toCharArray();
        Arrays.sort(letras1);
        Arrays.sort(letras2);

        // Comparar los arreglos ordenados
        return Arrays.equals(letras1, letras2);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crear un objeto de la clase AnagramaChecker
        AnagramaChecker checker = new AnagramaChecker();

        // Solicitar palabras al usuario
        System.out.print("Ingresa la primera palabra: ");
        String palabra1 = scanner.nextLine();

        System.out.print("Ingresa la segunda palabra: ");
        String palabra2 = scanner.nextLine();

        // Llamar al método de instancia
        boolean resultado = checker.sonAnagramas(palabra1, palabra2);
        System.out.println("¿Son anagramas? " + resultado);

        scanner.close();
    }
}
