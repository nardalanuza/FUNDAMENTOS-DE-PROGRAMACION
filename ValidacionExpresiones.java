import java.util.Scanner;
import java.util.Stack;

public class ValidacionExpresiones {

    // Método que verifica si una expresión está balanceada
    public static boolean estaBalanceado(String expresion) {
        // Creamos una pila para almacenar los símbolos de apertura
        Stack<Character> pila = new Stack<>();

        // Recorremos cada carácter de la expresión
        for (int i = 0; i < expresion.length(); i++) {
            char caracter = expresion.charAt(i);

            // Si el carácter es de apertura: (, { o [ lo agregamos a la pila
            if (caracter == '(' || caracter == '{' || caracter == '[') {
                pila.push(caracter);
            } 
            // Si el carácter es de cierre: ), } o ]
            else if (caracter == ')' || caracter == '}' || caracter == ']') {
                // Verificamos si la pila está vacía; si lo está, no hay símbolo de apertura
                if (pila.isEmpty()) {
                    return false;
                }
                // Extraemos el último símbolo de apertura almacenado
                char simboloApertura = pila.pop();
                // Verificamos si el símbolo de cierre corresponde al de apertura correcto
                if ((caracter == ')' && simboloApertura != '(') ||
                    (caracter == '}' && simboloApertura != '{') ||
                    (caracter == ']' && simboloApertura != '[')) {
                    return false;
                }
            }
            // Otros caracteres se ignoran en este ejemplo
        }
        // Si la pila está vacía, todos los símbolos han sido emparejados correctamente
        return pila.isEmpty();
    }

    public static void main(String[] args) {
        // Creamos el objeto Scanner para leer desde el teclado
        Scanner entrada = new Scanner(System.in);
        System.out.println("Introduce una expresión (o escribe 'salir' para finalizar):");
        
        // Ciclo que se ejecuta hasta que el usuario escriba "salir"
        while (true) {
            String linea = entrada.nextLine();

            // Si el usuario ingresa "salir", terminamos el programa
            if (linea.equalsIgnoreCase("salir")) {
                break;
            }
            
            // Llamamos al método que verifica el balanceo y mostramos el resultado
            boolean balanceado = estaBalanceado(linea);
            System.out.println("La expresión: " + linea + " está balanceada? " + balanceado);
            System.out.println("Introduce otra expresión (o escribe 'salir' para finalizar):");
        }
        
        System.out.println("Programa finalizado.");
        entrada.close(); // Cerramos el Scanner para liberar recursos
    }
}
