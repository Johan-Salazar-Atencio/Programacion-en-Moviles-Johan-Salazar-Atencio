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

<img width="1141" height="625" alt="image" src="https://github.com/user-attachments/assets/71a84477-1ac8-4520-959e-1f7e96121670" />



---

### Commit 2: Agrega campos de ingreso con estado
Se crearon las variables de estado reactivo y los controles `OutlinedTextField` para el nombre del producto, así como la distribución en fila (`Row`) para el precio y cantidad[cite: 1].

<img width="674" height="421" alt="Imagen1" src="https://github.com/user-attachments/assets/8ac072bb-e707-4ed4-ac03-497f02db479a" />


---

### Commit 3: Agrega boton y card de resumen con importe calculado
Se añadió el control `Button` y la tarjeta de resumen `Card` que calcula dinámicamente el importe total al convertir y multiplicar los campos de texto[cite: 1].

<img width="1095" height="846" alt="image" src="https://github.com/user-attachments/assets/70b97862-caa8-42d0-8537-1759b8a10f46" />


---

### Commit 4: Agrega mensaje de confirmación en verde
Se añadieron los espaciados estandarizados (`16.dp` / `24.dp`) y el mensaje visual de confirmación en color verde al registrar correctamente un producto.

<img width="895" height="502" alt="image" src="https://github.com/user-attachments/assets/eef2812b-46c6-44d4-ac41-fa368133e508" />
<img width="843" height="435" alt="Imagen2" src="https://github.com/user-attachments/assets/b747a869-922c-4beb-863e-82305e359eae" />



---

### Commit 5: Agrega README con capturas y respuesta sobre remember
Se completó la documentación del repositorio con la descripción técnica, el proceso de commits, evidencias y respuestas teóricas.

<img width="1228" height="953" alt="image" src="https://github.com/user-attachments/assets/c1bc52c1-c8a3-48ae-a81d-8f0d23dd8597" />



---

## Pregunta de Reflexión

**¿Qué pasaría si declaras las variables de los campos SIN `remember`?**  
Si declaramos las variables únicamente con `mutableStateOf("")` sin envolverlas en `remember`, en cada recomposición (cada vez que el usuario escribe un carácter en el teclado y la pantalla se redibuja), la variable perdería su valor actual y volvería a inicializarse en vacía (`""`). La función `remember` le indica a Compose que debe conservar dicho valor guardado en memoria a lo largo de los redibujos de la interfaz.

---

## Resultado de Ejecución en la App

### Pantalla Inicial (Formulario Vacío)
<img width="432" height="817" alt="image" src="https://github.com/user-attachments/assets/21b7eee7-71e7-4af9-b086-90c153069ca5" />



### Pantalla de Producto Registrado
<img width="391" height="828" alt="image" src="https://github.com/user-attachments/assets/238a4b9c-b824-4fef-93e9-145fb2dc6f65" />


### Pantalla Girada 
<img width="622" height="775" alt="image" src="https://github.com/user-attachments/assets/2907e917-7c8b-45b9-bb65-44cba22e7061" />
