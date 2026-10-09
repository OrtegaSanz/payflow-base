package com.nttdata.payflow;

import com.nttdata.payflow.model.Area;
import com.nttdata.payflow.model.Boleta;
import com.nttdata.payflow.model.EmpleadoPlanilla;
import com.nttdata.payflow.model.EmpleadoPorHoras;
import com.nttdata.payflow.model.Gerente;
import com.nttdata.payflow.model.Practicante;
import com.nttdata.payflow.servicio.Nomina;

import java.util.Locale;

/** Demostración del sistema. (Resuelto: funciona cuando completes todos los TODO) */
public class Main {
    public static void main(String[] args) {
        Nomina nomina = new Nomina();

        EmpleadoPorHoras bruno = new EmpleadoPorHoras("E002", "Bruno Díaz", Area.VENTAS, 20);
        bruno.registrarHoras(170);

        nomina.agregar(new EmpleadoPlanilla("E001", "Ana Torres", Area.TI, 3000, true, 4));
        nomina.agregar(bruno);
        nomina.agregar(new Gerente("E003", "Carla Ruiz", Area.TI, 8000, false, 5));
        nomina.agregar(new Practicante("E004", "Diego León", Area.RRHH, 1200));

        System.out.println("=== Boletas de pago ===");
        for (Boleta b : nomina.generarBoletas()) {
            System.out.printf(Locale.US, "%s | %-11s | %-11s | %9.2f%n",
                    b.idEmpleado(), b.nombre(), b.tipo(), b.monto());
        }
        System.out.printf(Locale.US, "Total nómina: %.2f%n", nomina.totalNomina());
        System.out.printf(Locale.US, "Total área TI: %.2f%n", nomina.totalPorArea(Area.TI));
    }
}
