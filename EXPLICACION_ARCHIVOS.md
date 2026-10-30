# 📖 Guía Masticada y Explicación Archivo por Archivo - VulnFollowUp (Backend)

> **Proyecto Intermodular · 2.º DAM (Curso 2026-2027)**  
> **Centro:** Instituto Nebrija de Formación Profesional  
> **Objetivo de este documento:** Explicar en un lenguaje sencillo, claro y sin tecnicismos innecesarios qué hace exactamente cada uno de los archivos creados en el backend, por qué existen y cómo se comunican entre sí. Ideal para repasar, estudiar y defender el proyecto.

---

## 💡 1. La analogía del Restaurante (¿Cómo funciona todo junto?)

Para no perderte entre tantas carpetas y nombres técnicos, imagina que nuestro backend es un **restaurante de alta cocina**:

1. **La Base de Datos (MySQL):** Es la despensa y el almacén donde se guardan físicamente todos los datos.
2. **Las Entidades (`model/entity`):** Son los ingredientes clasificados (carne, verdura, salsa). En código, representan las filas de las tablas.
3. **Los Enums (`model/enums`):** Son las etiquetas fijas (ej. "fresco", "congelado", "caducado"). Evitan que alguien invente una etiqueta no permitida.
4. **Los Repositorios (`repository`):** Son los mozos de almacén. Son los únicos que tienen las llaves de la despensa. Si la cocina necesita algo, se lo pide a ellos con frases como *"dame las vulnerabilidades abiertas"* o *"guarda este comentario"*.
5. **El Filtro y la Especificación (`specification`):** Es la hoja de pedido personalizada. Si un cliente pide *"platos picantes, sin gluten y que cuesten menos de 10 €"*, este componente se asegura de traducir esa petición a un lenguaje exacto y seguro para que el mozo de almacén no traiga nada que no corresponda ni sufra robos (*inyección SQL*).
6. **El Servicio (`service`):** Es el Chef principal. Recibe la comanda, comprueba que se cumplan las reglas (ej. no se puede cerrar una vulnerabilidad sin dejar un comentario justificativo) y le pide a los mozos de almacén los datos necesarios para cocinar la respuesta.

---

## 🗺️ 2. Mapa Visual de los Archivos Creados

```text
backend/
├── pom.xml                                               # 1. Lista de la compra del proyecto (dependencias)
└── src/
    ├── main/
    │   ├── resources/
    │   │   └── application.properties                    # 2. Configuración secreta y conexión a MySQL
    │   └── java/es/nebrija/vulnfollowup/
    │       ├── VulnFollowUpApplication.java              # 3. El botón de encendido del backend
    │       │
    │       ├── model/enums/                              # 4. Las etiquetas y valores cerrados
    │       │   ├── Role.java                             # Permisos: ADMIN, ANALYST, VIEWER
    │       │   ├── Criticality.java                      # Criticidad proveedor DORA: BAJA, MEDIA, ALTA, CRITICA
    │       │   ├── Environment.java                      # Dónde corre el activo: PRODUCCION, PREPRODUCCION, etc.
    │       │   ├── AssetStatus.java                      # Estado activo: ACTIVO, INACTIVO, MANTENIMIENTO
    │       │   ├── VulnerabilityStatus.java              # Fases del fallo: ABIERTA, PENDIENTE, RESUELTA, CERRADA
    │       │   ├── CvssVersion.java                      # Versiones estándar: 3.1 o 4.0
    │       │   └── Severity.java                         # Gravedad CVSS: NINGUNA, BAJA, MEDIA, ALTA, CRITICA
    │       │
    │       ├── model/entity/                             # 5. Los moldes de las tablas de la BD
    │       │   ├── User.java                             # Tabla 'users'
    │       │   ├── TicProvider.java                      # Tabla 'tic_providers'
    │       │   ├── Asset.java                            # Tabla 'assets'
    │       │   ├── Vulnerability.java                    # Tabla 'vulnerabilities' (El rey del sistema)
    │       │   ├── CvssAssessment.java                   # Tabla 'cvss_assessments'
    │       │   ├── RemediationComment.java               # Tabla 'remediation_comments'
    │       │   └── AuditLog.java                         # Tabla 'audit_logs'
    │       │
    │       ├── repository/                               # 6. Quienes hablan con la base de datos
    │       │   ├── UserRepository.java
    │       │   ├── TicProviderRepository.java
    │       │   ├── AssetRepository.java
    │       │   ├── CvssAssessmentRepository.java
    │       │   ├── RemediationCommentRepository.java
    │       │   ├── AuditLogRepository.java
    │       │   ├── VulnerabilityRepository.java          # El repositorio principal con soporte dinámico
    │       │   └── specification/
    │       │       ├── VulnerabilityFilter.java          # La caja donde metes los filtros opcionales
    │       │       └── VulnerabilitySpecification.java   # El traductor de filtros a consultas SQL seguras
    │       │
    │       └── service/                                  # 7. La lógica y reglas de negocio
    │           ├── VulnerabilityService.java             # Contrato de lo que el sistema sabe hacer
    │           └── impl/VulnerabilityServiceImpl.java    # El que ejecuta las reglas y coordina todo
    │
    └── test/                                             # 8. Las pruebas para demostrar que no falla
        ├── resources/application-test.properties         # Configuración de base de datos volátil H2
        └── java/.../VulnerabilityRepositoryTest.java     # Batería de pruebas automáticas
```

---

## 📂 3. Explicación Masticada de Todos y Cada Uno de los Archivos

### 📦 3.1. Archivos de Configuración y Arranque

#### 📄 [`pom.xml`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/pom.xml)
- **¿Qué es?:** Es el archivo de configuración de Maven (la herramienta que construye y compila el proyecto Java).
- **¿Para qué sirve?:** Contiene la lista de librerías externas que necesita el backend para funcionar: Spring Boot (el motor general), Spring Data JPA y Hibernate (para no picar SQL a mano), el conector oficial de MySQL y la base de datos en memoria H2 para hacer pruebas sin romper nada real.
- **En lenguaje sencillo:** Es la *lista de la compra*. Le dice a Java qué librerías tiene que descargar de Internet para que el código funcione.

#### 📄 [`backend/src/main/resources/application.properties`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/resources/application.properties)
- **¿Qué es?:** El cuaderno de ajustes del servidor.
- **¿Para qué sirve?:** Le indica a Spring Boot dónde está la base de datos MySQL (`localhost:3306/vulnfollowup_db`), cuál es el usuario (`usuario_prueba`) y la contraseña, cuántas conexiones abiertas puede mantener a la vez (HikariCP pool) y que muestre en la consola de comandos las consultas SQL formateadas para poder depurar fallos fácilmente.
- **En lenguaje sencillo:** Las *credenciales y la dirección* para que Java sepa cómo y dónde llamar a la puerta de MySQL.

#### 📄 [`VulnFollowUpApplication.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/VulnFollowUpApplication.java)
- **¿Qué es?:** La clase Java que contiene el método `public static void main(String[] args)`.
- **¿Para qué sirve?:** Es el interruptor general. Cuando ejecutas esta clase, Spring Boot se despierta, lee el `application.properties`, se conecta a MySQL, escanea todas las entidades y repositorios y deja el backend listo para recibir peticiones.
- **En lenguaje sencillo:** El *botón de START* del backend.

---

### 🏷️ 3.2. Enums (`model/enums/`): El vocabulario cerrado del sistema

Los *Enums* sirven para evitar que la gente cometa faltas de ortografía o meta datos erróneos en columnas clave. Si una vulnerabilidad solo puede estar en 4 estados, un Enum impide físicamente que alguien escriba `"EN_PROCESO"` o `"ARREGLAO"`. Coinciden exactamente con las restricciones `CHECK` que pusimos en el [schema.sql](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/database/schema.sql).

#### 📄 [`Role.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/enums/Role.java)
- **Valores:** `ADMIN`, `ANALYST`, `VIEWER`.
- **Qué hace:** Define los tres tipos de usuario del sistema para el control de acceso (RBAC).

#### 📄 [`Criticality.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/enums/Criticality.java)
- **Valores:** `BAJA`, `MEDIA`, `ALTA`, `CRITICA`.
- **Qué hace:** Mide la importancia de un proveedor informático externo (proveedor TIC) según las normas europeas de resiliencia financiera (marco DORA).

#### 📄 [`Environment.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/enums/Environment.java)
- **Valores:** `PRODUCCION`, `PREPRODUCCION`, `DESARROLLO`.
- **Qué hace:** Indica en qué entorno real está funcionando un activo (ej. una API en `PRODUCCION` es crítica para el banco; en `DESARROLLO` es solo para pruebas internas).

#### 📄 [`AssetStatus.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/enums/AssetStatus.java)
- **Valores:** `ACTIVO`, `INACTIVO`, `MANTENIMIENTO`.
- **Qué hace:** Refleja la salud operativa de un servidor, aplicación o base de datos.

#### 📄 [`VulnerabilityStatus.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/enums/VulnerabilityStatus.java)
- **Valores:** `ABIERTA`, `PENDIENTE_REMEDIACION`, `RESUELTA`, `CERRADA`.
- **Qué hace:** Es el semáforo del ciclo de vida de la vulnerabilidad. Nace `ABIERTA`, pasa a `PENDIENTE_REMEDIACION` mientras se trabaja el parche, `RESUELTA` cuando se aplica y `CERRADA` tras pasar la auditoría final.

#### 📄 [`CvssVersion.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/enums/CvssVersion.java)
- **Valores:** `V3_1 ("3.1")`, `V4_0 ("4.0")`.
- **Qué hace:** Guarda la versión del cálculo estándar de ciberseguridad utilizado para valorar la amenaza.

#### 📄 [`Severity.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/enums/Severity.java)
- **Valores:** `NINGUNA`, `BAJA`, `MEDIA`, `ALTA`, `CRITICA`.
- **Qué hace:** La gravedad cualitativa estándar que se le asigna a una vulnerabilidad en función de su nota numérica del 0 al 10.

---

### 🏛️ 3.3. Entidades JPA (`model/entity/`): Los moldes de los datos

Cada una de estas clases representa una **tabla** en la base de datos relacional. Cada objeto que creamos de esta clase es una **fila** real.

#### 📄 [`User.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/entity/User.java) (Tabla `users`)
- **Qué guarda:** Los usuarios que inician sesión (nombre, contraseña hasheada en BCrypt, email corporativo, su rol y si está activo).
- **Detalle de seguridad:** Su método `toString()` omite a propósito la contraseña para que nunca se filtre en los archivos de log del servidor.

#### 📄 [`TicProvider.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/entity/TicProvider.java) (Tabla `tic_providers`)
- **Qué guarda:** Empresas externas que nos dan servicio tecnológico (como HostingSur, AWS o la pasarela de pagos). Guarda el contrato, su criticidad según DORA y la fecha en la que hay que auditar el contrato obligatoriamente (`contractReviewDate`).
- **Relación:** Tiene una lista de activos (`assets`). Es una relación 1:N (un proveedor da soporte a muchos activos).

#### 📄 [`Asset.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/entity/Asset.java) (Tabla `assets`)
- **Qué guarda:** Las máquinas y programas de la empresa (ej. "API de Pagos", "Servidor de Base de Datos").
- **Relaciones:**
  - N:1 con `TicProvider`: sabe qué empresa externa lo gestiona (si es interno, este campo es nulo).
  - N:M con `Vulnerability`: un activo puede sufrir muchas vulnerabilidades a la vez.

#### 📄 [`Vulnerability.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/entity/Vulnerability.java) (Tabla `vulnerabilities`)
- **Qué guarda:** **Es el corazón del proyecto**. Guarda el código identificador (ej. `CVE-2024-3094`), el título, la descripción técnica, la fecha en que se descubrió, la fecha límite para arreglarla, el analista asignado y su estado actual.
- **Relaciones complejas:**
  1. **N:M con `Asset` (`@ManyToMany`):** A través de la tabla intermedia `vulnerability_assets`. Una vulnerabilidad puede infectar 10 servidores y un servidor puede tener 5 vulnerabilidades. Tiene los métodos `addAsset()` y `removeAsset()` para que cuando vincules un activo, ambos lados de la memoria queden sincronizados.
  2. **1:N con `CvssAssessment` (`@OneToMany`):** Guarda la historia de valoraciones numéricas de esa vulnerabilidad. Si borras la vulnerabilidad, se borran sus notas en cascada (`CascadeType.ALL`, `orphanRemoval = true`).
  3. **1:N con `RemediationComment` (`@OneToMany`):** Guarda todas las justificaciones y notas que han ido dejando los ingenieros cada vez que tocan esa vulnerabilidad.
- **Identidad:** Dos vulnerabilidades son iguales si tienen el mismo `cveId`.

#### 📄 [`CvssAssessment.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/entity/CvssAssessment.java) (Tabla `cvss_assessments`)
- **Qué guarda:** La nota de peligro del fallo. Guarda el vector CVSS (un texto como `CVSS:3.1/AV:N/AC:L...`), la nota numérica (ej. `9.8` de 10) y la severidad (`CRITICA`).
- **Relación:** Pertenece obligatoriamente a una `Vulnerability`.

#### 📄 [`RemediationComment.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/entity/RemediationComment.java) (Tabla `remediation_comments`)
- **Qué guarda:** El libro de firmas de los analistas. Cada vez que el analista cambia el estado de una vulnerabilidad (por ejemplo de ABIERTA a RESUELTA), aquí se anota obligatoriamente quién fue (`user`), qué parche metió (`commentText`), cuál era el estado antes y cuál es el nuevo. Esto da cumplimiento a los artículos de trazabilidad de DORA.

#### 📄 [`AuditLog.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/model/entity/AuditLog.java) (Tabla `audit_logs`)
- **Qué guarda:** La caja negra del sistema. Cada vez que alguien entra, borra o edita algo importante, se guarda un registro inmutable con la IP de origen, la fecha y la acción realizada.

---

### 🗄️ 3.4. Repositorios (`repository/`): La pasarela con la Base de Datos

En Spring Data JPA no hace falta escribir sentencias SQL manuales como `SELECT * FROM users WHERE username = ...`. Tan solo declaramos una interfaz Java que extiende de `JpaRepository` y Spring genera el código SQL óptimo automáticamente por debajo.

- 📄 [`UserRepository.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/repository/UserRepository.java): Permite buscar usuarios por nombre de usuario (`findByUsername`), email o rol, y verificar si ya existen.
- 📄 [`TicProviderRepository.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/repository/TicProviderRepository.java): Permite buscar proveedores por nombre, criticidad y alertar de contratos cuya fecha de revisión esté vencida (`findByContractReviewDateBeforeAndIsActiveTrue`).
- 📄 [`AssetRepository.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/repository/AssetRepository.java): Filtra activos por entorno (ej. dame solo los de `PRODUCCION`) o por el proveedor que los mantiene.
- 📄 [`CvssAssessmentRepository.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/repository/CvssAssessmentRepository.java): Busca notas CVSS por rango (ej. puntuaciones entre 7.0 y 10.0).
- 📄 [`RemediationCommentRepository.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/repository/RemediationCommentRepository.java): Devuelve el historial de comentarios de una vulnerabilidad ordenado cronológicamente del más nuevo al más viejo.
- 📄 [`AuditLogRepository.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/repository/AuditLogRepository.java): Busca eventos de auditoría por usuario o rango de fechas.

#### 📄 [`VulnerabilityRepository.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/repository/VulnerabilityRepository.java) (El más potente)
- **¿Qué tiene de especial?:** Además de extender `JpaRepository`, extiende `JpaSpecificationExecutor<Vulnerability>`.
- **¿Qué le aporta?:** Le da el superpoder de ejecutar **consultas dinámicas**. Incluye los métodos `findByFilter(VulnerabilityFilter filter)`, que permiten al frontend o a la app móvil buscar vulnerabilidades combinando los filtros que el usuario quiera, devolviendo los resultados limpios o paginados (`Pageable`).

---

### 🔍 3.5. Filtrado Dinámico Inteligente (`repository/specification/`)

Este es uno de los requisitos más exigentes del proyecto: ¿cómo buscar en la base de datos combinando 4 parámetros distintos si el usuario puede dejar algunos en blanco, sin romper la consulta ni permitir inyecciones SQL?

#### 📄 [`VulnerabilityFilter.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/repository/specification/VulnerabilityFilter.java)
- **¿Qué es?:** Es un `record` Java (un objeto inmutable que sirve solo para transportar datos).
- **¿Qué lleva dentro?:**
  1. `severity` (Severidad CVSS deseada, o nulo si le da igual).
  2. `status` (Estado del fallo, o nulo).
  3. `assetId` (ID del servidor afectado, o nulo).
  4. `minScore` y `maxScore` (Rango de nota CVSS, ej. de 7.5 a 10.0).
- **Inteligencia:** Si alguien intenta meter una nota menor que 0, mayor que 10, o pone un mínimo más alto que el máximo, este archivo lanza un error inmediatamente antes de gastar recursos de base de datos.
- **En lenguaje sencillo:** La *comanda del cliente*. Si el cliente no especifica un dato, ese campo va vacío (`null`).

#### 📄 [`VulnerabilitySpecification.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/repository/specification/VulnerabilitySpecification.java)
- **¿Qué es?:** La fábrica que traduce la "comanda" (`VulnerabilityFilter`) a una consulta de base de datos real utilizando **JPA Criteria API**.
- **¿Cómo razona paso a paso?:**
  1. Activa `query.distinct(true)`: Como para buscar por servidor o por nota CVSS hay que hacer cruces (`JOIN`), esto garantiza que si una vulnerabilidad afecta a 5 servidores, no aparezca 5 veces repetida en pantalla.
  2. Pregunta: *¿El usuario ha pedido filtrar por estado?* Si es sí, añade la condición `estado = :status`. Si es nulo, no hace nada.
  3. Pregunta: *¿El usuario ha indicado un activo?* Si es sí, hace un `JOIN` con la tabla intermedia de activos y busca ese ID. Si es nulo, no hace el JOIN.
  4. Pregunta: *¿El usuario ha pedido nota o severidad CVSS?* Si es sí, hace un `JOIN` con la tabla de valoraciones CVSS y añade las condiciones (`severity = :sev` y `baseScore BETWEEN min AND max`).
  5. **Seguridad contra SQL Injection:** Todos los valores se colocan mediante parámetros enlazados (`?`). Da igual si alguien escribe comillas o comandos maliciosos; MySQL los tratará como texto plano y jamás los ejecutará como código.

---

### ⚙️ 3.6. Capa de Servicio (`service/`): Las Reglas de Negocio

El repositorio solo sabe guardar y leer; no sabe qué es lo correcto o incorrecto en el negocio. La **Capa de Servicio** es la que manda y toma las decisiones.

#### 📄 [`VulnerabilityService.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/service/VulnerabilityService.java)
- **¿Qué es?:** Una interfaz Java.
- **¿Para qué sirve?:** Define el *menú de opciones* que ofrece nuestra aplicación al mundo exterior (a la API REST, al cliente de escritorio o a la app Android). Dice: *"Yo sé buscar con filtros, sé consultar por CVE, sé crear vulnerabilidades y sé cambiar de estado registrando evidencias"*.

#### 📄 [`VulnerabilityServiceImpl.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/main/java/es/nebrija/vulnfollowup/service/impl/VulnerabilityServiceImpl.java)
- **¿Qué es?:** La clase que implementa la interfaz anterior.
- **¿Qué hace en la práctica?:**
  - Está protegida con `@Transactional(readOnly = true)` para que las lecturas sean súper rápidas y consuman poca memoria.
  - Al crear una vulnerabilidad, comprueba primero si el código CVE ya existe en la base de datos para no duplicarlo (`existsByCveId`).
  - Al cambiar de estado (`updateStatus`), busca la vulnerabilidad y el usuario, crea automáticamente el registro de evidencia en `remediation_comments`, actualiza el estado y lo guarda todo en una única transacción atómica (si algo falla a mitad, se deshace todo automáticamente para no dejar datos corruptos).

---

### 🧪 3.7. Archivos de Pruebas Automatizadas (`src/test/`)

#### 📄 [`backend/src/test/resources/application-test.properties`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/test/resources/application-test.properties)
- **¿Qué es?:** Configuración exclusiva para los tests automáticos.
- **¿Para qué sirve?:** En vez de usar el MySQL del ordenador de desarrollo, levanta una base de datos en la memoria RAM del ordenador (H2). Se crea al pulsar "Ejecutar pruebas" y se destruye al terminar, sin alterar los datos reales de la empresa.

#### 📄 [`VulnerabilityRepositoryTest.java`](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/backend/src/test/java/es/nebrija/vulnfollowup/repository/VulnerabilityRepositoryTest.java)
- **¿Qué es?:** La batería de pruebas de integración con JUnit 5 y AssertJ.
- **¿Qué prueba exactamente?:**
  1. Si buscas con filtro vacío, devuelve todas las vulnerabilidades.
  2. Si filtras por severidad `CRITICA`, solo devuelve la puerta trasera de XZ.
  3. Si filtras por estado `PENDIENTE_REMEDIACION`, solo devuelve Log4Shell.
  4. Si buscas qué fallos afectan a la pasarela de pagos (cruce N:M), saca el fallo correcto.
  5. Si pones notas de 8.0 a 10.0, saca los dos fallos más graves.
  6. Si combinas los 4 filtros a la vez, saca el resultado exacto.
  7. Si pides una combinación imposible (ej. un fallo crítico pero ya resuelto cuando está abierto), devuelve una lista vacía sin dar ningún error.

---

## 🔄 4. Resumen Rápido: ¿Cómo viaja una petición de búsqueda?

Para tu defensa oral o explicación a un compañero, este es el viaje de los datos cuando alguien busca en la pantalla de la aplicación:

```
1. El usuario marca en la pantalla: "Quiero ver vulnerabilidades CRÍTICAS que afecten a la API de Pagos".
                            │
                            ▼
2. El sistema empaqueta esos datos en un objeto:
   VulnerabilityFilter(severity = CRITICA, assetId = 1, status = null, minScore = null, maxScore = null)
                            │
                            ▼
3. Se lo entrega a: VulnerabilityService.searchVulnerabilities(filter)
                            │
                            ▼
4. El servicio llama a: VulnerabilityRepository.findByFilter(filter)
                            │
                            ▼
5. VulnerabilitySpecification lee el filtro:
   - Ve que severity no es null ──> añade condición de severidad
   - Ve que assetId no es null   ──> añade cruce JOIN con activos
   - Ve que status es null       ──> lo ignora
   - Activa DISTINCT para no duplicar filas
                            │
                            ▼
6. Hibernate genera la sentencia SQL segura:
   SELECT DISTINCT v.* FROM vulnerabilities v 
   INNER JOIN vulnerability_assets va ON v.id = va.vulnerability_id 
   INNER JOIN cvss_assessments ca ON v.id = ca.vulnerability_id 
   WHERE ca.severity = ? AND va.asset_id = ?
                            │
                            ▼
7. MySQL ejecuta la consulta y devuelve los resultados limpios a los objetos Java.
```

---
*Documento elaborado para el repositorio oficial **VulnFollowUp** (Proyecto Intermodular 2.º DAM).*
