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

<img width="428" height="664" alt="image" src="https://github.com/user-attachments/assets/f80e8d1a-3142-40eb-8a8f-8a33271f1aa0" />


---

## 3. Diagrama de Arquitectura MVVM

<img width="577" height="486" alt="image" src="https://github.com/user-attachments/assets/437dc735-e63b-4645-9808-118584236ab1" />


---

## 4. Capturas de Pantalla (Pruebas de Aceptación)

### Perfil Estudiante (Cliente)

#### Prueba 1: Menú de Productos y Buscador
<img width="355" height="736" alt="image" src="https://github.com/user-attachments/assets/2c378fc6-9597-487a-bd80-63c0838f077a" />


#### Prueba 2: Detalle y Confirmación de Pedido
<img width="363" height="702" alt="image" src="https://github.com/user-attachments/assets/6c333cb2-45b2-4539-b109-3d444de65359" />

<img width="326" height="693" alt="image" src="https://github.com/user-attachments/assets/f0180516-836b-404a-a4a9-23780f228fe4" />


#### Prueba 3: Seguimiento del Pedido y Perfil
<img width="283" height="617" alt="image" src="https://github.com/user-attachments/assets/fdee2ace-eaea-408b-80c4-a18cca0d3783" />

<img width="292" height="620" alt="image" src="https://github.com/user-attachments/assets/3dacee06-1f56-496e-bca3-9825f1016e5b" />

---

### Perfil Administrador

#### Prueba 4: Panel de Control y Gráfico

<img width="296" height="636" alt="image" src="https://github.com/user-attachments/assets/988b2927-ba85-424b-af07-3230e185468f" />



#### Prueba 5: Gestión del Menú y Formulario

<img width="296" height="614" alt="image" src="https://github.com/user-attachments/assets/b7f679c7-60fa-48d2-bf19-770142ad94ce" />

<img width="310" height="613" alt="image" src="https://github.com/user-attachments/assets/866e51fd-da2d-40fc-9122-9c8e03d37428" />



#### Prueba 6: Ajustes y Modo Oscuro Global

<img width="310" height="648" alt="image" src="https://github.com/user-attachments/assets/3a197ef3-2ee3-45ff-9146-2b32677576d6" />


---

### Funcionalidad Exclusiva: Modo Sorpréndeme (Creador de Combos por Presupuesto)

<img width="280" height="576" alt="image" src="https://github.com/user-attachments/assets/7f099755-4e53-4614-b2f6-50aab66c8de2" />

<img width="283" height="576" alt="image" src="https://github.com/user-attachments/assets/60d53ba3-2c48-4b41-908d-fce7cb3d1c1d" />



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
<img width="364" height="740" alt="image" src="https://github.com/user-attachments/assets/be1b5386-a5cb-4121-8496-b3c4bd57a07a" />

### Prompt 2: Reporte del Día para Administrador
@workspace Agrega la funcionalidad 'Reporte del día' en la capa de datos y UI de Admin. Crea en 'PedidoDao' consultas SQL que obtengan: 1) El producto más vendido del día y 2) El total recaudado agrupado por forma de pago (Efectivo, Yape, Plin). Expón estos datos en 'PanelAdminViewModel' y muestra una tarjeta resumida de 'Reporte Diario' dentro de 'PanelScreen.kt'.
<img width="378" height="803" alt="image" src="https://github.com/user-attachments/assets/8cb5e3d3-6266-4026-babb-7ff44883eaf4" />


### Prompt 3: Gráfico Real de Pedidos por Hora
@workspace Refactoriza el gráfico de barras de 'ui/admin/PanelScreen.kt' para que no use valores estáticos. Crea una consulta SQL en 'PedidoDao' que agrupe los pedidos no cancelados por rango de hora de creación (usando strftime en SQLite). Conecta estos datos a 'PanelAdminViewModel' para que las barras del gráfico representen el conteo real de pedidos registrados en cada intervalo de tiempo del día.
<img width="528" height="996" alt="image" src="https://github.com/user-attachments/assets/b8dea08d-c2b1-4df1-9650-2f453a32cd53" />

