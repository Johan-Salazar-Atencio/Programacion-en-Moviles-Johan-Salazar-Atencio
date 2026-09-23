# Laboratorio 05: Navegación y Rediseño UI en Jetpack Compose

**Curso:** Programación en Móviles  
**Docente:** Juan José León Suiyon  
**Estudiante:** Johan Salazar Atencio

---

## Descripción del Proyecto

Este proyecto implementa la arquitectura de navegación entre múltiples pantallas en una aplicación Android desarrollada con **Jetpack Compose** y **Navigation Component**. Se abordan conceptos como la definición de rutas con `sealed class`, el paso de argumentos dinámicos entre pantallas, la gestión de la pila de navegación (*backstack*) y el rediseño completo de la interfaz hacia un **Portal Académico** estilizado con componentes de **Material 3**.

---

## Componentes y Funcionalidades Implementadas

- **Navegación Centralizada con `NavHost`**: Gestión del flujo de pantallas mediante `NavController` y `NavHost`.
- **Rutas Seguras (`Screen.kt`)**: Estructuración de rutas mediante `sealed class` para evitar errores de tipeo en las cadenas de navegación.
- **Paso de Parámetros Dinámicos**: Enrutamiento con argumentos tipados (`detail/{itemId}`) procesados en `NavBackStackEntry`.
- **Control del Backstack (`popUpTo`)**: Limpieza de la pila de navegación al regresar al inicio o cerrar sesión para evitar la acumulación de pantallas en memoria.
- **Rediseño UI / Portal Académico (IA)**: Implementación de pantalla de Login, tarjetas con bordes redondeados (`Card`), degradados verticales (`Brush.verticalGradient`), avatares circulares e íconos de Material Design 3.

---

## Pasos de Desarrollo y Commits

### Primer Commit: Configuración de Dependencias
Inclusión de la librería `androidx.navigation:navigation-compose` en el archivo `build.gradle.kts`.



---

### Segundo Commit: Definición de Rutas (`Screen.kt`)
Creación de la clase sellada `Screen` para definir las rutas `home`, `list`, `profile` y la ruta con argumento `detail/{itemId}`.



---

### Tercer Commit: Estructura de Navegación (`AppNavigation.kt`)
Configuración del contenedor `NavHost` definiendo el punto de inicio y las composables para cada pantalla.



---

### Cuarto Commit: Vinculación en `MainActivity.kt`
Integración del composable `AppNavigation()` dentro del `setContent` del punto de entrada principal.



---

### Quinto Commit: Implementación de `HomeScreen.kt`
Diseño de la pantalla de inicio con botones de navegación hacia la lista y el perfil.



---

### Sexto Commit: Implementación de `ListScreen.kt`
Creación de la pantalla de directorio con `LazyColumn` y botones para navegar al detalle pasando el ID del elemento.



---

### Séptimo Commit: Implementación de `DetailScreen.kt`
Recepción y renderizado del parámetro `itemId` recibido desde la ruta de navegación.



---

### Octavo Commit: Implementación de `ProfileScreen.kt`
Creación de la pantalla de perfil utilizando `popUpTo` para limpiar la pila de navegación al volver al inicio.



---

## Resultados de la App Base

Capturas de la aplicación funcionando con la estructura de navegación inicial:









---

## Rediseño UI con IA (Portal Académico)

Se aplicó un prompt de refinamiento visual para transformar la interfaz en un **Portal Académico** profesional con esquema de colores morado/púrpura, tarjetas elevadas, avatares y una nueva pantalla de **Login**.

## Prompt
Actúa como un experto en UI/UX para Android y Jetpack Compose Material 3. Requiero rediseñar por completo las pantallas de mi proyecto actual (Lab05-Navegacion) y agregar la pantalla de inicio de sesión para que el flujo completo coincida con el "Portal Académico" de la guía.

--- 1. PALETA DE COLORES Y ESTILOS GLOBALES ---
- Fondo con Degradado: Usa 'Brush.verticalGradient' desde un morado/púrpura medio/oscuro (ej. Color(0xFF6B52A1) o Color(0xFF523D7F)) en la parte superior, hacia un lila/blanco suave (ej. Color(0xFFF3EDF7)) en la parte inferior.
- Tarjetas (Cards): Esquinas muy redondeadas ('RoundedCornerShape(20.dp)'), fondo blanco ('Color.White') o lila clarísimo, con elevación suave ('CardDefaults.cardElevation(defaultElevation = 4.dp)').
- Tipografía: Títulos en negrita ('FontWeight.Bold'), textos secundarios en gris/púrpura atenuado.
- Botones secundarios / Acción de salida: Ícono de 'ExitToApp' o 'AutoMirrored.Outlined.ExitToApp' con texto en rojo/vino ('Color(0xFFC62828)').

--- 2. NAVEGACIÓN ---
- En 'AppNavigation.kt' y 'Screen.kt', define e incluye 'Login' como la ruta inicial ('startDestination').

--- 3. PANTALLAS DEL PROYECTO ---

A) LoginScreen.kt ("Portal Académico" - Pantalla Inicial):
- Fondo con degradado completo.
- Tarjeta blanca centrada en pantalla ('RoundedCornerShape(20.dp)'):
    * Título: "Portal Académico" (Púrpura, Bold).
    * Subtítulo: "Accede a tu cuenta".
    * Campo 'Correo Institucional' con ícono de email a la izquierda.
    * Campo 'Contraseña' con ícono de candado y botón para ocultar/mostrar texto.
    * Botón ancho "INICIAR SESIÓN" en púrpura (al hacer clic navega a HomeScreen).
    * Texto en la parte inferior: "¿Olvidaste tu contraseña?".

B) HomeScreen.kt ("Panel Principal"):
- Fondo completo con degradado púrpura a lila claro.
- Encabezado con texto blanco/claro:
    * "Bienvenido, Johan Salazar" (HeadlineMedium, Bold).
    * "¿Qué deseas gestionar hoy?" (BodyMedium, semi-transparente o lila claro).
- 2 Tarjetas principales de navegación (blancas, esquinas de 20.dp):
    1. "Directorio de Alumnos" (Subtítulo: "Ver y gestionar estudiantes") -> Con un ícono de grupo de personas a la izquierda dentro de un contenedor cuadrado lila suave.
    2. "Mi Perfil Académico" (Subtítulo: "Datos personales y progreso") -> Con un ícono de usuario a la izquierda dentro de un contenedor lila.
- En la parte inferior, un botón 'TextButton' con ícono rojo para "Cerrar Sesión Segura" (regresa a LoginScreen).

C) ListScreen.kt ("Directorio de Alumnos"):
- Fondo en tono lila muy claro ('Color(0xFFF8F5FA)').
- TopAppBar con título "Directorio de Alumnos" y flecha de regreso ('popBackStack()').
- Lista en 'LazyColumn' con ítems estilo tarjeta redondeada ('RoundedCornerShape(16.dp)'):
    * Foto de perfil circular (Avatar) a la izquierda.
    * Primer elemento con el nombre "Johan Salazar Atencio" y los demás con nombres de prueba (ej: "María García", "Carlos Pérez").
    * Carrera técnica abajo (ej: "Ingeniería de Sistemas", "Diseño y Desarrollo de Software").
    * Flecha derecha (Chevron / ArrowForward) al extremo derecho.

D) DetailScreen.kt ("Expediente Académico"):
- TopAppBar con título "Expediente Académico" y flecha de regreso.
- Encabezado superior con un banner decorativo en degradado morado oscuro.
- Foto de perfil circular centrada que sobrepasa el borde del banner superior.
- Nombre "Johan Salazar Atencio" en grande y carrera abajo.
- Tarjeta blanca con información usando íconos a la izquierda:
    * Ícono de carnet -> "ID Estudiante: 2024-0001"
    * Ícono de sobre -> "Correo Electrónico: johan.salazar@tecsup.edu.pe"
    * Ícono de graduación -> "Facultad: Ingeniería y Tecnología"
    * Sección "Biografía": Texto descriptivo breve en un bloque inferior.

E) ProfileScreen.kt ("Configuración de Perfil"):
- TopAppBar con título "Configuración de Perfil" y flecha de regreso.
- Cabecera con degradado morado, foto de perfil circular centrada y nombre "Johan Salazar Atencio".
- Secciones organizadas en etiquetas en mayúsculas y color morado/gris:
    * Sección "INFORMACIÓN PERSONAL": Cajas redondeadas con Ícono + Etiqueta + Valor (Nombre Completo: Johan Salazar Atencio, Correo Institucional, Teléfono).
    * Sección "ACADÉMICO": Cajas estilizadas para Carrera e Indicar "VI Ciclo".
- Botón al final con texto rojo "Cerrar Sesión".

Por favor, genera y actualiza el código Kotlin de los archivos necesarios usando componentes estándar de Jetpack Compose y Material 3.

### Capturas del Rediseño Final:

#### 1. Pantalla de Inicio de Sesión (`LoginScreen`)


#### 2. Panel Principal (`HomeScreen`)


#### 3. Directorio de Alumnos (`ListScreen`)


#### 4. Expediente Académico (`DetailScreen`)


#### 5. Configuración de Perfil (`ProfileScreen`)
