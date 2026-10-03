# Proyecto: Mi Bodega - App de Comercio para Clientes

**Curso:** Programación en Móviles  
**Docente:** Juan José León Suiyon  
**Estudiante:** Johan Salazar Atencio  
**Repositorio GitHub:** [https://github.com/Johan-Salazar-Atencio/Programacion-en-Moviles-Johan-Salazar-Atencio](https://github.com/Johan-Salazar-Atencio/Programacion-en-Moviles-Johan-Salazar-Atencio)

---

## Descripción del Proyecto

El proyecto **Mi Bodega** es una aplicación móvil Android desarrollada en **Jetpack Compose** enfocada en simular el flujo completo de compra de una bodega local. La aplicación abarca desde la bienvenida y registro de datos del cliente, exploración del catálogo con filtrado por categorías, detalle del producto, gestión interactiva del carrito de compras, hasta la captura de dirección/método de pago y la confirmación final del pedido.

El desarrollo se organizó en dos etapas complementarias:
* **Fase 1 (Desarrollo Base - Manual):** Implementación de la estructura principal a partir de un esqueleto de código, construyendo la navegación centralizada con `NavHost`, la gestión reactiva de estados con *State Hoisting* y las pantallas del flujo del cliente.
* **Fase 2 (Optimización Asistida por IA):** Mejora de la experiencia de usuario (UI/UX) mediante Gemini Agent en Android Studio, añadiendo un buscador en tiempo real, iconografía vectorial para productos, animaciones de interfaz y un módulo interactivo de Favoritos.

---

## Componentes y Funcionalidades Implementadas

* **Registro / Login:** Pantalla inicial de acceso con navegación directa al formulario de creación de cuenta y captura de datos personales (Nombre, Teléfono, Dirección, Referencia).
* **Catálogo e Inicio (`InicioScreen`):** Muestra tarjetas de productos (`ProductoCard`) organizadas en cuadrícula, barra de búsqueda en tiempo real, filtro rápido por categorías ("Todos", "Bebidas", "Abarrotes", "Snacks") y filtro por "Favoritos".
* **Detalle de Producto (`DetalleProductoScreen`):** Vista expandida con selector numérico de cantidad (`-` / `+`), precio unitario, descripción detallada y acción para agregar al carrito.
* **Carrito de Compras (`CarritoScreen`):** Lista reactiva de productos seleccionados con cálculo automático de Subtotal, Costo de Delivery (S/ 4.00) y Total general en soles.
* **Datos de Entrega (`CheckoutScreen`):** Formulario con selección de métodos de pago (Efectivo, Yape/Plin, Tarjeta) y validación del total a pagar.
* **Confirmación de Pedido (`ConfirmacionScreen`):** Pantalla de éxito con indicador visual verde, tiempo estimado de entrega y limpieza automática de la pila de navegación (*backstack*).

---

### Fase 1: Flujo Base del Cliente (Desarrollo Manual)

### 1. Bienvenida / Login
<img width="495" height="1069" alt="image" src="https://github.com/user-attachments/assets/a5077e72-8ac5-40fd-948e-14965f02ea28" />


### 2. Registro de Datos
<img width="501" height="1076" alt="image" src="https://github.com/user-attachments/assets/03d0fd8d-0a4b-4c92-b95b-d47e82db0867" />

### 3. Inicio / Productos
<img width="504" height="1070" alt="image" src="https://github.com/user-attachments/assets/fa02f0b5-c4df-44d5-a086-ffdd51c0587e" />

### 4. Detalle de Producto
<img width="496" height="1065" alt="image" src="https://github.com/user-attachments/assets/3a6c75cc-32bd-4b3f-9ed3-4e13341953bb" />


### 5. Carrito de Compras
<img width="510" height="1065" alt="image" src="https://github.com/user-attachments/assets/14fe9c97-f5d2-483a-aca3-f3a8c925ac7d" />

### 6. Datos de Entrega
<img width="506" height="1066" alt="image" src="https://github.com/user-attachments/assets/baec9fa7-1770-4006-92b8-245f99b6824f" />

### 7. Pedido Confirmado
<img width="494" height="1063" alt="image" src="https://github.com/user-attachments/assets/64403e1c-2117-490c-ae08-18abb9967561" />

---

### Fase 2: Mejoras Visuales e Interactivas (Asistido por IA)

### 8. Rediseño con Iconografía
<img width="533" height="1039" alt="image" src="https://github.com/user-attachments/assets/a4626816-fe7a-4e4d-b869-99609f01c514" />

### 9. Buscador en Tiempo Real
<img width="506" height="1020" alt="image" src="https://github.com/user-attachments/assets/6281d6a7-e906-44d6-ae57-6548faa6c5bf" />

### 10. Módulo de Favoritos
<img width="570" height="1084" alt="image" src="https://github.com/user-attachments/assets/bacde3b3-0242-4484-b217-9186f9f6979b" />


---

## VI. Preguntas de Reflexión

### • ¿Por qué `Producto.kt` y `MainActivity.kt` se entregaron completos, y las pantallas no? ¿Qué tienen en común los archivos que sí se dejaron como esqueleto?
* `Producto.kt` define el modelo de datos (*Data Class*) y `MainActivity.kt` configura el punto de entrada de la aplicación y la jerarquía de temas. Ambos representan el núcleo arquitectónico fijo sobre el cual se sostiene el proyecto.
* Los archivos dejados como esqueleto representan la capa de presentación (las pantallas `@Composable`). Todos ellos compartían en común ser vistas interactivas que requerían implementar la gestión de estado (*State Hoisting*), recomposición y la lógica de navegación para completar el flujo funcional del usuario.

### • ¿Cómo lograste que el filtro de categoría (`LazyRow`) y el cálculo del carrito reaccionen automáticamente sin que tú "actualices" nada a mano?
* Se logró gracias al mecanismo de **Recomposición Automática** de Jetpack Compose. Utilizando variables de estado observables mediante `remember { mutableStateOf(...) }` y `remember { mutableStateListOf(...) }`, Compose rastrea automáticamente cualquier cambio en la categoría seleccionada o en la cantidad de productos del carrito. Al mutar el estado, Jetpack Compose vuelve a ejecutar (*recompone*) únicamente los bloques visuales afectados sin necesidad de manipular manualmente la vista.

### • ¿Qué diferencia notaste entre `navigate()` normal (Inicio → Detalle) y el que usa `popUpTo` (Datos de entrega → Confirmación)?
* `navigate("detalle")` es una navegación estándar que agrega la nueva pantalla sobre la pila de navegación (*backstack*), permitiendo que el usuario retroceda a la pantalla anterior con el botón físico o de la interfaz.
* El uso de `popUpTo("inicio") { inclusive = false }` al navegar hacia la pantalla de confirmación remueve las pantallas intermedias del carrito y formulario de entrega de la pila. De esta forma, si el usuario presiona "Atrás" o "Volver al inicio", no puede regresar al formulario de pago de un pedido que ya fue procesado.

### • ¿Qué tuviste que corregir del código que te generó la IA para el buscador en tiempo real?
* La IA generó un filtro que funcionaba de manera aislada para el texto, pero no mantenía la sincronización con la categoría seleccionada. Se tuvo que corregir la condición dentro del filtro derivado para asegurar que la búsqueda por texto (`contains(query, ignoreCase = true)`) se combinara mediante una condición lógica `AND` con el chip de categoría activo, además de manejar adecuadamente los casos en los que la barra de búsqueda quedaba vacía.

### • Compara el `NavigationDrawer` del Laboratorio 6 con el `NavigationBar` de esta tarea: ¿en qué caso usarías cada uno en un proyecto propio?
* **`NavigationBar` (Barra Inferior):** Es ideal para aplicaciones de consumo directo con 3 a 5 secciones principales de acceso rápido y constante (como Inicio, Categorías, Pedidos y Perfil), favoreciendo la usabilidad con una sola mano en pantallas móviles.
* **`NavigationDrawer` (Menú Lateral):** Es adecuado para aplicaciones complejas o administrativas con más de 5 secciones, donde se requiere estructurar menús secundarios, configuraciones de cuenta, términos de uso o soporte técnico sin ocupar espacio permanente en la pantalla principal.

---

## VII. Observaciones y Conclusiones

### Observaciones
1. **Centralización del Estado (*Single Source of Truth*):** Durante el desarrollo de la Fase 1, se constató que la elevación de estados (*State Hoisting*) hacia el nivel superior de `ClienteApp` es indispensable para garantizar que el total del carrito se mantenga sincronizado entre la tarjeta de producto, el resumen del carrito y la pantalla de pago.
2. **Importaciones de Iconografía de Material:** En la Fase 2, al aplicar las sugerencias de la IA, fue necesario verificar manualmente las librerías importadas de `Icons.Default` / `Icons.Outlined` en Compose para evitar errores de compilación por iconos no incluidos en el paquete básico.

### Conclusiones
1. **Eficiencia del Desarrollo basado en Esqueletos:** Trabajar con una estructura preexistente agiliza la comprensión de patrones de arquitectura en Android (como Jetpack Compose), permitiendo enfocar el esfuerzo práctico directamente en el manejo de estados y flujos de navegación.
2. **Sinergia entre Desarrollo Manual y Asistencia por IA:** La combinación entre el desarrollo guiado de la Fase 1 y las consultas asistidas en la Fase 2 demostró que las herramientas de IA como Gemini aceleran significativamente la maquetación y el diseño UI/UX, aunque la intervención del desarrollador sigue siendo esencial para corregir la lógica de estado y garantizar la estabilidad del software.
