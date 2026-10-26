-- ==============================================================================
-- VulnFollowUp - Script DDL de Creación de la Base de Datos
-- Proyecto Intermodular · 2.º DAM (Curso 2026-2027) · Instituto Nebrija FP
-- Módulo: Acceso a Datos (AD) y Sistemas de Gestión Empresarial (SGE)
-- ==============================================================================

-- 1. Tabla: users (Autenticación y Roles RBAC)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'ANALYST', 'VIEWER')),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 2. Tabla: tic_providers (Proveedores TIC - Cumplimiento DORA / SGE)
CREATE TABLE IF NOT EXISTS tic_providers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    service_provided VARCHAR(150) NOT NULL,
    criticality VARCHAR(20) NOT NULL CHECK (criticality IN ('BAJA', 'MEDIA', 'ALTA', 'CRITICA')),
    contract_ref VARCHAR(50),
    contract_start_date DATE,
    contract_review_date DATE NOT NULL,
    contact_name VARCHAR(100),
    contact_email VARCHAR(120),
    contact_phone VARCHAR(30),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 3. Tabla: assets (Activos Tecnológicos)
CREATE TABLE IF NOT EXISTS assets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    environment VARCHAR(30) NOT NULL CHECK (environment IN ('PRODUCCION', 'PREPRODUCCION', 'DESARROLLO')),
    manager VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVO' CHECK (status IN ('ACTIVO', 'INACTIVO', 'MANTENIMIENTO')),
    provider_id BIGINT,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_assets_provider FOREIGN KEY (provider_id) 
        REFERENCES tic_providers (id) ON DELETE RESTRICT
);

-- 4. Tabla: vulnerabilities (Catálogo de Vulnerabilidades y Ciclo de Remediación)
CREATE TABLE IF NOT EXISTS vulnerabilities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cve_id VARCHAR(50) NOT NULL UNIQUE,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ABIERTA' 
        CHECK (status IN ('ABIERTA', 'PENDIENTE_REMEDIACION', 'RESUELTA', 'CERRADA')),
    reported_date DATE NOT NULL,
    target_resolution_date DATE,
    assigned_to_user_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_vulnerabilities_assigned_user FOREIGN KEY (assigned_to_user_id) 
        REFERENCES users (id) ON DELETE SET NULL
);

-- 5. Tabla Intermedia N:M: vulnerability_assets (Asociación Vulnerabilidad - Activo)
CREATE TABLE IF NOT EXISTS vulnerability_assets (
    vulnerability_id BIGINT NOT NULL,
    asset_id BIGINT NOT NULL,
    detected_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (vulnerability_id, asset_id),
    CONSTRAINT fk_va_vulnerability FOREIGN KEY (vulnerability_id) 
        REFERENCES vulnerabilities (id) ON DELETE CASCADE,
    CONSTRAINT fk_va_asset FOREIGN KEY (asset_id) 
        REFERENCES assets (id) ON DELETE CASCADE
);

-- 6. Tabla: cvss_assessments (Valoraciones CVSS Base v3.1 / v4.0)
CREATE TABLE IF NOT EXISTS cvss_assessments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vulnerability_id BIGINT NOT NULL,
    version VARCHAR(10) NOT NULL CHECK (version IN ('3.1', '4.0')),
    vector_string VARCHAR(150) NOT NULL,
    base_score DECIMAL(3,1) NOT NULL CHECK (base_score >= 0.0 AND base_score <= 10.0),
    severity VARCHAR(20) NOT NULL CHECK (severity IN ('NINGUNA', 'BAJA', 'MEDIA', 'ALTA', 'CRITICA')),
    assessed_by_user_id BIGINT,
    assessment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    notes TEXT,
    CONSTRAINT fk_cvss_vulnerability FOREIGN KEY (vulnerability_id) 
        REFERENCES vulnerabilities (id) ON DELETE CASCADE,
    CONSTRAINT fk_cvss_assessed_by FOREIGN KEY (assessed_by_user_id) 
        REFERENCES users (id) ON DELETE SET NULL
);

-- 7. Tabla: remediation_comments (Evidencias de Remediación y Trazabilidad)
CREATE TABLE IF NOT EXISTS remediation_comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vulnerability_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    comment_text TEXT NOT NULL,
    previous_status VARCHAR(30),
    new_status VARCHAR(30),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comments_vulnerability FOREIGN KEY (vulnerability_id) 
        REFERENCES vulnerabilities (id) ON DELETE CASCADE,
    CONSTRAINT fk_comments_user FOREIGN KEY (user_id) 
        REFERENCES users (id) ON DELETE RESTRICT
);

-- 8. Tabla: audit_logs (Auditoría de Operaciones del Sistema)
CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    action VARCHAR(50) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id VARCHAR(50),
    details TEXT,
    ip_address VARCHAR(45),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_user FOREIGN KEY (user_id) 
        REFERENCES users (id) ON DELETE SET NULL
);

-- Índices para optimización de consultas frecuentes (Dashboard y Filtros)
CREATE INDEX idx_vulnerabilities_status ON vulnerabilities(status);
CREATE INDEX idx_cvss_score_severity ON cvss_assessments(base_score, severity);
CREATE INDEX idx_assets_provider ON assets(provider_id);
CREATE INDEX idx_audit_created ON audit_logs(created_at);
