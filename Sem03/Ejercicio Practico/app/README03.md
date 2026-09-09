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
<img width="886" height="471" alt="image" src="https://github.com/user-attachments/assets/7aa5f384-54ea-42da-85d4-6ee9164e4b73" />


### Commit 2: Agrega Cabecera "Registro de Notas"
<img width="886" height="498" alt="image" src="https://github.com/user-attachments/assets/ad14d034-108f-4948-ac36-fa48104abb82" />



### Commit 3: Definir estados reactivos de la UI
<img width="886" height="458" alt="image" src="https://github.com/user-attachments/assets/eecd52fb-a9ee-4b47-afc5-2013f2863ea1" />


### Commit 4: Crear componente ItemCursoSlider para las notas
<img width="886" height="471" alt="image" src="https://github.com/user-attachments/assets/07beb7f3-abc1-4c00-92ee-cc8ef47000f2" />


### Commit 5: Integra controles Switch y Checkbox
<img width="886" height="627" alt="image" src="https://github.com/user-attachments/assets/a180cbec-955f-4c97-bd42-b247c2ec6fa6" />


### Commit 6: Implementa un botón de cálculo condicionado por Checkbox
<img width="886" height="645" alt="image" src="https://github.com/user-attachments/assets/5cc1b56d-4fde-4920-a92b-d2323591f1a4" />


### Commit 7: Calcular promedio y muestra de resultados
<img width="886" height="693" alt="image" src="https://github.com/user-attachments/assets/f9671efb-89df-4815-ad50-d91c0bfc497a" />


### Commit 8: Agregado 4 estados de observación y pie de página
<img width="886" height="635" alt="image" src="https://github.com/user-attachments/assets/32687ff5-bbbd-4328-85f8-7309bcf14a33" />

### Commit 9: Implementa reto opcional de aporte por curso
<img width="886" height="671" alt="image" src="https://github.com/user-attachments/assets/4a99fd86-32ab-4c87-b884-c66ef45adba7" />


