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

<img width="886" height="457" alt="image" src="https://github.com/user-attachments/assets/4b6ebccf-bba6-4f30-a469-1cbed54d5f08" />


---

### Segundo Commit: Definición de Rutas (`Screen.kt`)
Creación de la clase sellada `Screen` para definir las rutas `home`, `list`, `profile` y la ruta con argumento `detail/{itemId}`.

<img width="886" height="753" alt="image" src="https://github.com/user-attachments/assets/43ce6059-aa32-4234-bdfa-abba77a11d77" />


---

### Tercer Commit: Estructura de Navegación (`AppNavigation.kt`)
Configuración del contenedor `NavHost` definiendo el punto de inicio y las composables para cada pantalla.
<img width="886" height="472" alt="image" src="https://github.com/user-attachments/assets/ac0ea634-c46b-4f33-a985-379bb6313818" />



---

### Cuarto Commit: Vinculación en `MainActivity.kt`
Integración del composable `AppNavigation()` dentro del `setContent` del punto de entrada principal.

<img width="875" height="941" alt="image" src="https://github.com/user-attachments/assets/68094769-4c0e-47d4-9eae-9be566787f72" />


---

### Quinto Commit: Implementación de `HomeScreen.kt`
Diseño de la pantalla de inicio con botones de navegación hacia la lista y el perfil.

<img width="886" height="652" alt="image" src="https://github.com/user-attachments/assets/6bc41fd9-4a52-4188-90d5-0cee5616dfea" />


---

### Sexto Commit: Implementación de `ListScreen.kt`
Creación de la pantalla de directorio con `LazyColumn` y botones para navegar al detalle pasando el ID del elemento.

<img width="886" height="507" alt="image" src="https://github.com/user-attachments/assets/51ab9621-9784-4529-9bb5-24944882dc54" />


---

### Séptimo Commit: Implementación de `DetailScreen.kt`
Recepción y renderizado del parámetro `itemId` recibido desde la ruta de navegación.
<img width="886" height="472" alt="image" src="https://github.com/user-attachments/assets/76c3aace-b004-4698-a196-7baffd847873" />



---

### Octavo Commit: Implementación de `ProfileScreen.kt`
Creación de la pantalla de perfil utilizando `popUpTo` para limpiar la pila de navegación al volver al inicio.

<img width="886" height="458" alt="image" src="https://github.com/user-attachments/assets/e2f9b7bf-d164-4fa5-b317-5b9cf2f12c81" />


---

## Resultados de la App Base

Capturas de la aplicación funcionando con la estructura de navegación inicial:


<img width="519" height="1165" alt="image" src="https://github.com/user-attachments/assets/c50bf4d9-d36a-4f19-b2d5-3107ca5ea7ea" />

<img width="509" height="1183" alt="image" src="https://github.com/user-attachments/assets/a19eca0d-4154-4a68-b99e-5a3286ce0ea0" />

<img width="488" height="1026" alt="image" src="https://github.com/user-attachments/assets/f60619ee-62e5-42a8-895f-aa86894a7101" />

<img width="456" height="998" alt="image" src="https://github.com/user-attachments/assets/542fd0ab-7bf6-44e3-864a-abf02a9e4a5f" />





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
<img width="414" height="879" alt="image" src="https://github.com/user-attachments/assets/d58995bc-fd7e-459b-8ee0-f0572e2adb5c" />


#### 2. Panel Principal (`HomeScreen`)
<img width="425" height="883" alt="image" src="https://github.com/user-attachments/assets/0c790d03-a3be-4fa0-9206-527d421116cb" />


#### 3. Directorio de Alumnos (`ListScreen`)
<img width="409" height="885" alt="image" src="https://github.com/user-attachments/assets/f9b3a0c2-4dab-4dda-ac32-8bec8f5ffafe" />


#### 4. Expediente Académico (`DetailScreen`)
<img width="403" height="888" alt="image" src="https://github.com/user-attachments/assets/ef726d41-91a0-4916-886f-168103dc69c1" />


#### 5. Configuración de Perfil (`ProfileScreen`)
<img width="421" height="884" alt="image" src="https://github.com/user-attachments/assets/1f37ff87-8a0c-436d-892c-47f71d3874e0" />

