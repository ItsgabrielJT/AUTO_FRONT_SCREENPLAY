---
description: Guía de estándares de codificación para proyectos de automatización con Serenity BDD y Screenplay.
applyTo: 'Proyectos de automatización, creación de tareas, acciones, o refactorización de código'
---

### Guías para el Código Limpio (Clean Code)

**Cero Comentarios:** El código debe ser "limpio" y autodocumentado mediante nombres descriptivos de variables, métodos y clases; no utilices comentarios para explicar qué hace el código.

**Principios SOLID:**
**Responsabilidad Única (SRP):** Cada clase debe tener una sola razón para cambiar (ej. separar localizadores de las acciones/interacciones).
**Abierto/Cerrado (OCP):** Abierto a la extensión pero cerrado a la modificación (ej. usar interfaces para drivers de navegadores).
**Sustitución de Liskov (LSP):** Las subclases deben poder sustituir a sus clases base sin romper la funcionalidad.
**Segregación de Interfaces (ISP):** No obligar a implementar métodos que no se utilizan.
**Inversión de Dependencias (DIP):** Depender de abstracciones, no de implementaciones concretas.

**Estructura de Carpetas:**
`src/test/java`: Código fuente de las pruebas (Step definitions, Tasks, Interactions, Questions, UI). 
`src/test/resources/features`: Archivos `.feature` escritos en lenguaje Gherkin.



---

Esta estructura asegura que el agente siga un enfoque sistemático y escalable, optimizando la detección de errores y facilitando el mantenimiento a largo plazo.