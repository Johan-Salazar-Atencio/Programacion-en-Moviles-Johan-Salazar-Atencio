# CafeCampus — App de Pedidos para la Cafetería del Campus

**Autor:** Johan Salazar Atencio  
**Curso:** Programación en Móviles — TECSUP  
**Tecnologías:** Kotlin, Jetpack Compose, Room DB, ViewModel, StateFlow, DataStore  
**Link Repositorio:** [Programacion-en-Moviles-Johan-Salazar-Atencio](https://github.com/Johan-Salazar-Atencio/Programacion-en-Moviles-Johan-Salazar-Atencio)

---

## 1. Descripción del Proyecto

CafeCampus es una aplicación móvil nativa desarrollada para optimizar el proceso de pedido y recojo de productos en la cafetería del campus TECSUP. Permite a los estudiantes explorar la carta, personalizar notas de preparación, realizar pedidos y dar seguimiento a su estado en tiempo real. Por otro lado, ofrece al personal administrativo un panel de control para gestionar ventas, cambiar estados de atención, controlar la disponibilidad de productos y configurar parámetros globales como el estado de la cafetería y el modo oscuro.

---

## 2. Estructura del Proyecto (Árbol de Paquetes)



---

## 3. Diagrama de Arquitectura MVVM



---

## 4. Capturas de Pantalla (Pruebas de Aceptación)

### Perfil Estudiante (Cliente)

#### Prueba 1: Menú de Productos y Buscador


#### Prueba 2: Detalle y Confirmación de Pedido

#### Prueba 3: Seguimiento del Pedido y Perfil

---

### Perfil Administrador

#### Prueba 4: Panel de Control y Gráfico


#### Prueba 5: Gestión del Menú y Formulario


#### Prueba 6: Ajustes y Modo Oscuro Global


---

### Funcionalidad Exclusiva: Modo Sorpréndeme (Creador de Combos por Presupuesto)

---

## 5. Respuestas a la Sección "Para Pensar"

* **Pregunta 1:** ¿Por qué guardamos las líneas del pedido en una tabla separada (`DetallePedido`) en lugar de guardar un String con los nombres en `Pedido`?  
  **Respuesta:** Se guardan en una tabla separada por principios de **normalización de bases de datos (1FN/2FN)**. Guardar un String no estructurado impediría realizar consultas analíticas (como identificar el producto más vendido o métricas de ventas por hora), rompería la integridad referencial de los datos y complicaría el cálculo de cantidades, precios unitarios y subtotales.

* **Pregunta 2:** ¿Qué pasaría si el estudiante cambia el precio de un producto en la base de datos mientras un pedido está en curso? ¿Cómo evita esto nuestro diseño?  
  **Respuesta:** Nuestro diseño lo evita guardando el `precioUnitario` histórico directamente dentro de la tabla `DetallePedido` al momento de confirmar la compra. De esta forma, si el Administrador modifica el precio en la tabla `Producto`, los pedidos ya realizados o en curso conservan su total inalterado.

* **Pregunta 3:** Si quisieras agregar notificaciones push cuando el pedido cambia a LISTO, ¿en qué capa pondrías la lógica y por qué?  
  **Respuesta:** La lógica de activación se ubicaría en la **Capa de Datos (Repository)** con un servicio en segundo plano (como `WorkManager` o `FirebaseMessagingService`), mientras que la observación del estado corresponde al **ViewModel**. El Repository escucha los cambios en el `Flow<Pedido>` de Room y, al detectar el estado `LISTO`, dispara la notificación nativa del sistema.

---

## 6. Prompts para Mejoras de la Fase 2 (OpenCode / IA)

### Prompt 1: Buscar en el Menú
@workspace Implementa la funcionalidad 'Buscar en el menú' en 'ui/cliente/MenuScreen.kt' y 'viewmodel/MenuViewModel.kt'. Agrega un campo de texto interactivo 'OutlinedTextField' en la parte superior que filtre la lista de productos en tiempo real por su nombre a medida que el usuario escribe, manteniendo el filtrado por categorías.
### Prompt 2: Reporte del Día para Administrador
@workspace Agrega la funcionalidad 'Reporte del día' en la capa de datos y UI de Admin. Crea en 'PedidoDao' consultas SQL que obtengan: 1) El producto más vendido del día y 2) El total recaudado agrupado por forma de pago (Efectivo, Yape, Plin). Expón estos datos en 'PanelAdminViewModel' y muestra una tarjeta resumida de 'Reporte Diario' dentro de 'PanelScreen.kt'.
### Prompt 3: Gráfico Real de Pedidos por Hora
@workspace Refactoriza el gráfico de barras de 'ui/admin/PanelScreen.kt' para que no use valores estáticos. Crea una consulta SQL en 'PedidoDao' que agrupe los pedidos no cancelados por rango de hora de creación (usando strftime en SQLite). Conecta estos datos a 'PanelAdminViewModel' para que las barras del gráfico representen el conteo real de pedidos registrados en cada intervalo de tiempo del día.
