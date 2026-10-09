# PayFlow — Taller POO (proyecto base)

Proyecto base del **Taller 2: Programación Orientada a Objetos**. Completa los comentarios `// TODO` siguiendo el documento del taller.

## Requisitos
- JDK 17
- IntelliJ IDEA (Community o Ultimate)

## Abrir en IntelliJ IDEA
1. Descomprime el archivo.
2. **File > Open…** y selecciona la carpeta `payflow-base` (o su `pom.xml`). Elige **Trust Project** si lo solicita.
3. Verifica el SDK: **File > Project Structure > Project > SDK: 17**.

## Qué está resuelto y qué debes completar
| Archivo | Estado |
|---|---|
| `model/Area.java`, `model/Bonificable.java`, `model/Boleta.java` | Resuelto |
| `model/Practicante.java` | Resuelto (ejemplo a seguir) |
| `model/Empleado.java` | TODO 1 a 4 |
| `model/EmpleadoPlanilla.java` | TODO 5 a 10 |
| `model/EmpleadoPorHoras.java` | TODO 11 a 14 |
| `model/Gerente.java` | TODO 15 a 17 |
| `servicio/Nomina.java` | TODO 18 a 21 |
| `Main.java`, `validacion/Validador.java` | Resuelto (no modificar) |

## Cómo validar tu avance
Ejecuta `validacion/Validador.java` (botón ▶ junto a `main`). Muestra 20 verificaciones y tu **puntaje automático** sobre 20.
Al inicio el puntaje es 0: sube a medida que completas los TODO.

Cuando el Validador muestre 20/20, ejecuta `Main.java` para ver las boletas de pago.
