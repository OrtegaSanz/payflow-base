package com.nttdata.payflow.model;

/**
 * EJEMPLO RESUELTO: úsalo como guía para las demás subclases.
 * RN-06: el practicante recibe una subvención fija y no recibe bonos.
 */
public class Practicante extends Empleado {
    private final double subvencion;

    public Practicante(String id, String nombre, Area area, double subvencion) {
        super(id, nombre, area);
        if (subvencion <= 0) {
            throw new IllegalArgumentException("La subvención debe ser positiva");
        }
        this.subvencion = subvencion;
    }

    @Override
    public double calcularPagoMensual() {
        return subvencion;
    }

    @Override
    public String getTipo() {
        return "Practicante";
    }

    public double getSubvencion() {
        return subvencion;
    }
}
