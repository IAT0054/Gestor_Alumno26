package principal;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Util {
    private static final Scanner scanner = new Scanner(System.in);

    public static String introducirCadena() {
        return scanner.nextLine().trim();
    }

    public static int introducirInt() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Error. Introduce un número entero: ");
            }
        }
    }

    public static LocalDate introducirFecha() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.print("Error. Formato incorrecto (AAAA-MM-DD). Inténtalo de nuevo: ");
            }
        }
    }

    public static boolean validarNIF(String nif) {
        if (nif == null)
            return false;
        nif = nif.toUpperCase();

        // Basic format check: 8 digits followed by 1 letter
        if (!Pattern.matches("\\d{8}[A-Z]", nif)) {
            return false;
        }

        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        try {
            int numero = Integer.parseInt(nif.substring(0, 8));
            char letraCorrecta = letras.charAt(numero % 23);
            return nif.charAt(8) == letraCorrecta;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean introducirBoolean() {
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("s") || input.equals("si") || input.equals("true"))
                return true;
            if (input.equals("n") || input.equals("no") || input.equals("false"))
                return false;
            System.out.print("Introduce 's' (sí) o 'n' (no): ");
        }
    }
}
