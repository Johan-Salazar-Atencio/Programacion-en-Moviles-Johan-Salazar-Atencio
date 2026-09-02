# Laboratorio 03: Diseño de interfaces con Jetpack Compose: Registro de Producto

**Curso:** Programación en Móviles  
**Docente:** Juan José León Suiyon  
**Estudiante:** Johan Salazar Atencio

---

## Descripción del Proyecto

Este proyecto implementa una interfaz gráfica nativa en Android utilizando **Jetpack Compose** para el registro de productos y el cálculo automático de importes. La aplicación permite ingresar el nombre del producto, precio unitario y cantidad mediante controles `OutlinedTextField`. Al presionar un botón de acción, procesa los datos mediante variables de estado (`remember` y `mutableStateOf`), aplicando conversiones seguras con `toDoubleOrNull` / `toIntOrNull` para desplegar una tarjeta resumen (`Card`) con el total calculado.

## Funciones y Lógica Implementada

* **Gestión de Estado:** Manejo de recomposición en tiempo real mediante `remember { mutableStateOf(...) }` para capturar la entrada del usuario en los campos de texto y controlar la visibilidad de la interfaz.
* **Layouts Proporcionales:** Organización de la interfaz mediante `Column` y `Row`, aplicando la propiedad `Modifier.weight(1f)` para distribuir simétricamente los campos de precio y cantidad en una misma fila.
* **Cálculo Financiero y Manejo de Errores:** Conversión de cadenas a datos numéricos mediante `toDoubleOrNull()` e `toIntOrNull()`, con el operador Elvis (`?:`) para evitar caídas por entradas inválidas y formateo numérico a dos decimales con `String.format("%.2f")`.
* **Componentes Material Design 3:** Uso de contenedores estilizados como `Card` con `MaterialTheme.colorScheme.primaryContainer` y jerarquía tipográfica adecuada según las reglas de diseño[cite: 1].

---

## Proceso de Desarrollo (Commits)

### Commit 1: Agrega encabezado con jerarquia tipografica
Se implementó el contenedor principal `Column` y los títulos de la pantalla aplicando `headlineSmall` y `bodyMedium` respetando la jerarquía tipográfica del tema[cite: 1].

<!-- PEGA TU CAPTURA AQUÍ -->


---

### Commit 2: Agrega campos de ingreso con estado
Se crearon las variables de estado reactivo y los controles `OutlinedTextField` para el nombre del producto, así como la distribución en fila (`Row`) para el precio y cantidad[cite: 1].

<!-- PEGA TU CAPTURA AQUÍ -->


---

### Commit 3: Agrega boton y card de resumen con importe calculado
Se añadió el control `Button` y la tarjeta de resumen `Card` que calcula dinámicamente el importe total al convertir y multiplicar los campos de texto[cite: 1].

<!-- PEGA TU CAPTURA AQUÍ -->


---

### Commit 4: Agrega mensaje de confirmación en verde
Se añadieron los espaciados estandarizados (`16.dp` / `24.dp`) y el mensaje visual de confirmación en color verde al registrar correctamente un producto[cite: 1].

<!-- PEGA TU CAPTURA AQUÍ -->


---

### Commit 5: Agrega README con capturas y respuesta sobre remember
Se completó la documentación del repositorio con la descripción técnica, el proceso de commits, evidencias y respuestas teóricas[cite: 1].

<!-- PEGA TU CAPTURA AQUÍ -->


---

## Pregunta de Reflexión

**¿Qué pasaría si declaras las variables de los campos SIN `remember`?**  
Si declaramos las variables únicamente con `mutableStateOf("")` sin envolverlas en `remember`, en cada recomposición (cada vez que el usuario escribe un carácter en el teclado y la pantalla se redibuja), la variable perdería su valor actual y volvería a inicializarse en vacía (`""`)[cite: 1]. La función `remember` le indica a Compose que debe conservar dicho valor guardado en memoria a lo largo de los redibujos de la interfaz[cite: 1].

---

## Resultado de Ejecución en la App

### Pantalla Inicial (Formulario Vacío)
<!-- PEGA AQUÍ LA CAPTURA DE LA PANTALLA INICIAL (FIGURA 1) -->


### Pantalla de Producto Registrado
<!-- PEGA AQUÍ LA CAPTURA CON EL PRODUCTO REGISTRADO Y LA CARD VERDE (FIGURA 2) -->