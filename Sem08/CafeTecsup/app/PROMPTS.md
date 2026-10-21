# Prompts Utilizados — Fase 2 (CafeCampus)

**Alumno:** Johan Salazar Atencio  
**Rama Git:** `feature/version-ia`  
**Herramienta IA:** OpenCode

---

## Prompt 1: Buscar en el Menú

**Texto enviado a la IA:**
> @workspace Implementa la funcionalidad de "Buscar en el menú" en la vista del estudiante para la Fase 2 del laboratorio.  
> **Restricciones:** No gi commit/push, mantener colores dinámicos (sin hardcodeo de Color.White), usar emojis o íconos nativos.  
> **Lógica:** Agregar StateFlow de query en MenuViewModel, filtrar productos por nombre y categoría de forma reactiva y conectar un OutlinedTextField en MenuScreen.kt.

**Qué generó la IA:**
- Agregó el `StateFlow<String>` de búsqueda y la función `onBusquedaChanged` en `MenuViewModel.kt`.
- Implementó la combinación reactiva de la query con el filtro de categoría seleccionada.
- Agregó la barra de búsqueda `OutlinedTextField` con ícono de lupa y botón para limpiar en `MenuScreen.kt`.

**Qué tuve que corregir yo:**
- Nada crítico, solo ajustar el padding del campo de texto en Compose para que encajara correctamente con la cabecera.

**¿Respeta MVVM?:** Sí, la lógica de filtrado se mantiene 100% en el `MenuViewModel` mediante combinadores de `StateFlow`. El Composable solo se encarga de renderizar la barra y emitir los eventos de entrada.

---

## Prompt 2: Reporte del Día para Administrador

**Texto enviado a la IA:**
> @workspace Implementa la funcionalidad de "Reporte del día para Administrador" en la vista de Admin.  
> **Lógica:** Crear consultas SQL en PedidoDao para obtener el producto más vendido del día y el total recaudado por forma de pago (Efectivo, Yape, Plin). Exponer estos datos en PanelAdminViewModel y mostrar la tarjeta "Reporte Diario" en PanelScreen.kt.

**Qué generó la IA:**
- Consultas SQL con `@Query` en `PedidoDao.kt` agrupando por estado y método de pago.
- Funciones correspondientes en `CafeRepository` y `CafeRepositoryRoom`.
- Estado de reporte diario expuesto en `PanelAdminViewModel.kt`.
- Componente visual de tarjeta " Reporte Diario" en `PanelScreen.kt`.

**Qué tuve que corregir yo:**
- La IA descuadro algo, que impedia la interaccion con la pantalla inicial de admnistrador, a lo que tuve que arreglarlo

**¿Respeta MVVM?:** Sí, la vista observa el `StateFlow` expuesto por el ViewModel sin consultar directamente el DAO ni el Repositorio.

---

## Prompt 3: Gráfico Real de Pedidos por Hora

**Texto enviado a la IA:**
> @workspace Implementa la funcionalidad de "Gráfico real de pedidos por hora" en la vista del Administrador.  
> **Lógica:** Crear consulta SQL en PedidoDao para agrupar pedidos no cancelados por rango de hora del día. Conectar con PanelAdminViewModel y refactorizar el gráfico de barras en PanelScreen.kt para eliminar los datos estáticos.

**Qué generó la IA:**
- Consulta SQL con agregación temporal en `PedidoDao.kt`.
- Flujo reactivo en `CafeRepositoryRoom` y transformación en `PanelAdminViewModel.kt`.
- Refactorización del gráfico de barras en `PanelScreen.kt` alimentado con datos reales.

**Qué tuve que corregir yo:**
- Ajusté los colores del gráfico para asegurar que respetara los esquemas dinámicos del `MaterialTheme` en modo oscuro.

**¿Respeta MVVM?:** Sí, se cumple estrictamente el flujo `DAO -> Repository -> ViewModel -> Composable`.

---

## Reflexión (Fase 2)

- **¿Qué mejoró la IA y qué no?:** La IA aceleró drásticamente la escritura de consultas SQL analíticas en Room y la estructura de componentes Compose en la UI. Sin embargo, requiere supervisión en el manejo de límites de respuesta y en el cumplimiento de estilos de modo oscuro.
- **¿Qué error cometió?:** Se detuvo a mitad del archivo `PanelScreen.kt` al exceder el límite de tokens del modelo y sugirió inicialmente valores de color fijos.
- **¿Qué aprendí sobre MVVM y Room?:** Aprendí a utilizar consultas de agregación SQL avanzadas en Room expuestas mediante `Flow`, demostrando cómo la arquitectura MVVM permite integrar métricas dinámicas en la vista sin acoplar la UI a la base de datos.