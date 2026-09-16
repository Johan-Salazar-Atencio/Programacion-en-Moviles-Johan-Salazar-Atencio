# Laboratorio 03: Sistema de Cálculo de Cuotas y Cronograma de Pagos en Kotlin

**Curso:** Programación en Móviles  
**Docente:** Juan José León Suiyon  
**Estudiante:** Johan Salazar Atencio  

---

## Descripción del Proyecto

Este proyecto implementa un sistema de financiamiento por consola en **Kotlin** para el cálculo de cuotas y la generación de un calendario de pagos mensual. El sistema solicita al usuario el nombre del producto, precio, cantidad y el número de cuotas deseadas (6, 12 o 24 meses), aplicando tasas de interés progresivas y generando una tabla de amortización detallada hasta liquidar el saldo a cero.

## Funciones y Lógica Implementada

* **Lectura de Entradas:** Captura de datos del producto (`producto`, `precio`, `cantidad`, `cuotas`) mediante la clase `Scanner` con habilitación de lectura interactiva por consola.
* **Validación de Tasa de Interés:** Aplicación de la estructura condicional `when` para evaluar el plazo de financiamiento:
  * 6 cuotas $\rightarrow$ 20% de interés.
  * 12 cuotas $\rightarrow$ 40% de interés.
  * 24 cuotas $\rightarrow$ 60% de interés.
* **Cálculo Financiero:** Generación del monto inicial ($P \times C$), cálculo del interés, monto total a pagar y valor del pago mensual fija.
* **Generación de Cronograma:** Bucle `for` dinámico utilizando la clase `Calendar` y `SimpleDateFormat` para simular las fechas mensuales de pago, descontando el valor de la cuota en cada período hasta restar a S/ 0.00.

---

## Proceso de Desarrollo (Commits)

### Commit 1: Estructura inicial del programa en Main.kt
Se creó la estructura principal con la función `main()` y la configuración del menú inicial por consola.

<img width="886" height="468" alt="image" src="https://github.com/user-attachments/assets/86f577ae-5978-4d8f-bf5f-41cf28b9939e" />


---

### Commit 2: Configuración de tarea Gradle interactiva
Se configuró la tarea `ejecutarCuotas` de tipo `JavaExec` en el archivo `build.gradle.kts (:app)` asignando `standardInput = System.`in`` para permitir la entrada de datos por teclado.

<img width="886" height="505" alt="image" src="https://github.com/user-attachments/assets/8471d55d-743d-4cdc-a335-ec462fc25b6c" />


---

### Commit 3: Captura de inputs y asignación de tasa de interés
Se agregó la lectura interactiva de variables y la lógica condicional `when` para validar las cuotas permitidas (6, 12, 24) y sus respectivos porcentajes de interés.

<img width="886" height="225" alt="image" src="https://github.com/user-attachments/assets/df0ba97c-4701-4cdd-b3aa-bb03be62f066" />


---

### Commit 4: Cálculo de totales y resumen financiero
Se implementaron las fórmulas matemáticas para determinar el Monto Inicial, el Interés acumulado, el Monto a Pagar final y la cuota de Pago Mensual.

<img width="886" height="316" alt="image" src="https://github.com/user-attachments/assets/182cc8cd-2999-4573-bed6-f0dd73a81a97" />


---

### Commit 5: Calendario de pagos y deducción progresiva del saldo
Se programó la iteración mensual con manejo de fechas reales y formateo numérico para imprimir la tabla con los saldos anteriores, pagos aplicados y resto pendiente.

<img width="886" height="846" alt="image" src="https://github.com/user-attachments/assets/e88f61fb-418f-447f-82d7-f286762bd6b2" />


---

## Resultado de Ejecución en Consola
<img width="713" height="1015" alt="image" src="https://github.com/user-attachments/assets/dc912b61-bff8-48e5-94ab-7f7a28bab925" />



