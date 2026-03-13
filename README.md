# Serenity Screenplay - Pruebas E2E de Listado de Kudos

## Que valida este modulo

Este modulo contiene pruebas E2E con Serenity BDD, Selenium 4, Cucumber y JUnit Platform para validar el listado de Kudos en SofkianOS.

La automatizacion cubre un flujo hibrido:

- siembra datos de prueba por API en producer-api
- espera la persistencia asincrona consultando consumer-worker
- valida el comportamiento visible en la UI del frontend

Actualmente la cobertura principal valida:

- visualizacion de multiples Kudos creados por un mismo emisor
- incremento del contador totalElements despues de sembrar datos
- visualizacion del emisor en el listado
- filtrado por categoria y busqueda por mensaje en la pantalla de Kudos

## Stack usado

- Serenity BDD 4.2.34
- Selenium 4
- Cucumber
- Gradle
- Java 17
- Chrome

## Estructura del modulo

- src/test/resources/features: escenarios Gherkin
- src/test/resources/junit-platform.properties: configuracion central de Cucumber
- src/test/resources/serenity.conf: configuracion de Serenity y ambientes
- src/test/java/screenplay_pattern/userinterface: mapeo de Target (locators)
- src/test/java/screenplay_pattern/tasks: acciones de negocio de alto nivel
- src/test/java/screenplay_pattern/interactions: interacciones de bajo nivel (UI/API)
- src/test/java/screenplay_pattern/questions: consultas para aserciones
- src/test/java/screenplay_pattern/models: contexto y modelos de soporte
- src/test/java/screenplay_pattern/stepdefinitions: glue code Cucumber
- src/test/java/screenplay_pattern/runners: runner JUnit Platform

## Prerrequisitos

Antes de ejecutar las pruebas, verifica lo siguiente:

- Java 17 instalado
- Google Chrome instalado
- frontend disponible en http://localhost:5173
- producer-api disponible en http://localhost:8082
- consumer-worker disponible en http://localhost:8081

Si vas a levantar el stack completo con Docker, puedes usar el comando de tu repositorio principal (si aplica), por ejemplo:

```bash
docker compose up -d --build
```

## Como corro las pruebas

Ubicate en la carpeta e2e_screenplay y ejecuta:

```bash
./gradlew clean test aggregate
```

Ese comando hace tres cosas:

1. limpia artefactos anteriores
2. ejecuta los escenarios Cucumber
3. genera el reporte HTML de Serenity

## Donde veo el reporte

Al finalizar la ejecucion, el reporte queda en:

```text
target/site/serenity/index.html
```

Tambien puedes revisar el reporte de pruebas Gradle en:

```text
build/reports/tests/test/index.html
```

## Ambientes configurados

La configuracion vive en serenity.conf y soporta estos ambientes:

- default
- local
- dev
- qa

### Local

Usa el frontend en localhost:5173.

```bash
./gradlew clean test aggregate -Denvironment=local
```

### Dev

Debes definir la variable SERENITY_DEV_BASE_URL.

```bash
SERENITY_DEV_BASE_URL=https://tu-url-dev \
./gradlew clean test aggregate -Denvironment=dev
```

### QA

Debes definir la variable SERENITY_QA_BASE_URL.

```bash
SERENITY_QA_BASE_URL=https://tu-url-qa \
./gradlew clean test aggregate -Denvironment=qa
```

## Notas utiles

- Las pruebas corren en headless chrome por defecto
- El filtrado de Cucumber se controla con la etiqueta @Kudos
- El reporte de Serenity se genera al final del build con la tarea aggregate
- La espera de persistencia asincrona y de resultados en UI esta configurada en hasta 8 segundos

## Problemas comunes

### El reporte sale vacio

Ejecuta el build completo con aggregate y luego abre el HTML en target/site/serenity/index.html.

### El build falla por version de Java

Si Gradle usa Java 8, exporta Java 17 para la ejecucion:

```bash
./gradlew clean test aggregate
```

### La prueba falla al sembrar datos

Revisa que producer-api y consumer-worker esten arriba y respondiendo en los puertos esperados.

### La UI no carga

Verifica que el frontend este disponible en la URL del ambiente configurado.

### Quiero ver el navegador en ejecucion

Puedes sobreescribir la configuracion headless por linea de comandos:

```bash
./gradlew clean test aggregate -Dheadless.mode=false
```