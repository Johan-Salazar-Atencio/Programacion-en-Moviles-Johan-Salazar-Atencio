# Registro de Prompts - Fase 2 (Mejora Asistida por IA)

**Proyecto:** Tecsup Store  
**Estudiante:** Johan Salazar Atencio  
**Rama Git:** `mejora-ia` / `feature/version-ia`

---

## Introducción
Este documento registra las instrucciones directas (prompts) proporcionadas al agente de Inteligencia Artificial (Gemini in-IDE) dentro de Android Studio para el desarrollo de la Fase 2 del laboratorio.

---

## Prompts Ejecutados

### Prompt 1: Persistencia de datos, Callbacks y Pantalla de Favoritos
> **Instrucción:**  
> "Modifica TarjetaProducto.kt, PantallaCarrito.kt y crea PantallaFavoritos.kt.
> 1. En TarjetaProducto.kt, agrega el callback `onFavoritoToggle: (Producto) -> Unit` y ejecútalo al presionar 'Favoritos' en el DropdownMenu.
> 2. Reestructura PantallaCarrito.kt para recibir la lista de productos y callbacks desde afuera, asegurando que los productos agregados no se borren al navegar entre pantallas.
> 3. Crea PantallaFavoritos.kt que reciba la lista de productos marcados como favoritos y los muestre usando TarjetaProducto. Si la lista está vacía, muestra el mensaje 'No tienes productos en favoritos'."

---

### Prompt 2: Indicador Badge en el NavigationDrawer
> **Instrucción:**  
> "Modifica AppDrawer.kt agregando el parámetro `cantidadFavoritos: Int = 0`. Añade la propiedad `badge` al `NavigationDrawerItem` de la opción FAVORITOS para mostrar un `Badge` con el número actual de favoritos únicamente cuando `cantidadFavoritos > 0`."

---

### Prompt 3: Eliminación de Favoritos, Refinamiento Visual M3 y Conexión Global
> **Instrucción:**  
> "En AppNavegacion.kt, PantallaCarrito.kt y PantallaFavoritos.kt:
> 1. Agrega la funcionalidad para eliminar un producto de la lista de favoritos directamente desde la vista de favoritos o al desmarcarlo en el DropdownMenu.
> 2. Mejora la apariencia visual de todas las pestañas: aplica bordes redondeados, sombras elevadas en las tarjetas, colores temáticos armónicos de Material 3 y espaciado (padding/margins) consistente en los contenedores.
> 3. Conecta el estado global de ambas listas (carrito y favoritos) con AppDrawer, PantallaCarrito y PantallaFavoritos."

---

## COMMITS REALIZADOS

# Commit 1
implementa callback de favoritos, persistencia en carrito y crear PantallaFavoritos
<img width="886" height="468" alt="image" src="https://github.com/user-attachments/assets/66ecd33d-bbe3-45ea-8390-cfc9fab568c1" />
<img width="886" height="465" alt="image" src="https://github.com/user-attachments/assets/9a1ca0f5-a0a2-48d7-b736-eceb2e3bf204" />
<img width="886" height="483" alt="image" src="https://github.com/user-attachments/assets/c6120619-aac7-494e-b4fe-4fc5241d956d" />


# Commit 2
agrega badge contador de favoritos en NavigationDrawer
<img width="886" height="409" alt="image" src="https://github.com/user-attachments/assets/5143a3aa-a0c6-4405-91e5-14bd131a0576" />


# Commit 3
implementar eliminacion de favoritos, refinamiento visual de UI
<img width="886" height="468" alt="image" src="https://github.com/user-attachments/assets/af2a5680-02d5-4322-b199-d94bf0a17592" />


