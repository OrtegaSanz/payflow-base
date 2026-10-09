package com.nttdata.payflow.model;

/**
 * Empleado con sueldo fijo mensual.
 */
// TODO 5: implementa la interfaz Bonificable.
public class EmpleadoPlanilla extends Empleado implements Bonificable {

    private static final double ASIGNACION_FAMILIAR = 100.00;
    private static final double PORCENTAJE_BONO = 0.10;

    // TODO 6: declara los atributos privados y finales: sueldoBase, tieneHijos y evaluacion.
    private double sueldoBase;
    private boolean tieneHijos;
    private final int evaluacion;


    public EmpleadoPlanilla(String id, String nombre, Area area,
                            double sueldoBase, boolean tieneHijos, int evaluacion) {
        super(id, nombre, area);
        // TODO 7: valida sueldoBase > 0 y evaluacion entre 1 y 5 (IllegalArgumentException)
        //         y asigna los atributos.
        if(sueldoBase <= 0 || evaluacion < 1 || evaluacion > 5) {
            throw new IllegalArgumentException("El sueldo base debe ser mayor a 0");
        }
        this.sueldoBase = sueldoBase;
        this.tieneHijos = tieneHijos;
        this.evaluacion = evaluacion;
    }

    // TODO 8 (RN-03): bono = 10% del sueldo base si la evaluación es 4 o 5; en otro caso, 0.
    public double calcularBono() {
        return sueldoBase * (evaluacion >= 4 ? PORCENTAJE_BONO : 0);
    }

    // TODO 9 (RN-02): pago = sueldo base + asignación familiar (100.00 si tiene hijos) + bono.
    @Override
    public double calcularPagoMensual() {
        double pago = sueldoBase;
        if (tieneHijos) {
            pago += ASIGNACION_FAMILIAR;
        }
        pago += calcularBono();
        return pago;
    }

    // TODO 10: getTipo() debe devolver "Planilla" y getSueldoBase() el sueldo base.
    @Override
    public String getTipo() {
        return  "Planilla";
    }

    public double getSueldoBase() {
        return sueldoBase;
    }
}
