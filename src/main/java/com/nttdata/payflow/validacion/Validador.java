package com.nttdata.payflow.validacion;

import com.nttdata.payflow.model.Area;
import com.nttdata.payflow.model.Bonificable;
import com.nttdata.payflow.model.Boleta;
import com.nttdata.payflow.model.Empleado;
import com.nttdata.payflow.model.EmpleadoPlanilla;
import com.nttdata.payflow.model.EmpleadoPorHoras;
import com.nttdata.payflow.model.Gerente;
import com.nttdata.payflow.model.Practicante;
import com.nttdata.payflow.servicio.Nomina;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Ejecuta 20 verificaciones automáticas sobre tu solución. (Resuelto: no modificar)
 * Cada verificación vale 1 punto.
 */
public class Validador {

    @FunctionalInterface
    interface Prueba {
        boolean ejecutar() throws Exception;
    }

    private static final Map<String, int[]> RESUMEN = new LinkedHashMap<>();
    private static int numero = 0;

    public static void main(String[] args) {
        System.out.println("PayFlow - Validación automática del taller");
        System.out.println("==========================================");

        // ---------------- Encapsulamiento (5) ----------------
        verificar("Encapsulamiento", "Empleado rechaza un nombre vacío",
                () -> lanza(IllegalArgumentException.class, () -> new Practicante("X01", " ", Area.TI, 1000)));
        verificar("Encapsulamiento", "EmpleadoPorHoras rechaza horas negativas",
                () -> lanza(IllegalArgumentException.class, () -> new EmpleadoPorHoras("X02", "Test", Area.TI, 10).registrarHoras(-5)));
        verificar("Encapsulamiento", "Atributos de Empleado son private",
                () -> atributosPrivados(Empleado.class, 3));
        verificar("Encapsulamiento", "Atributos de EmpleadoPlanilla y EmpleadoPorHoras son private",
                () -> atributosPrivados(EmpleadoPlanilla.class, 3) && atributosPrivados(EmpleadoPorHoras.class, 2));
        verificar("Encapsulamiento", "Nomina.getEmpleados() no permite modificar la lista",
                () -> {
                    Nomina n = new Nomina();
                    n.agregar(new Practicante("X03", "Test", Area.TI, 1000));
                    return lanza(UnsupportedOperationException.class, () -> n.getEmpleados().clear());
                });

        // ---------------- Herencia (4) ----------------
        verificar("Herencia", "Gerente hereda de EmpleadoPlanilla",
                () -> EmpleadoPlanilla.class.equals(Gerente.class.getSuperclass()));
        verificar("Herencia", "Gerente reutiliza atributos y cálculo del pago de su padre",
                () -> Arrays.stream(Gerente.class.getDeclaredFields()).noneMatch(f -> !Modifier.isStatic(f.getModifiers()))
                        && Arrays.stream(Gerente.class.getDeclaredMethods()).noneMatch(m -> m.getName().equals("calcularPagoMensual")));
        verificar("Herencia", "El constructor de Gerente inicializa los datos con super",
                () -> {
                    Object g = new Gerente("X04", "Gina", Area.FINANZAS, 8000, false, 3);
                    EmpleadoPlanilla ep = (EmpleadoPlanilla) g;
                    return ep.getNombre().equals("Gina") && ep.getArea() == Area.FINANZAS && ep.getSueldoBase() == 8000;
                });
        verificar("Herencia", "Gerente es Bonificable por heredar de EmpleadoPlanilla",
                () -> {
                    Object g = new Gerente("X05", "Gino", Area.TI, 5000, false, 3);
                    return g instanceof Bonificable;
                });

        // ---------------- Polimorfismo (5) ----------------
        verificar("Polimorfismo", "Planilla sin hijos y evaluación 3: pago = 3000.00",
                () -> igual(new EmpleadoPlanilla("P01", "Ana", Area.TI, 3000, false, 3).calcularPagoMensual(), 3000));
        verificar("Polimorfismo", "Planilla con hijos y evaluación 4: pago = 3400.00",
                () -> igual(new EmpleadoPlanilla("P02", "Ana", Area.TI, 3000, true, 4).calcularPagoMensual(), 3400));
        verificar("Polimorfismo", "Por horas, 170 h a 20.00: pago = 3500.00",
                () -> {
                    EmpleadoPorHoras e = new EmpleadoPorHoras("P03", "Bruno", Area.VENTAS, 20);
                    e.registrarHoras(170);
                    return igual(e.calcularPagoMensual(), 3500);
                });
        verificar("Polimorfismo", "Gerente con sueldo 8000 y evaluación 5: pago = 10400.00",
                () -> igual(new Gerente("P04", "Carla", Area.TI, 8000, false, 5).calcularPagoMensual(), 10400));
        verificar("Polimorfismo", "getTipo() devuelve un valor distinto por cada clase",
                () -> {
                    EmpleadoPorHoras h = new EmpleadoPorHoras("P05", "B", Area.TI, 10);
                    List<Empleado> lista = List.of(new EmpleadoPlanilla("P06", "A", Area.TI, 1000, false, 1), h,
                            new Gerente("P07", "C", Area.TI, 1000, false, 1), new Practicante("P08", "D", Area.TI, 500));
                    return lista.stream().map(Empleado::getTipo).toList()
                            .equals(List.of("Planilla", "Por horas", "Gerente", "Practicante"));
                });

        // ---------------- Abstracción (3) ----------------
        verificar("Abstracción", "Empleado es una clase abstracta",
                () -> Modifier.isAbstract(Empleado.class.getModifiers()));
        verificar("Abstracción", "calcularPagoMensual() y getTipo() son abstractos en Empleado",
                () -> metodoAbstracto("calcularPagoMensual") && metodoAbstracto("getTipo"));
        verificar("Abstracción", "EmpleadoPlanilla implementa la interfaz Bonificable",
                () -> Arrays.asList(EmpleadoPlanilla.class.getInterfaces()).contains(Bonificable.class));

        // ---------------- Composición y colecciones (3) ----------------
        verificar("Composición", "Nomina rechaza empleados con id duplicado",
                () -> {
                    Nomina n = new Nomina();
                    n.agregar(new Practicante("C01", "Uno", Area.TI, 1000));
                    return lanza(IllegalArgumentException.class, () -> n.agregar(new Practicante("C01", "Dos", Area.TI, 1000)));
                });
        verificar("Composición", "Totales: nómina = 18500.00 y área TI = 13800.00",
                () -> {
                    Nomina n = nominaDeEjemplo();
                    return igual(n.totalNomina(), 18500) && igual(n.totalPorArea(Area.TI), 13800);
                });
        verificar("Composición", "generarBoletas() crea una boleta por empleado con su monto",
                () -> {
                    List<Boleta> b = nominaDeEjemplo().generarBoletas();
                    return b.size() == 4 && b.get(0).idEmpleado().equals("E001") && igual(b.get(0).monto(), 3400)
                            && b.get(2).tipo().equals("Gerente") && igual(b.get(3).monto(), 1200);
                });

        // ---------------- Resumen ----------------
        System.out.println();
        System.out.println("Resumen por criterio");
        System.out.println("--------------------");
        int total = 0;
        for (Map.Entry<String, int[]> e : RESUMEN.entrySet()) {
            System.out.printf("%-16s %d / %d%n", e.getKey(), e.getValue()[0], e.getValue()[1]);
            total += e.getValue()[0];
        }
        System.out.println("--------------------");
        System.out.printf("PUNTAJE AUTOMÁTICO: %d / 20%n", total);
    }

    // ---------------- utilidades ----------------
    private static void verificar(String criterio, String descripcion, Prueba prueba) {
        numero++;
        boolean ok;
        String detalle = "";
        try {
            ok = prueba.ejecutar();
        } catch (UnsupportedOperationException ex) {
            ok = false;
            detalle = "  (pendiente: " + ex.getMessage() + ")";
        } catch (Throwable ex) {
            ok = false;
            detalle = "  (" + ex.getClass().getSimpleName() + ")";
        }
        int[] r = RESUMEN.computeIfAbsent(criterio, k -> new int[2]);
        r[1]++;
        if (ok) r[0]++;
        System.out.printf("%s %2d. [%s] %s%s%n", ok ? "[OK]" : "[X] ", numero, criterio, descripcion, detalle);
    }

    private static boolean lanza(Class<? extends Throwable> esperado, Runnable accion) {
        try {
            accion.run();
            return false;
        } catch (Throwable ex) {
            return esperado.isInstance(ex);
        }
    }

    private static boolean igual(double a, double b) {
        return Math.abs(a - b) < 0.01;
    }

    private static boolean atributosPrivados(Class<?> clase, int minimo) {
        Field[] campos = Arrays.stream(clase.getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers())).toArray(Field[]::new);
        return campos.length >= minimo && Arrays.stream(campos).allMatch(f -> Modifier.isPrivate(f.getModifiers()));
    }

    private static boolean metodoAbstracto(String nombre) throws NoSuchMethodException {
        Method m = Empleado.class.getDeclaredMethod(nombre);
        return Modifier.isAbstract(m.getModifiers());
    }

    private static Nomina nominaDeEjemplo() {
        Nomina n = new Nomina();
        EmpleadoPorHoras bruno = new EmpleadoPorHoras("E002", "Bruno", Area.VENTAS, 20);
        bruno.registrarHoras(170);
        n.agregar(new EmpleadoPlanilla("E001", "Ana", Area.TI, 3000, true, 4));
        n.agregar(bruno);
        n.agregar(new Gerente("E003", "Carla", Area.TI, 8000, false, 5));
        n.agregar(new Practicante("E004", "Diego", Area.RRHH, 1200));
        return n;
    }
}
