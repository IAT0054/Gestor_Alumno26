package principal;

import java.time.LocalDate;

public class Alumno {
    private String nif;
    private String nombre;
    private LocalDate fechaNacimiento;
    private Ciclo ciclo;
    private boolean repetidor;

    public Alumno(String nif, String nombre, LocalDate fechaNacimiento, Ciclo ciclo, boolean repetidor) {
        this.nif = nif;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.ciclo = ciclo;
        this.repetidor = repetidor;
    }

    public String getNif() {
        return nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public boolean isRepetidor() {
        return repetidor;
    }

    public void setRepetidor(boolean repetidor) {
        this.repetidor = repetidor;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nif='" + nif + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", ciclo=" + ciclo +
                ", repetidor=" + (repetidor ? "Sí" : "No") +
                '}';
    }
}
