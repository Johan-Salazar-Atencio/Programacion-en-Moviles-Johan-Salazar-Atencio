# Laboratorio 06: Tecsup Store - Menu-Navegacion
**Curso:** Programación en Móviles  
**Docente:** Juan José León Suiyon  
**Estudiante:** Johan Salazar Atencio

---

## Descripción del Proyecto
Este proyecto consiste en el desarrollo de **Tecsup Store**, una aplicación móvil Android desarrollada en **Jetpack Compose** enfocada en la gestión interactiva de carritos de compras, menús contextuales y navegación mediante menús laterales.

Se aborda la arquitectura de UI reactiva evaluando conceptos de **elevación de estado (State Hoisting)**, persistencia de datos en memoria durante la navegación, componentes avanzadas como `ModalNavigationDrawer`, `DropdownMenu`, `Badge` e integración asistida por Inteligencia Artificial (Gemini in-IDE) para la refactorización y adición de funcionalidades avanzadas.

---

## Componentes y Funcionalidades Implementadas

### Fase 1: Desarrollo Base
- **`TarjetaProducto.kt`:** Ficha visual de cada producto que incluye datos de nombre, precio, cantidad, botón para eliminar e icono de tres puntos que despliega un `DropdownMenu`.
- **`PantallaCarrito.kt`:** Lista dinámica de productos agregados con formulario de ingreso (Nombre, Precio, Cantidad) y tarjeta de resumen financiero (Subtotal, IGV 18% y Total).
- **`AppDrawer.kt`:** Menú lateral navegable (`ModalNavigationDrawer`) con cabecera de usuario personalizado e ítems de navegación (*Inicio*, *Mis pedidos*, *Favoritos*, *Perfil*, *Cerrar sesión*).
- **`AppNavegacion.kt`:** Gestor de rutas de la aplicación que administra la visibilidad del Drawer y el estado de la pantalla activa mediante `Scaffold` y `TopAppBar`.

### Fase 2: Mejora Asistida por IA (Rama `mejora-ia`)
- **Persistencia de Estado:** Refactorización de listas globales en `AppNavegacion.kt` para evitar pérdida de datos al cambiar de pestaña.
- **`PantallaFavoritos.kt`:** Vista dedicada para visualizar los productos marcados como favoritos desde el desplegable de cada tarjeta.
- **Badge Contador:** Indicador numérico visual en la opción *Favoritos* del menú lateral que se actualiza en tiempo real al agregar o eliminar elementos.
- **Refinamiento UI M3:** Mejora estética general con bordes redondeados, elevación de sombras y esquemas de color armónicos de Material Design 3.

---

## Historial de Commits y Evidencia Visual

### Fase 1: Desarrollo Base

#### Commit 1: Implementación de TarjetaProducto y estructura de carrito
Se configuro los 3 puntitos en TarjetaProducto

#### Commit 2: Integración de DropdownMenu en TarjetaProducto
Se añadió el menú contextual desplegable con opciones de Favoritos, Compartir y Reportar.


#### Commit 3: Implementación de AppDrawer
Se construyó el menú lateral `ModalNavigationDrawer` con perfil de usuario y opciones de navegación.


#### Commit 4: Conexión de navegación en AppNavegacion y MainActivity
Se integró el Scaffold principal unificando el Drawer, la barra superior y el renderizado condicional de vistas.


---

### Fase 2: Mejora con IA (Rama `mejora-ia`)

#### Commit 1 (IA): Callbacks de favoritos, persistencia de carrito y PantallaFavoritos
Se elevó el estado de las listas a `AppNavegacion.kt`, se agregaron callbacks en la tarjeta y se creó la vista `PantallaFavoritos.kt`.


#### Commit 2 (IA): Badge contador de favoritos en NavigationDrawer
Se integró la propiedad `badge` en `AppDrawer.kt` para mostrar la cantidad actual de productos en favoritos.


#### Commit 3 (IA): Eliminación de favoritos y refinamiento de interfaz Material 3
Se implementó la desmarcación de favoritos y la mejora visual general en tarjetas, botones e inputs.


---

## Preguntas de Reflexión

1. **¿Por qué el `DropdownMenu` se declara dentro de un `Box` junto al ícono que lo activa, y no en cualquier parte de la pantalla?**  
   *Respuesta:* En Jetpack Compose, el `DropdownMenu` requiere un contenedor `Box` como ancla de posicionamiento relativo. Al colocarlo junto al `IconButton` dentro del mismo `Box`, el motor de renderizado calcula las coordenadas exactas de la pantalla para desplegar la carta flotante pegada al ícono de tres puntos.

2. **¿Qué diferencia de alcance hay entre las opciones del `DropdownMenu` (afectan solo a un producto) y las del `NavigationDrawer` (afectan a toda la app)?**  
   *Respuesta:* El `DropdownMenu` opera a nivel **local/item**, ejecutando acciones específicas sobre la entidad individual (`Producto`) donde fue presionado. Por otro lado, el `NavigationDrawer` opera a nivel **global/aplicación**, cambiando la ruta actual de la interfaz y alterando la vista completa del `Scaffold`.

3. **¿Cómo tuviste que estructurar tu código para que el contador de favoritos del drawer "se entere" de lo que pasa en el DropdownMenu de cada producto?**  
   *Respuesta:* Se aplicó la técnica de **Elevación de Estado (State Hoisting)**. La lista de productos favoritos se alojó en la raíz de navegación (`AppNavegacion.kt`). Al presionar la opción en el `DropdownMenu`, la tarjeta dispara un callback hacia arriba (`onFavoritoToggle`). `AppNavegacion` actualiza la lista observable y reenvía el nuevo conteo (`cantidadFavoritos`) hacia abajo como parámetro al `AppDrawer`.

4. **¿Qué tuviste que corregir del código que te generó la IA para que la mejora del badge de favoritos funcionara correctamente?**  
   *Respuesta:* Inicialmente, el código de la IA almacenaba los estados dentro de Composables locales, lo que causaba que al cambiar entre pestañas del Drawer la lista se reiniciara a cero. Se corrigió moviendo el estado observable a `AppNavegacion` y ajustando la firma del callback en `TarjetaProducto` para enviar la instancia del producto seleccionado.

---

## Observaciones y Conclusiones

### Observaciones
1. Durante el cambio de pantallas con el `NavigationDrawer`, Compose destruye los composables que no están visibles. Si el estado no está elevado a la raíz, los inputs y listas de productos se reinician por completo.
2. Al interactuar con el asistente de IA en Android Studio, los prompts con instrucciones imperativas y estructuradas por pasos generan código Jetpack Compose mucho más limpio y preciso que las preguntas generales.

### Conclusiones
1. La **Fase 1** permitió comprender la sintaxis declarativa y el ciclo de vida de los composables, mientras que la **Fase 2** demostró el potencial de las herramientas de IA para acelerar la refactorización y el diseño visual.
2. La elevación de estados es un patrón imprescindible en aplicaciones Jetpack Compose complejas; garantiza que la interfaz se mantenga sincronizada entre componentes distantes como un menú contextual y un cajón de navegación.