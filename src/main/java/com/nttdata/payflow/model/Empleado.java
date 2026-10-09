package com.nttdata.payflow.model;

/**
 * Clase base de todos los tipos de empleado.
 */
// TODO 1: convierte la clase en abstracta (no debe poder instanciarse).
public abstract class Empleado {

    // TODO 2: encapsula los atributos: deben ser private y final.
    private String id;
    private String nombre;
    private Area area;

    // TODO 3: valida (RN-01) y lanza IllegalArgumentException si:
    //         id o nombre son null o están vacíos, o area es null.
    protected Empleado(String id, String nombre, Area area) {
        if(id == null || id.isBlank() || nombre == null || nombre.isBlank() || area == null) {
            throw new IllegalArgumentException("Los campos no pueden ser nulos o vacíos");
        }
        this.id = id;
        this.nombre = nombre;
        this.area = area;
    }

    // TODO 4: convierte estos dos métodos en abstractos (sin cuerpo).
    public abstract double calcularPagoMensual();

    public abstract String getTipo();


    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Area getArea() {
        return area;
    }
}
