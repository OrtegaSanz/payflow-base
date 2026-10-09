package com.nttdata.payflow.model;

/** Boleta de pago inmutable (Java 17 record). (Resuelto) */
public record Boleta(String idEmpleado, String nombre, String tipo, double monto) {
}
