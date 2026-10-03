# Historial de Prompts - Fase 2 (Versión Asistida por IA)

**Curso:** Programación en Móviles  
**Estudiante:** Johan Salazar Atencio  
**Rama Git:** `feature/version-ia`

El presente documento registra las instrucciones exactas (*prompts*) utilizadas en el panel de Gemini Agent en Android Studio para llevar a cabo la Fase 2 del proyecto.

---

## Prompt 01: Buscador en Tiempo Real y Filtro de Categorías
Modifica `InicioScreen.kt` para implementar la búsqueda de productos en tiempo real y el filtrado por categorías según los elementos de la interfaz:

1. En el campo de texto de búsqueda (`OutlinedTextField`), filtra la lista de productos dinámicamente a medida que el usuario escribe.
2. Combina la búsqueda por texto con la categoría seleccionada en los chips de filtro ("Todos", "Bebidas", "Abarrotes", "Snacks") mediante una condición lógica AND.
3. Configura la tarjeta de producto (`ProductoCard`) para que muestre la imagen/icono del producto, el nombre, la presentación (ej. "1 kg", "1 L"), el precio en soles (S/) y el botón verde con el icono "+" para agregar al carrito.


## Prompt 02: Rediseño UI/UX, Iconografía Vectorial y Animaciones
Mejora el estilo visual y diseño UI/UX de TODAS las pantallas de la aplicación (InicioScreen, DetalleProductoScreen, CarritoScreen, CheckoutScreen, ConfirmacionScree):

1. Rediseña los componentes visuales de todas las pantallas utilizando una paleta de colores coherente (verde primario, fondos limpios, tarjetas con sombras suaves y bordes redondeados modernos).
2. Asigna e integra iconos vectoriales de Material (`Icons.Default` o `Icons.Outlined`) en los botones, campos de texto, encabezados y tarjetas de todas las pantallas.
3. En `ProductoCard.kt` y `DatosFake.kt`, asigna iconos vectoriales de Material (`Icons.Default` o `Icons.Outlined`) específicos a cada producto o categoría (por ejemplo: icono de botella/bebida para gaseosas, bolsa/compras para abarrotes, galleta para snacks).
4. Añade animaciones fluidas (`AnimatedVisibility` y transiciones de estado) al filtrar productos, cambiar de pantalla o interactuar con los botones para darle un acabado pulido a toda la app.


## Prompt 03: Sistema de Favoritos y Estado Reactivo

Implementa un sistema de Favoritos dentro de la aplicación:
1. Haz funcional el botón de corazón en las tarjetas de producto (`ProductoCard`) y en el detalle (`DetalleProductoScreen`) para marcar y desmarcar productos como favoritos.
2. Agrega una pestaña o chip de filtro rápido en `InicioScreen` para mostrar únicamente los productos marcados como favoritos.
3. Mantén el estado de favoritos actualizado de manera reactiva en toda la app.