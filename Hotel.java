import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Hotel {

  private Scanner scanner = new Scanner(System.in);
  private String emailGuardado;
  private String passwordGuardado;

  //  VALIDACIONES

  private boolean validarCorreo(String email) {
    String regex = "^[\\w.-]+@[\\w.-]+\\.com$";
    return Pattern.compile(regex).matcher(email).matches();
  }

  private boolean validarPassword(String password) {
    String regex =
      "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    return Pattern.compile(regex).matcher(password).matches();
  }

  private int validarEntradaNumerica(int min, int max, String mensaje) {
    int valor;
    do {
      System.out.print(mensaje);
      while (!scanner.hasNextInt()) {
        System.out.println("❌ Error: Debe ingresar un número válido.");
        scanner.next();
        System.out.print(mensaje);
      }
      valor = scanner.nextInt();
      scanner.nextLine(); // Consumir el salto de línea
    } while (valor < min || valor > max);
    return valor;
  }

  public void registrarCuenta() {
    System.out.println("\n---- REGISTRO DE CUENTA ----");
    String email, password;

    do {
      System.out.print("Ingrese su correo electrónico: ");
      email = scanner.nextLine();
      if (!validarCorreo(email)) {
        System.out.println(
          "❌ Correo inválido. Debe contener '@' y terminar en '.com'."
        );
      }
    } while (!validarCorreo(email));

    do {
      System.out.print(
        "Ingrese su contraseña (mínimo 8 caracteres, debe incluir letras, números y caracteres especiales): "
      );
      password = scanner.nextLine();
      if (!validarPassword(password)) {
        System.out.println(
          "❌ Contraseña inválida. Debe incluir letras, números y caracteres especiales."
        );
      }
    } while (!validarPassword(password));

    emailGuardado = email;
    passwordGuardado = password;
    System.out.println("✅ Registro exitoso. Ahora puede iniciar sesión.");
  }

  public boolean iniciarSesion() {
    System.out.println("\n---- INICIO DE SESIÓN ----");
    int intentos = 3;
    while (intentos > 0) {
      System.out.print("Ingrese su correo: ");
      String emailIngresado = scanner.nextLine();
      System.out.print("Ingrese su contraseña: ");
      String passwordIngresado = scanner.nextLine();

      if (
        emailIngresado.equals(emailGuardado) &&
        passwordIngresado.equals(passwordGuardado)
      ) {
        System.out.println("✅ Inicio de sesión exitoso. Bienvenido!");
        return true;
      } else {
        intentos--;
        System.out.println(
          "❌ Correo o contraseña incorrectos. Intentos restantes: " + intentos
        );
      }
    }
    System.out.println("⛔ Se agotaron los intentos de inicio de sesión.");
    return false;
  }

  //  REGISTRO DE RESERVA

  public void verificarEdad() {
    System.out.println("Para la reserva, ingrese su fecha de nacimiento.");

    int dia = validarEntradaNumerica(1, 31, "Día (DD): ");
    int mes = validarEntradaNumerica(1, 12, "Mes (MM): ");
    int anio = validarEntradaNumerica(
      1940,
      LocalDate.now().getYear(),
      "Año (YYYY): "
    );

    LocalDate fechaNacimiento = LocalDate.of(anio, mes, dia);
    LocalDate fechaActual = LocalDate.now();
    int edad = Period.between(fechaNacimiento, fechaActual).getYears();

    if (edad >= 18) {
      System.out.println("Proceda a llenar sus datos.");

      System.out.print("Ingrese su nombre: ");
      String nombreCliente = scanner.nextLine();
      System.out.print("Ingrese su apellido: ");
      String apellidoCliente = scanner.nextLine();
      System.out.print("Ingrese su DNI: ");
      String dni = scanner.nextLine();

      int cantPersonas = validarEntradaNumerica(
        1,
        10,
        "Ingrese la cantidad de personas a reservar (máx. 10): "
      );
      if (cantPersonas > 10) {
        System.out.println(
          "🔹 Para reservas corporativas, comuníquese al número 777."
        );
        return;
      }

      int tipoHabitacion = seleccionarTipoHabitacion(cantPersonas);
      double totalPagar = calcularPago(cantPersonas, tipoHabitacion);
      System.out.println("\nTotal a pagar: " + totalPagar);

      double montoPagado = solicitarPagoCliente(totalPagar);
      generarBoletaVenta(
        nombreCliente,
        apellidoCliente,
        dni,
        cantPersonas,
        tipoHabitacion,
        totalPagar,
        montoPagado
      );

      mostrarPisoHotel(tipoHabitacion);
    } else {
      System.out.println("Debe ingresar con un mayor de edad.");
    }
  }

  public int seleccionarTipoHabitacion(int cantPersonas) {
    System.out.println("Seleccione el tipo de habitación:");
    System.out.println("1. Simple");
    System.out.println("2. Doble");
    System.out.println("3. Matrimonial");
    return validarEntradaNumerica(1, 3, "Ingrese una opción válida (1-3): ");
  }

  public double calcularPago(int cantPersonas, int tipoHabitacion) {
    double precioBase = (tipoHabitacion == 1)
      ? 50
      : (tipoHabitacion == 2) ? 80 : 100;
    return cantPersonas * precioBase;
  }

  //  PROCESO DE PAGO Y BOLETA
  public double solicitarPagoCliente(double totalPagar) {
    double montoPagado;
    do {
      System.out.print("Ingrese el monto a pagar: ");
      while (!scanner.hasNextDouble()) {
        System.out.println("❌ Error: Debe ingresar un valor numérico.");
        scanner.next();
        System.out.print("Ingrese el monto a pagar: ");
      }
      montoPagado = scanner.nextDouble();
      scanner.nextLine(); // Consumir el salto de línea
      if (montoPagado < totalPagar) {
        System.out.println(
          "❌ El monto ingresado es insuficiente. Debe pagar al menos: " +
          totalPagar
        );
      }
    } while (montoPagado < totalPagar);
    return montoPagado;
  }

  public void generarBoletaVenta(
    String nombreCliente,
    String apellidoCliente,
    String dni,
    int cantPersonas,
    int tipoHabitacion,
    double totalPagar,
    double montoPagado
  ) {
    double vuelto = montoPagado - totalPagar;

    int capacidadHabitacion = (tipoHabitacion == 1) ? 1 : 2;
    int cantidadHabitaciones = (int) Math.ceil(
      (double) cantPersonas / capacidadHabitacion
    );

    double subtotal = totalPagar / 1.18;
    double igv = totalPagar - subtotal;

    System.out.println("\n===== BOLETA DE VENTA =====");
    System.out.println("Cliente: " + nombreCliente + " " + apellidoCliente);
    System.out.println("DNI: " + dni);
    System.out.println("Cantidad de habitaciones: " + cantidadHabitaciones);
    System.out.println(
      "Tipo de habitación: " +
      (
        tipoHabitacion == 1
          ? "Simple"
          : (tipoHabitacion == 2 ? "Doble" : "Matrimonial")
      )
    );
    System.out.println("Subtotal: " + String.format("%.2f", subtotal));
    System.out.println("IGV: " + String.format("%.2f", igv));
    System.out.println("Total a pagar: " + String.format("%.2f", totalPagar));
    System.out.println("Monto pagado: " + String.format("%.2f", montoPagado));
    System.out.println("Vuelto: " + String.format("%.2f", vuelto));
    System.out.println("===========================");
  }

  //  UBICACIÓN DE LA HABITACIÓN
  public void mostrarPisoHotel(int tipoHabitacion) {
    System.out.println("\n---- UBICACIÓN DE HABITACIÓN ----");
    int piso = (tipoHabitacion == 1) ? 1 : (tipoHabitacion == 2) ? 2 : 3;
    System.out.println("Su habitación está en el piso " + piso + ".");
    if (piso > 1) {
      System.out.println("Ingrese al elevador.");
      esperarElevador();
      mostrarPisos(piso);
    }
  }

  private void esperarElevador() {
    int respuesta;
    do {
      System.out.print("¿Ingresó al elevador? (0 = NO, 1 = SI): ");
      respuesta =
        validarEntradaNumerica(0, 1, "Ingrese 1 si ingresó al elevador: ");
    } while (respuesta != 1);
  }

  private void mostrarPisos(int pisoFinal) {
    for (int i = 1; i <= pisoFinal; i++) {
      System.out.println("PISO NRO #" + i);
    }
    System.out.println("Llegamos a su piso de destino.");
  }

  //  EJECUCIÓN DEL SISTEMA

  public static void main(String[] args) {
    Hotel hotel = new Hotel();
    hotel.registrarCuenta();
    if (!hotel.iniciarSesion()) {
      System.out.println("❌ No se pudo acceder al sistema.");
      return;
    }
    hotel.verificarEdad();
    hotel.scanner.close();
  }
}
