# Laboratorio 04: Mi Carrito TECSUP con LazyColumn
**Curso:** Programación en Móviles  
**Docente:** Juan José León Suiyon  
**Estudiante:** Johan Salazar Atencio

---

## Descripción del Proyecto
Este proyecto implementa una aplicación móvil Android de carrito de compras desarrollada con **Jetpack Compose**. La aplicación integra el modelado de datos en Kotlin, captura de datos mediante formularios interactivos y presentación de datos mediante listas dinámicas optimizadas con `LazyColumn`.

### Componentes y Funcionalidades Implementadas
- **Modelo `Producto`:** Data class que modela cada ítem con sus atributos `nombre`, `precio` y `cantidad`.
- **Formulario compacto de registro:** Captura de datos con validación para agregar productos dinámicamente al carrito.
- **`LazyColumn` optimizada:** Renderizado eficiente de la lista desplazable de productos.
- **`TarjetaProducto`:** Componente modular con diseño limpio (fondo blanco, borde definido), detalle del cálculo (`S/ precio x cantidad`), importe total por producto y botón de eliminación.
- **Manejo de estado observable:** Uso de `mutableStateListOf` para sincronizar en tiempo real las operaciones de adición y eliminación con la interfaz de usuario.
- **Estado vacío centrado:** Muestra un mensaje amigable con `Box` cuando el carrito no contiene elementos.
- **Panel de totales fijo:** Cálculo automático de Subtotal, IGV (18%) y Total general con formato a dos decimales siempre visible en la parte inferior.

---

## Pregunta sy respuestas

### 1. ¿Por qué se utiliza `mutableStateListOf` en lugar de una `MutableList` normal?
`mutableStateListOf` crea una lista observable por Jetpack Compose. Cuando se agregan o eliminan elementos, Compose detecta la modificación en el estado y desencadena automáticamente una recomposición de la UI para reflejar los cambios en pantalla. Una `MutableList` convencional modificaría los datos en memoria, pero no notificaría a Compose, haciendo que la interfaz se quede congelada sin mostrar las actualizaciones.

### 2. ¿Por qué la lista se declara con `val` y aún así se pueden agregar o eliminar elementos?
La palabra clave `val` en Kotlin indica que la **referencia** al objeto en memoria es inmutable (no se puede reasignar la variable a una nueva lista). Sin embargo, el objeto que vive en esa referencia (`SnapshotStateList`) es un contenedor **mutable** internamente, lo que permite modificar su contenido (agregar, quitar o limpiar ítems) sin cambiar la referencia de la variable.

### 3. ¿Qué hace `Modifier.weight(1f)` en la `LazyColumn`?
El modificador `weight(1f)` dentro de una `Column` le indica al composable que debe ocupar todo el espacio vertical sobrante o disponible. Al colocarle `weight(1f)` a la `LazyColumn`, esta se expande dinámicamente entre el formulario y la parte inferior, logrando empujar y fijar el panel de totales siempre al final de la pantalla.

---

## Capturas de Ejecución
