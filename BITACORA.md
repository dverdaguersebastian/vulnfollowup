# 📓 Bitácora y Registro de Actividades - VulnFollowUp

**Proyecto Intermodular · 2.º DAM · Curso 2026-2027**  
**Centro:** Instituto Nebrija de Formación Profesional  
**Proyecto:** *VulnFollowUp* — Plataforma para gestionar y priorizar vulnerabilidades (Marco DORA)  

---

## 📌 Propósito de este documento
Este archivo funciona como **cuaderno de bitácora y registro oficial de seguimiento** del proyecto intermodular. Su objetivo es mantener un historial cronológico, detallado y auditable de cada fase, decisión técnica, configuración y código implementado.

Este registro da soporte directo a los criterios de evaluación:
- **Bloque A (Memoria escrita):** Justificación de arquitectura, decisiones técnicas y cumplimiento normativo.
- **Bloque B (Archivos y código):** Trazabilidad de cambios, orden de carpetas y buenas prácticas.
- **Bloque C (Defensa oral):** Soporte argumental de cada hito y evolución del desarrollo.
- **Módulo IPE II:** Actas de seguimiento, plan de trabajo y registro de contribuciones.

---

## 📋 Resumen de Hitos y Pasos

| Paso | Fecha | Hito / Tarea | Estado | Módulos Implicados |
| :---: | :---: | :--- | :---: | :--- |
| **Paso 1** | 22/10/2026 | Inicialización de la estructura de carpetas, `.gitignore` y bitácora | **COMPLETADO** | Todos (AD, PSP, DI, PMDM, SGE, Transversales) |
| **Paso 2** | *Pendiente* | *Siguiente paso a definir según planificación técnica* | *En cola* | - |

---

## 📝 Registro Detallado de Pasos

### 🔹 Paso 1: Creación de la estructura del repositorio y configuración de Git

- **Fecha:** 22 de octubre de 2026
- **Responsable:** Equipo de desarrollo VulnFollowUp
- **Estado:** ✅ Completado con éxito

#### 1. Objetivos del paso
1. Establecer la jerarquía de directorios estándar y modular para el proyecto completo siguiendo la especificación oficial del enunciado (**apartado 12.3: Estructura recomendada del repositorio**).
2. Asegurar que las carpetas queden debidamente registradas y versionadas en Git mediante archivos de anclaje (`.gitkeep`).
3. Configurar un archivo `.gitignore` robusto y profesional que cubra la totalidad del ecosistema tecnológico (Java/Maven, Node/Frontend, Android/Gradle, IDEs y seguridad).
4. Crear el documento maestro de bitácora (`BITACORA.md`) que se mantendrá actualizado en cada avance.

---

#### 2. Justificación Técnica y Modular de la Estructura

Siguiendo el diseño del proyecto, se han definido y creado las siguientes carpetas:

```text
vulnfollowup/
├── backend/                  # API REST y lógica de negocio (PSP + AD + SGE)
├── desktop-client/           # Cliente de escritorio / SPA Web en React/Vue/Angular (DI)
├── android-app/              # Aplicación móvil Android para consulta y seguimiento (PMDM)
├── database/                 # Scripts SQL DDL/DML, esquema y carga inicial (AD)
├── data/                     # Conjunto de datos sintéticos/públicos JSON/CSV (AD + SGE)
├── docs/                     # Documentación técnica y módulos transversales
│   ├── api/                  # Documentación y especificación OpenAPI/REST (EN)
│   ├── english/              # Glosario bilingüe, executive summary, guion y vídeo (Ampliación / Inglés Prof.)
│   ├── ipe/                  # Lean Canvas, presupuesto, actas de seguimiento y portfolios (IPE II)
│   ├── sge/                  # Modelo de proveedores TIC y diagrama de proceso BPMN DORA (SGE)
│   ├── digitalizacion/       # Informe de madurez digital, flujo de datos y tecnologías habilitadoras
│   └── sostenibilidad/       # Informe ODS, auditoría de consumo y decálogo de software sostenible
├── .gitignore                # Reglas de exclusión para Git
├── BITACORA.md               # Registro continuo de actividades y decisiones del proyecto
└── README.md                 # Documento principal del repositorio en inglés
```

##### 📂 Detalle de cada módulo y carpeta:
1. **`backend/` (PSP + AD + SGE):**
   - Alojará el servicio HTTP/REST central, el modelo relacional/JPA, la lógica de validación de vectores CVSS (v3.1 y v4.0), concurrencia (hilos/ExecutorService) y subprocesos (`ProcessBuilder`).
2. **`desktop-client/` (DI):**
   - Contendrá el cliente web/escritorio para los roles `ADMIN` y `ANALYST`, con diseño WCAG 2.1 AA, dashboard gráfico de severidades y empaquetado (Electron/Docker Nginx).
3. **`android-app/` (PMDM):**
   - Contendrá la app nativa Android para consulta remota, cambio de estado de vulnerabilidades y caché local offline (Room/DataStore).
4. **`database/` (AD):**
   - Scripts de creación de tablas normalizadas, integridad referencial (claves foráneas, restricciones de unicidad) y transacciones.
5. **`data/` (AD + SGE):**
   - Ficheros fuente de prueba (JSON/CSV) con al menos 20 vulnerabilidades (sintéticas y públicas CVE) y activos/proveedores iniciales.
6. **`docs/` (Módulos transversales y especificaciones):**
   - `docs/api/`: Contratos de interfaz HTTP, ejemplos de petición/respuesta y códigos de estado.
   - `docs/english/`: Glosario técnico bilingüe (mínimo 30 términos), *Executive Summary* para el cliente y material audiovisual.
   - `docs/ipe/`: Plan de viabilidad, Lean Canvas, DAFO, estructura de costes/presupuesto y actas.
   - `docs/sge/`: Documentación del proceso de homologación de proveedores TIC bajo el reglamento DORA.
   - `docs/digitalizacion/`: Diagnóstico del sector financiero, matriz de riesgos y mapa de flujo de datos.
   - `docs/sostenibilidad/`: Justificación ODS (8, 9, 12, 16), comparativas de huella digital antes/después y decálogo verde.

---

#### 3. Control de Versiones: Archivos `.gitkeep`
Git ignora por diseño los directorios vacíos en el sistema de archivos. Para garantizar que la estructura requerida se clone y descargue de manera idéntica en cualquier entorno y equipo de trabajo sin perder carpetas, se ha incorporado un archivo vacío `.gitkeep` en cada una de las carpetas creadas.

---

#### 4. Configuración del Archivo `.gitignore`
Se ha redactado y aplicado un fichero `.gitignore` exhaustivo adaptado a las tecnologías del proyecto:
- **Java / Backend:** Exclusión de carpetas de compilación (`target/`, `build/`), binarios (`*.class`, `*.jar`, `*.war`) y archivos de empaquetado.
- **Frontend / Cliente de Escritorio:** Exclusión de la carpeta masiva de dependencias `node_modules/`, logs de npm/yarn y carpetas de build/distribución (`dist/`, `out/`).
- **Android:** Exclusión de ficheros generados por Gradle (`.gradle/`), archivos de configuración local privada (`local.properties`), cachés nativas (`.cxx/`) y artefactos generados (`*.apk`, `*.aab`).
- **Entornos e IDEs:** Reglas para IntelliJ IDEA (`.idea/`), VS Code (`.vscode/` preservando configuraciones compartidas de equipo), Eclipse y NetBeans.
- **Sistema Operativo:** Exclusión de archivos basura de Windows (`Thumbs.db`, `desktop.ini`) y macOS (`.DS_Store`).
- **Seguridad y Cumplimiento DORA (Apartado 10 del enunciado):** Exclusión explícita de archivos de variables de entorno (`.env`), certificados privados (`*.pem`, `*.key`), almacenes de claves (`*.jks`, `*.keystore`) y bases de datos dinámicas locales, previniendo fugas de credenciales en el repositorio público.

---

#### 5. Evidencias de Verificación
- Estructura de carpetas verificada en el árbol de archivos.
- Fichero `.gitignore` probado y validado.
- Bitácora creada y enlazada.

---

*(Las siguientes entradas se añadirán consecutivamente a medida que se ejecuten los siguientes pasos del proyecto).*
