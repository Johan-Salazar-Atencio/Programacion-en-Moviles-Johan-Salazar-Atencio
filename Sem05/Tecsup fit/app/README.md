# Informe de Proyecto: TecsupFit App

**Curso:** Desarrollo de Aplicaciones Móviles  
**Institución:** TECSUP  
**Estudiante:** Johan Salazar  
**Tecnologías:** Kotlin, Android Studio, Jetpack Compose, Material Design 3, Git & GitHub, Generative AI Agent

---

## I. Descripción del Proyecto

**TecsupFit** es una aplicación móvil nativa diseñada para la gestión y reserva de clases dentro de un gimnasio universitario. La aplicación permite a los usuarios explorar clases disponibles, ver detalles de horarios y cupos, realizar reservas, consultar su historial y calcular métricas de salud como el IMC (Índice de Masa Corporal).

El proyecto consta de dos partes principales:
1. **Desarrollo de Commits Base (Fase 1):** Construcción paso a paso de la arquitectura de la app usando Jetpack Compose, `NavHost`, `Scaffold` y transferencia de datos.
2. **Mejoras con Inteligencia Artificial (Fase 2):** Rediseño estético, adición de rutinas interactivas y calculadora de IMC.

---

## II. Fase 1: Desarrollo Secuencial (Commits 01 al 09)

### Commit 01: Definición de Rutas de Navegación (`Screen.kt`)
- **Archivos modificados:** `Screen.kt`
- **Descripción:** Creación de la clase sellada para manejar todas las rutas y argumentos de la navegación de forma segura.


---

### Commit 02: Configuración Inicial del NavHost (`AppNavigation.kt` y `MainActivity.kt`)
- **Archivos modificados:** `AppNavigation.kt`, `MainActivity.kt`
- **Descripción:** Implementación del contenedor principal de navegación `NavHost` acoplado al `NavController`.


---

### Commit 03: Diseño de Pantalla Principal (`HomeScreen.kt`)
- **Archivos modificados:** `HomeScreen.kt`
- **Descripción:** Maquetación de la pantalla de inicio con lista de clases disponibles (`LazyColumn`), barra de filtros (`LazyRow`) y la barra de navegación inferior (`NavigationBar`).


---

### Commit 04: Ruta y Argumentos de Detalle en NavHost
- **Archivos modificados:** `AppNavigation.kt`
- **Descripción:** Declaración de la ruta con argumento de tipo entero (`classId`) en el `NavHost` para conectar con la pantalla de detalle.


---

### Commit 05: Implementación de la Pantalla de Detalle (`DetailScreen.kt`)
- **Archivos modificados:** `DetailScreen.kt`
- **Descripción:** Desarrollo de la interfaz de detalle mostrando la información específica de la clase seleccionada, horario, salón y botón para reservar.


---

### Commit 06: Configuración de Ruta de Confirmación
- **Archivos modificados:** `AppNavigation.kt`
- **Descripción:** Definición de la ruta de confirmación en el `NavHost` configurada para recibir múltiples parámetros de la reserva.


---

### Commit 07: Pantalla de Confirmación de Reserva (`ConfirmationScreen.kt`)
- **Archivos modificados:** `ConfirmationScreen.kt`
- **Descripción:** Diseño de la pantalla de éxito con confirmación visual de la reserva y botón para ver el historial.


---

### Commit 08: Pantalla de Mis Reservas (`ReservasScreen.kt`)
- **Archivos modificados:** `ReservasScreen.kt`
- **Descripción:** Vista del historial de clases reservadas por el usuario, mostrando estados de confirmación y detalles del evento.


---

### Commit 09: Pantalla de Perfil de Usuario (`ProfileScreen.kt`)
- **Archivos modificados:** `ProfileScreen.kt`
- **Descripción:** Maquetación del perfil con avatar, nivel de suscripción del usuario y estadísticas de asistencia.


---

## III. Fase 2: Mejora Funcional y Rediseño con Inteligencia Artificial

### Prompt 01: Rediseño Visual Moderno con Degradados y Animaciones
- **Prompt enviado a la IA:**
  > "Rediseña visualmente todas las pantallas (@HomeScreen.kt, @DetailScreen.kt, @ConfirmationScreen.kt, @ReservasScreen.kt y @ProfileScreen.kt) aplicando degradados (`Brush.verticalGradient`), elevación de tarjetas (`CardDefaults.cardElevation`), bordes redondeados y animaciones con `animateColorAsState` para los filtros."
- **Resultado:** Interfaz moderna con paleta verde corporativa (`#00695C`), bordes suaves y animaciones de selección.


---

### Prompt 02: Implementación de `RutinasScreen.kt` y Enrutamiento Global
- **Prompt enviado a la IA:**
  > "Crea @RutinasScreen.kt con tarjetas de rutinas (Fuerza, HIIT) e incluye un checklist interactivo de ejercicios (`Checkbox`). Luego actualiza @AppNavigation.kt para gestionar 4 pestañas en la barra inferior (Home, Reservas, Rutinas, Perfil)."
- **Resultado:** Creación e integración de la pestaña de rutinas de entrenamiento con lista interactiva.


---

### Prompt 03: Calculadora de IMC Interactiva en Perfil
- **Prompt enviado a la IA:**
  > "Agrega un módulo interactivo de Calculadora de IMC en @ProfileScreen.kt con dos campos (`OutlinedTextField`) para Peso (kg) y Altura (cm), botón de cálculo, clasificación del resultado y recomendación personalizada de clases."
- **Resultado:** Implementación de la calculadora de IMC funcional con sugerencia dinámica de clases.


---

## IV. Preguntas para la Sustentación

### 1. ¿Cómo llega el ítem elegido en Inicio (médico o clase) hasta la pantalla de confirmación? Describe la ruta completa del dato.
1. **Selección en `HomeScreen`:** Se pulsa la tarjeta de la clase deseada y se envía la clase seleccionada al callback.
2. **Navegación a Detalle:** El controlador navega hacia la pantalla de detalle enviando el ID de la clase como argumento en la ruta.
3. **Recepción en `DetailScreen`:** El `NavHost` extrae el ID de la ruta, encuentra la clase correspondiente y carga sus datos en la vista.
4. **Navegación a Confirmación:** Al hacer clic en reservar, se llama a la ruta de confirmación enviando los datos clave de la clase como parámetros.
5. **Renderizado Final:** `ConfirmationScreen` recibe los argumentos y presenta el resumen visual de la reserva confirmada.

---

### 2. Si elegiste la Opción B (Bottom Navigation Bar): ¿cómo sabe el bottomBar cuál ícono resaltar en cada pantalla?
El `NavigationBar` evalúa la condición booleana del atributo `selected` en cada uno de los ítems:
- En cada pantalla se define el índice correspondiente a su pestaña.
- Durante la construcción de la barra, se compara el índice de cada botón con el índice de la pantalla activa:
  ```
  selected = (index == INDEX_ACTIVO)
  ```
- Si es verdadero, Compose aplica los estilos, colores e indicador de selección activo.

---

### 3. ¿Por qué la selección de fecha/hora (o de horario) se comporta como un RadioButton, aunque visualmente sean "chips"?
Se comporta como un `RadioButton` debido a la gestión de estado de **Exclusividad Mutua**:
- Se utiliza una única variable de estado mutable para almacenar la opción elegida.
- Cuando el usuario hace clic en un chip, dicha variable actualiza su valor únicamente al nombre de la opción presionada.
- Como la variable solo puede tener un valor al mismo tiempo, el estado `isSelected` resulta positivo únicamente para un chip y falso para los demás.

---

### 4. ¿Qué tuviste que corregir del código que te generó la IA para tu mejora de la Fase 2?
1. **Validación de entradas en la Calculadora IMC:** Se agregaron controles de error (`try-catch`) para evitar cierres inesperados si el usuario no ingresaba números válidos en los campos de texto.
2. **Importaciones de Íconos:** Se corrigieron varias rutas de importación de íconos que venían desactualizadas en la respuesta de la IA.
3. **Manejo del Backstack:** Se configuró `launchSingleTop = true` en las transiciones de la barra inferior para no acumular pantallas repetidas en el historial de navegación.

---

## V. Conclusiones
1. **Jetpack Compose** permite construir interfaces modernas de forma declarativa e intuitiva.
2. La arquitectura con **`NavHost`** garantiza el paso claro y seguro de parámetros entre pantallas.
3. La **Inteligencia Artificial** acelera el desarrollo de nuevas funciones y estética, requiriendo siempre la revisión manual del programador para corregir errores.