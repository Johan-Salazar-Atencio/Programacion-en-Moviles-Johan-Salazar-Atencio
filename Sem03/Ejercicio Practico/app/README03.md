# Laboratorio 03: Registro de Notas en Jetpack Compose
**Curso:** Programación en Móviles  
**Docente:** Juan José León Suiyon  
**Estudiante:** Johan Salazar Atencio

---

## Descripción del Proyecto
Este proyecto implementa una aplicación móvil Android en **Jetpack Compose** para el cálculo del promedio ponderado de cuatro cursos académicos con sus respectivos pesos. Incorpora controles interactivos de selección y estado (`Slider`, `Switch`, `Checkbox`), redondeo de promedios, visualización de estado dinámico (`when`) y un desglose con el aporte individual por curso.

### Componentes y Funcionalidades Implementadas
- **Contenedor Principal y Header:** Implementación de un diseño moderno con fondo en degradado (`Brush.verticalGradient`) y cabecera con elevación visual.
- **`ItemCursoSlider`:** Componente personalizable que utiliza `Slider` para la selección interactiva de notas (rango 0 a 20) con badge de visualización.
- **Control de Redondeo (`Switch`):** Permite habilitar o deshabilitar el redondeo entero del promedio ponderado.
- **Confirmación de Notas (`Checkbox`):** Habilita/deshabilita el botón de cálculo garantizando la validación de los datos.
- **Evaluación por `when`:** Determinación dinámica del estado académico (**EXCELENTE**, **APROBADO**, **EN RECUPERACIÓN**, **DESAPROBADO**) con badges y colores de estado.
- **Reto Opcional (Aporte por curso):** Visualización detallada dentro de la tarjeta con el cálculo en tiempo real de `Nota × Peso = Aporte` por cada materia.
- **Pie de Página:** Identificación fija del desarrollador en la parte inferior de la pantalla.

---

## Capturas de Ejecución por Commit

### Commit 1: Configurar estructura base y fondo degradado

### Commit 2: Agrega Cabecera "Registro de Notas"

### Commit 3: Definir estados reactivos de la UI

### Commit 4: Crear componente ItemCursoSlider para las notas

### Commit 5: Integra controles Switch y Checkbox

### Commit 6: Implementa un botón de cálculo condicionado por Checkbox

### Commit 7: Calcular promedio y muestra de resultados

### Commit 8: Agregado 4 estados de observación y pie de página

### Commit 9: Implementa reto opcional de aporte por curso
