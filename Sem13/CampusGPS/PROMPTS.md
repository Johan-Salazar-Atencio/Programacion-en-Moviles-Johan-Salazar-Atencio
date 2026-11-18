# PROMPTS

CampusGPS · Laboratorio 13 · Fase 2 (rama `mejora-ia`) · Johan Salazar Atencio

Herramienta de IA usada: Claude (Claude Code). La guía menciona Gemini.

## Prompts importantes

| Prompt que escribí | Qué respondió la IA | Qué cambié o descarté | Por qué |
|---|---|---|---|
| Agrega chips (Estudio, Comida, Eventos, Servicios) para filtrar los lugares por categoría, tanto en el mapa como en la lista. Un chip tocado de nuevo quita el filtro. Respeta MVVM: sin lógica en los Composables. | Un StateFlow de categoría en el ViewModel, la lista filtrada con combine y un composable FiltroCategorias. | Hice que el lugar más cercano se calcule sobre la lista ya filtrada. El composable solo recibe la lista y avisa qué chip se tocó. | El filtro es lógica: va en el ViewModel. Así la vista solo dibuja y no rompe MVVM. |
| Agrega un selector de radio (100, 200 o 500 m) que dibuje un círculo alrededor de mi posición y marque en la lista los lugares que quedan dentro. Quién está dentro debe decidirlo el ViewModel, no la vista. | Un StateFlow de radio y otro con los ids de los lugares dentro del radio en el ViewModel; un Polygon circular en el mapa. | Descarté el Circle de Google Maps porque el mapa es osmdroid; se usó Polygon.pointsAsCircle. Quién está dentro del radio lo decide el ViewModel, no la lista. | Calcular quién está dentro es lógica de negocio. El composable solo dibuja el círculo y el texto. |
| Guarda con DataStore el último lugar seleccionado y recupéralo al abrir la app, de modo que quede resaltado en la lista y el mapa lo muestre. El ViewModel no debe recibir un Context. | Una interfaz UltimoLugarRepository con una implementación en DataStore; el ViewModel lee al iniciar y guarda al seleccionar. | Dejé el Context solo dentro de la implementación y la fábrica. El ViewModel recibe la interfaz, no el Context. | Un Context dentro del ViewModel rompe la regla de capas y dificulta probarlo. |

## Análisis propio

- Filtro por categoría: el ViewModel guarda la categoría elegida y publica la lista ya filtrada. El composable solo muestra los chips y la lista que recibe.
- Radio de cercanía: el ViewModel decide qué lugares quedan dentro del radio. El mapa solo dibuja el círculo y la lista muestra el texto.
- Último lugar: DataStore se usa solo dentro de un Repository con interfaz. El ViewModel no recibe Context y solo llama a esa interfaz.
- En las tres mejoras se mantuvo MVVM: la View solo habla con su ViewModel y la distancia no se calcula en ningún Composable.
