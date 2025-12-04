package principal;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;

public class Principal {
    private static ArrayList<Alumno> alumnos = new ArrayList<>();

    public static void main(String[] args) {
        String opcion;
        do {
            System.out.println("\n--- GESTIÓN DEL ALUMNADO ---");
            System.out.println("1. Matricular alumno/a");
            System.out.println("2. Listado de alumnos");
            System.out.println("3. Buscar alumno por NIF");
            System.out.println("4. Modificar datos de alumno");
            System.out.println("5 Modificar repetidor DAW por edad");
            System.out.println("6. Dar de baja alumno");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");
            opcion = Util.introducirCadena().toUpperCase();

            if (alumnos.isEmpty() && !opcion.equals("1") && !opcion.equals("7")) {
                System.out.println("AVISO: No hay alumnos introducidos.");
                continue;
            }

            switch (opcion) {
                case "1":
                    matricularAlumno();
                    break;
                case "2":
                    listarAlumnos();
                    break;
                case "3":
                    buscarAlumnoPorNif();
                    break;
                case "4":
                    modificarAlumno();
                    break;
                case "5":
                    modificarRepetidorDAW();
                    break;
                case "6":
                    bajaAlumno();
                    break;
                case "7":
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (!opcion.equals("7"));
    }

    private static void matricularAlumno() {
        do {
            System.out.print("Introduce NIF: ");
            String nif = Util.introducirCadena().toUpperCase();
            if (!Util.validarNIF(nif)) {
                System.out.println("NIF no válido.");
                continue;
            }

            // Check existing enrollments
            boolean enDam = false;
            boolean enDaw = false;
            for (Alumno a : alumnos) {
                if (a.getNif().equals(nif)) {
                    if (a.getCiclo() == Ciclo.DAM)
                        enDam = true;
                    if (a.getCiclo() == Ciclo.DAW)
                        enDaw = true;
                }
            }

            if (enDam && enDaw) {
                System.out.println("Alumno/a ya introducido en ambos ciclos.");
            } else {
                Ciclo ciclo = null;
                if (enDam) {
                    System.out.println("El alumno ya está en DAM. Solo puede matricularse en DAW.");
                    ciclo = Ciclo.DAW;
                } else if (enDaw) {
                    System.out.println("El alumno ya está en DAW. Solo puede matricularse en DAM.");
                    ciclo = Ciclo.DAM;
                } else {
                    System.out.print("Introduce ciclo (DAM/DAW): ");
                    try {
                        ciclo = Ciclo.valueOf(Util.introducirCadena().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ciclo no válido.");
                        continue;
                    }
                }

                System.out.print("Nombre: ");
                String nombre = Util.introducirCadena();
                System.out.print("Fecha nacimiento (AAAA-MM-DD): ");
                LocalDate fecha = Util.introducirFecha();
                System.out.print("¿Es repetidor? (s/n): ");
                boolean repetidor = Util.introducirBoolean();

                alumnos.add(new Alumno(nif, nombre, fecha, ciclo, repetidor));
                System.out.println("Alumno matriculado correctamente.");
            }

            System.out.print("¿Matricular otro alumno? (s/n): ");
        } while (Util.introducirBoolean());
    }

    private static void listarAlumnos() {
        System.out.println("\n--- LISTADO DE ALUMNOS ---");
        for (Alumno a : alumnos) {
            System.out.println(a);
        }
    }

    private static void buscarAlumnoPorNif() {
        System.out.print("Introduce NIF a buscar: ");
        String nif = Util.introducirCadena().toUpperCase();
        boolean encontrado = false;
        for (Alumno a : alumnos) {
            if (a.getNif().equals(nif)) {
                System.out.println(a);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("Error: No se encuentra alumno con ese NIF.");
        }
    }

    private static void modificarAlumno() {
        System.out.print("Introduce NIF del alumno a modificar: ");
        String nif = Util.introducirCadena().toUpperCase();

        // Find all records for this NIF (could be 1 or 2)
        ArrayList<Alumno> encontrados = new ArrayList<>();
        for (Alumno a : alumnos) {
            if (a.getNif().equals(nif)) {
                encontrados.add(a);
            }
        }

        if (encontrados.isEmpty()) {
            System.out.println("Error: No existe el alumno.");
            return;
        }

        System.out.println("Datos actuales:");
        for (Alumno a : encontrados) {
            System.out.println(a);
        }

        System.out.println("Introduce nuevos datos (el ciclo no se puede modificar):");
        System.out.print("Nuevo nombre: ");
        String nombre = Util.introducirCadena();
        System.out.print("Nueva fecha nacimiento (AAAA-MM-DD): ");
        LocalDate fecha = Util.introducirFecha();

        for (Alumno a : encontrados) {
            a.setNombre(nombre);
            a.setFechaNacimiento(fecha);
            System.out.print("¿Es repetidor en " + a.getCiclo() + "? (s/n): ");
            a.setRepetidor(Util.introducirBoolean());
        }
        System.out.println("Datos modificados.");
    }

    private static void modificarRepetidorDAW() {
        System.out.print("Introduce la edad: ");
        int edad = Util.introducirInt();
        boolean encontrado = false;
        LocalDate ahora = LocalDate.now();

        for (Alumno a : alumnos) {
            if (a.getCiclo() == Ciclo.DAW) {
                int edadAlumno = Period.between(a.getFechaNacimiento(), ahora).getYears();
                if (edadAlumno == edad) {
                    System.out.println("Modificando repetidor para " + a.getNombre() + " (NIF: " + a.getNif() + ")");
                    System.out.print("¿Es repetidor? (s/n): ");
                    a.setRepetidor(Util.introducirBoolean());
                    encontrado = true;
                }
            }
        }

        if (!encontrado) {
            System.out.println("No existen alumnos de DAW con esa edad.");
        }
    }

    private static void bajaAlumno() {
        System.out.print("Introduce NIF para dar de baja: ");
        String nif = Util.introducirCadena().toUpperCase();

        ArrayList<Alumno> aBorrar = new ArrayList<>();
        for (Alumno a : alumnos) {
            if (a.getNif().equals(nif)) {
                aBorrar.add(a);
            }
        }

        if (aBorrar.isEmpty()) {
            System.out.println("Error: No se encuentra el alumno.");
            return;
        }

        System.out.println("Se eliminarán las siguientes matrículas:");
        for (Alumno a : aBorrar) {
            System.out.println(a);
        }

        System.out.print("¿Confirmar baja? (s/n): ");
        if (Util.introducirBoolean()) {
            alumnos.removeAll(aBorrar);
            System.out.println("Alumno dado de baja.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
}
