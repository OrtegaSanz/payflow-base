package com.nttdata.payflow.model;

/**
 * Empleado que cobra por horas trabajadas.
 */
public class EmpleadoPorHoras extends Empleado {

    private static final int LIMITE_HORAS = 160;
    private static final double TARIFA_EXTRA = 1.5;
    // TODO 11: declara los atributos privados tarifaHora (final) y horasTrabajadas.
    //          En el constructor valida tarifaHora > 0 (IllegalArgumentException).

    private final double tarifaHora;
    private int horasTrabajadas;


    public EmpleadoPorHoras(String id, String nombre, Area area, double tarifaHora) {
        super(id, nombre, area);
        this.tarifaHora = tarifaHora;
        this.horasTrabajadas = 0;
    }

    // TODO 12: registra las horas; si son negativas lanza IllegalArgumentException.
    public void registrarHoras(int horas) {
        if(horas < 0) {
            throw new IllegalArgumentException("Las horas no pueden ser negativas");
        }
        this.horasTrabajadas = horas;
    }

    // TODO 13 (RN-04): hasta 160 horas se pagan a la tarifa normal;
    //          las horas que superan 160 se pagan al 150% de la tarifa.
    @Override
    public double calcularPagoMensual() {
        double horasNormales = Math.min(horasTrabajadas, LIMITE_HORAS);
        double horasExtra = Math.max(0, horasTrabajadas - LIMITE_HORAS);
        return (horasNormales * tarifaHora) + (horasExtra * tarifaHora * TARIFA_EXTRA);
    }

    // TODO 14: debe devolver "Por horas".
    @Override
    public String getTipo() {
        return "Por horas";
    }
}
