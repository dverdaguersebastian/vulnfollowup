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
| **Paso 2** | 26/10/2026 | Diseño del Modelo de Datos Relacional, Diagrama ER y Script DDL | **COMPLETADO** | Acceso a Datos (AD), SGE, PSP |
| **Paso 3** | 29/10/2026 | Carga inicial (JSON), configuración JDBC, render ER y análisis DAFO | **COMPLETADO** | AD, SGE, IPE II |
| **Paso 4** | 30/10/2026 | Implementación de la Capa de Persistencia (JPA, DAOs/Repositorios y Filtrado Dinámico Criteria API) | **COMPLETADO** | Acceso a Datos (AD), PSP, SGE |

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

### 🔹 Paso 2: Diseño del Modelo Relacional, Diagrama ER y Script DDL

- **Fecha:** 26 de octubre de 2026
- **Responsable:** Equipo de desarrollo VulnFollowUp
- **Estado:** ✅ Completado con éxito
- **Módulos Implicados:** Acceso a Datos (AD), Sistemas de Gestión Empresarial (SGE), Programación de Servicios y Procesos (PSP)

#### 1. Objetivos del paso
1. Modelar formalmente las entidades requeridas por el enunciado en Tercera Forma Normal (3FN): **usuarios con roles**, **activos**, **vulnerabilidades**, **valoraciones CVSS** y **proveedores TIC**.
2. Diseñar un diagrama Entidad-Relación visual en sintaxis Mermaid y elaborar el diccionario de datos detallado con tipos, restricciones de clave y nulabilidad.
3. Generar el script SQL DDL reproducible (`database/schema.sql`) para la inicialización y creación de tablas e índices en el gestor relacional.
4. Cumplir estrictamente con los requisitos normativos del marco **DORA (Reglamento UE 2022/2554)** y los casos de uso obligatorios (CU1, CU2, CU3, CU4, CU5).

---

#### 2. Entidades Modeladas y Justificación Técnica

| Entidad / Tabla | Módulo Principal | Justificación Técnica y de Negocio |
| :--- | :---: | :--- |
| **`users`** | PSP / Seguridad | Almacenamiento seguro de usuarios con perfiles (`ADMIN`, `ANALYST`, `VIEWER`). Contraseñas con hash seguro y salt (BCrypt) sin texto plano (apartado 10). |
| **`tic_providers`** | SGE (DORA) | Gestión de dependencias de terceros prestadores TIC (artículos de DORA): criticidad, referencia contractual, fecha periódica de revisión y contactos de emergencia. |
| **`assets`** | AD / SGE | Activos tecnológicos (API, servidor, BBDD) con entorno, responsable técnico y vinculación a proveedor TIC externo (FK). |
| **`vulnerabilities`** | AD / PSP | Catálogo de vulnerabilidades (CVE / código interno), título, descripción, fechas y ciclo de remediación (`ABIERTA`, `PENDIENTE_REMEDIACION`, `RESUELTA`, `CERRADA`). |
| **`vulnerability_assets`** | AD | Tabla asociativa que implementa la relación de cardinalidad **N:M** obligatoria: una vulnerabilidad puede afectar a varios activos y un activo puede tener múltiples vulnerabilidades. |
| **`cvss_assessments`** | AD / PSP | Registro desacoplado de evaluaciones métricas CVSS Base (versiones `3.1` y `4.0`), vector completo, puntuación numérica (0.0 a 10.0) y severidad cualitativa coherente. |
| **`remediation_comments`** | AD / PSP | Histórico auditable de comentarios y evidencias aportadas durante las transiciones de estado de una vulnerabilidad (CU4 y CU5). |
| **`audit_logs`** | PSP / Seguridad | Registro inmutable de eventos y operaciones en el sistema para trazabilidad y control de concurrencia. |

---

#### 3. Reglas de Integridad y Optimización Aplicadas
- **Claves Primarias y Foráneas:** Garantizan la integridad referencial sin permitir registros huérfanos. Se implementan políticas `ON DELETE CASCADE` para tablas dependientes de composición (comentarios y valoraciones de una vulnerabilidad) y `ON DELETE RESTRICT` para evitar borrar proveedores con activos en producción.
- **Restricciones `CHECK`:** Aseguran que valores como roles (`ADMIN`, `ANALYST`, `VIEWER`), estados de remediación y rangos numéricos de puntuación CVSS (`base_score BETWEEN 0.0 AND 10.0`) sean validados a nivel de base de datos antes de persistir.
- **Índices de Alto Rendimiento:** Se crean índices específicos en columnas de filtrado frecuente (`status`, `base_score`, `severity`, `provider_id`) para maximizar el rendimiento de las consultas agregadas del Dashboard y los filtros combinados.

---

#### 4. Entregables Generados en este Paso
1. `database/DIAGRAMA_ER.md`: Documento técnico exhaustivo con el diagrama Mermaid navegable, diccionario de datos y justificaciones de diseño.
2. `database/schema.sql`: Script DDL estándar y reproducible para la creación de todas las tablas e índices.
3. Actualización de `BITACORA.md`: Registro documental de trazabilidad.

---

### 29/10/2026 - Incorporación de datasets iniciales (JSON), configuración de conexión, renderizado del diagrama ER y análisis DAFO
* **Archivos afectados:** `data/actives.json`, `data/application.properties`, `data/providers.json`, `data/vulnerabilities.json`, `database/Diagrama_ER.png`, `docs/ipe/VulnFollowUp - Plan de Negocio y Analisis Estrategico DAFO.pdf`
* **Qué se ha hecho:** 
  1. Se han creado los ficheros de inicialización y carga de datos sintéticos/públicos en formato JSON dentro de `data/`:
     - `actives.json`: Define los activos iniciales de prueba para el cliente financiero, incluyendo la pasarela `API de pagos` (entorno `PRODUCCION`, vinculada al proveedor ID 1) y el activo interno `Base de datos Clientes` (entorno `PREPRODUCCION`).
     - `providers.json`: Estructura la ficha del proveedor TIC externo `HostingSur S.L.` con criticidad `ALTA`, contrato `CT-2025-09A`, fecha límite de revisión periódica en diciembre de 2026 y canales de contacto técnico y soporte.
     - `vulnerabilities.json`: Inicializa el catálogo de amenazas con el registro crítico `CVE-2024-3094` (puerta trasera en XZ Utils), estado inicial `ABIERTA`, valoración CVSS v3.1 con vector `CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:C/C:H/I:H/A:H`, puntuación Base 10.0, severidad `CRITICA` y mapeo de los activos afectados (`[1, 2]`).
  2. Se ha creado el archivo de configuración `data/application.properties` con los parámetros de conexión JDBC para Spring Boot (`spring.datasource.url=jdbc:mysql://localhost:3306/vulnfollowup_db`, usuario y contraseña de entorno de desarrollo seguro).
  3. Se ha incorporado en `database/Diagrama_ER.png` la representación gráfica exportada en imagen de alta resolución del modelo Entidad-Relación relacional diseñado en el Paso 2.
  4. Se ha incorporado y ubicado en la carpeta correspondiente `docs/ipe/` el documento formal `VulnFollowUp - Plan de Negocio y Analisis Estrategico DAFO.pdf`.
* **Por qué se ha hecho (Justificación):** 
  - **Cumplimiento del apartado 4.2 (Carga inicial y registro de información) y Caso de Uso CU1 (Preparar el entorno):** La aplicación requiere disponer de archivos iniciales desacoplados para poblar la base de datos con activos, proveedores y vulnerabilidades sin obligar al usuario a introducirlos manualmente desde formularios en cada despliegue o prueba.
  - **Cumplimiento normativo DORA y módulo SGE (apartados 4 y 11.5):** La inclusión de `providers.json` y su vinculación con `actives.json` modela de forma efectiva la supervisión de riesgos y dependencias de terceros prestadores de servicios TIC que exige el Reglamento (UE) 2022/2554.
  - **Entregables de IPE II (apartados 11.8 y 12.2):** El análisis DAFO y plan de negocio constituye una de las evidencias obligatorias para justificar la viabilidad de la plataforma como servicio ante entidades financieras.
  - **Accesibilidad y visualización documental (Bloque B):** La imagen PNG del diagrama ER asegura la consulta visual directa del esquema de datos para evaluadores y miembros del equipo en cualquier visor sin dependencia del motor de renderizado Mermaid.

---

### 🔹 Paso 4: Implementación de la Capa de Persistencia (Acceso a Datos)
* **Fecha:** 30 de octubre de 2026
* **Responsable:** Equipo Backend VulnFollowUp
* **Estado:** ✅ Completado con éxito
* **Módulos Implicados:** Acceso a Datos (AD), Programación de Servicios y Procesos (PSP), Sistemas de Gestión Empresarial (SGE)
* **Archivos afectados:** 
  - `backend/pom.xml`
  - `backend/src/main/resources/application.properties`
  - `backend/src/main/java/es/nebrija/vulnfollowup/model/enums/*` (`Role`, `Criticality`, `Environment`, `AssetStatus`, `VulnerabilityStatus`, `CvssVersion`, `Severity`)
  - `backend/src/main/java/es/nebrija/vulnfollowup/model/entity/*` (`User`, `TicProvider`, `Asset`, `Vulnerability`, `CvssAssessment`, `RemediationComment`, `AuditLog`)
  - `backend/src/main/java/es/nebrija/vulnfollowup/repository/*` (`UserRepository`, `TicProviderRepository`, `AssetRepository`, `CvssAssessmentRepository`, `RemediationCommentRepository`, `AuditLogRepository`, `VulnerabilityRepository`)
  - `backend/src/main/java/es/nebrija/vulnfollowup/repository/specification/*` (`VulnerabilityFilter`, `VulnerabilitySpecification`)
  - `backend/src/main/java/es/nebrija/vulnfollowup/service/*` (`VulnerabilityService`, `VulnerabilityServiceImpl`)
  - `backend/src/test/java/es/nebrija/vulnfollowup/repository/VulnerabilityRepositoryTest.java`
* **Qué se ha hecho:**
  1. **Elección y configuración tecnológica:** Selección fundamentada de **Spring Data JPA / Hibernate (JPA 3.1 / Jakarta Persistence)** sobre JDBC puro para la gestión de entidades complejas, ciclos de vida y consultas tipadas seguras. Configuración de `pom.xml`, pool HikariCP y propiedades JPA en `application.properties`.
  2. **Mapeo Objeto-Relacional (ORM):** Creación de las 7 entidades del modelo relacional conforme a `schema.sql` y `DIAGRAMA_ER.md`, asegurando:
     - Relación N:M entre `Vulnerability` y `Asset` con `@JoinTable` sobre `vulnerability_assets` y métodos helper bidireccionales (`addAsset`, `removeAsset`).
     - Relaciones 1:N de composición con `CvssAssessment` y `RemediationComment` con cascada total (`CascadeType.ALL`) y `orphanRemoval = true`.
     - Relaciones N:1 con proveedores TIC (`TicProvider`) y analistas (`User`).
     - Enums persistidos con `@Enumerated(EnumType.STRING)` alineados con las restricciones `CHECK` DDL.
  3. **Desacoplamiento mediante Repositorios:** Creación de interfaces de repositorio que extienden `JpaRepository` y `JpaSpecificationExecutor`, aislando el acceso a datos del dominio.
  4. **Filtrado dinámico seguro con Criteria API:** Creación del DTO inmutable `VulnerabilityFilter` y la especificación `VulnerabilitySpecification`. Permite búsquedas combinando de manera opcional: severidad CVSS, estado de remediación, activo afectado y rango de puntuación Base CVSS, garantizando protección absoluta frente a SQL Injection y control de unicidad (`distinct`).
  5. **Capa de Servicio y Pruebas Unitarias/Integración:** Implementación de `VulnerabilityService` y suite de pruebas `VulnerabilityRepositoryTest` sobre base de datos en memoria H2.
* **Por qué se ha hecho (Justificación):**
  - Cumplimiento de los requisitos de persistencia del módulo de Acceso a Datos (AD) y DORA (trazabilidad y severidad técnica CVSS v3.1/v4.0).
  - Máxima seguridad operativa (evitando SQL Injection mediante parámetros de `CriteriaBuilder`) y rendimiento óptimo mediante carga perezosa (`FetchType.LAZY`).

---

*(Las siguientes entradas se añadirán consecutivamente a medida que se ejecuten los siguientes pasos del proyecto).*


