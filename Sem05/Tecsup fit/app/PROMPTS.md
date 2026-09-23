# Registro de Prompts - Fase 2 (Desarrollo Asistido por IA)

## Commit 1: Rediseño Visual Global de la Aplicación
- **Objetivo:** Renovar el aspecto visual de todas las pantallas existentes con degradados, elevación y animaciones.
- **Prompt:** "Por favor, rediseña visualmente todas las pantallas existentes del proyecto (@HomeScreen.kt, @DetailScreen.kt, @ConfirmationScreen.kt, @ReservasScreen.kt y @ProfileScreen.kt)..."
- **Resultado:** Interfaz estilizada con `Brush.verticalGradient`, tarjetas elevadas `RoundedCornerShape(16.dp)` y transiciones suaves mediante `animateColorAsState`.

<img width="886" height="686" alt="image" src="https://github.com/user-attachments/assets/399164f7-13e5-467e-b051-ce181ce5c4bf" />


## Commit 2: Implementación de RutinasScreen y Enrutamiento Global
- **Objetivo:** Activar la 3.ª pestaña de navegación con rutinas e interacción de ejercicios.
- **Prompt:** "Crea el archivo @RutinasScreen.kt para activar la pestaña 'Rutinas' y actualiza @AppNavigation.kt..."
- **Resultado:** Creación de `RutinasScreen.kt` con checklist interactivo de ejercicios y enrutamiento unificado de 4 pestañas en `AppNavigation.kt`.


<img width="584" height="1084" alt="image" src="https://github.com/user-attachments/assets/cb6517b0-3dd3-4cb3-9766-9d3efd0fe2b9" />

<img width="465" height="1003" alt="image" src="https://github.com/user-attachments/assets/949ff4f0-3f8c-41ae-99f1-095389c44f12" />


## Commit 3: Calculadora de IMC Interactiva y Sugerencias en Perfil
- **Objetivo:** Implementar una herramienta interactiva de salud que calcule el IMC y sugiera clases según el estado físico.
- **Prompt:** "Implementa un módulo interactivo de Calculadora de IMC (Índice de Masa Corporal) con recomendaciones dentro de @ProfileScreen.kt..."
- **Resultado:** Integración de inputs interactivos para Peso y Altura, cálculo automático de IMC con diagnóstico y tarjetas de recomendación de clases personalizadas en `ProfileScreen.kt`.

<img width="886" height="712" alt="image" src="https://github.com/user-attachments/assets/3b859bc8-27b2-4a91-aab3-91a2512f67bd" />
