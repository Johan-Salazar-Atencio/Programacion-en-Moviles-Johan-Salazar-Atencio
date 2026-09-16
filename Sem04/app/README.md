# Laboratorio 04: Manejo de Estados en Jetpack Compose
**Curso:** Programación en Móviles  
**Docente:** Juan José León Suiyon  
**Estudiante:** Johan Salazar Atencio

---

## Descripción del Proyecto
Este proyecto implementa una aplicación móvil Android en **Jetpack Compose** enfocada en el manejo y gestión de estados de interfaz de forma progresiva[cite: 1]. Se exploran desde los conceptos fundamentales de reactividad (`remember`, `mutableStateOf`, `mutableStateListOf`)[cite: 1] hasta la refactorización mediante el patrón **State Holder** basado en Programación Orientada a Objetos (POO) para desacoplar la lógica de estado de la vista.

### Componentes y Funcionalidades Implementadas
- **`TemperatureDisplay`:** Componente interactivo que demuestra la persistencia de estado entre recomposiciones, actualización de colores dinámica según la temperatura (>30°C rojo, <10°C azul) y control mediante botones de incremento, decremento y reinicio[cite: 1].
- **Modelo `Tarea` e `ItemTarea`:** Ficha modular reusable para representar cada tarea individual con selección (`Checkbox`) y opción de eliminación[cite: 1].
- **`PantallaTareas`:** Vista principal que gestiona una lista observable en tiempo real (`mutableStateListOf`), cálculo dinámico del total de tareas y operaciones de adición, cambio de estado y eliminación[cite: 1].
- **Refactorización POO (State Holder):** Encapsulamiento de propiedades y comportamientos en clases gestoras (`TemperatureStateHolder` y `TareasStateHolder`) con estados privados (`private set`) para garantizar el principio de encapsulamiento.
- **Simulación de IA / Prueba Técnica:** Planteamiento de un desafío técnico sobre manejo de estados orientado a un perfil Junior, estructurado como Tech Lead a través del chat de Gemini[cite: 1].

---

## Prompts Utilizados

### Prompt 1: Evaluación Técnica (Requerido por la Guía)
> Actúa como tech lead de Android. Necesito que plantees un desafío técnico sobre manejo de estados en Jetpack Compose. Esto está dirigido a un candidato para una posición de desarrollador Android junior. Quiero que respondas en formato de prueba técnica, incluyendo contexto del negocio, requerimientos, criterios de evaluación y posibles extensiones. Ten en cuenta estas condiciones: no incluyas la solución, el problema debe evaluar buenas prácticas y uso correcto de estados[cite: 1].

### Prompt 2: Refactorización POO de `TemperatureDisplay`
> Actúa como un desarrollador Senior en Android. Necesito implementar el componente `TemperatureDisplay` en Jetpack Compose aplicando Programación Orientada a Objetos (POO). Encapsula el estado y las operaciones en una clase gestora (`TemperatureStateHolder`) con temperatura inicial en 20, métodos `subir()`, `bajar()`, `resetear()` y la lógica de color (rojo si >30, azul si <10)[cite: 1]. Entrega la clase y el composable bien comentados explicando los principios de POO aplicados.

### Prompt 3: Refactorización POO de Lista de Tareas
> Actúa como un desarrollador Senior en Android. Necesito implementar la aplicación de gestión de tareas (`ItemTarea` y `PantallaTareas`) en Jetpack Compose utilizando Programación Orientada a Objetos (POO). Crea el modelo de datos `Tarea` y una clase gestora (`TareasStateHolder`) que encapsule el estado del campo de texto, la lista observable y los métodos para agregar, eliminar y alternar el estado de completado de una tarea[cite: 1]. Proporciona el código completo con comentarios detallados sobre POO.

---

## Capturas de Ejecución por Commit

### Commit 1: implementa TemperatureDisplay con manejo de estado

<img width="886" height="481" alt="image" src="https://github.com/user-attachments/assets/12b471eb-b746-46da-968c-10ca76698b02" />


### Commit 2: agrega componente ItemTarea

<img width="886" height="452" alt="image" src="https://github.com/user-attachments/assets/162a2415-687b-4c06-8b9e-5acffe80ff51" />


### Commit 3: agrega PantallaTareas y conecta con MainActivity

<img width="886" height="455" alt="image" src="https://github.com/user-attachments/assets/e8cff01d-39aa-4054-aa4d-f574db72fb5b" />




.
