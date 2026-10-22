# VulnFollowUp - Vulnerability Management & Prioritization Platform

> **Final Capstone Project (Proyecto Intermodular)**  
> **Course:** 2nd Year, Higher Technician in Multiplatform Application Development (DAM)  
> **Academic Year:** 2026-2027  
> **Institution:** Instituto Nebrija de Formación Profesional  

---

## 📖 Overview
**VulnFollowUp** is a centralized defensive security and vulnerability tracking platform designed in compliance with the **EU Regulation 2022/2554 (DORA - Digital Operational Resilience Act)** for financial entities and ICT third-party service providers.

It enables organizations to register IT assets, ingest and manage known vulnerabilities (CVEs and internal identifiers), evaluate technical severity using **CVSS v3.1 / v4.0 Base scores**, prioritize mitigation workflows, manage ICT providers, and generate audit-ready evidence reports.

---

## 🗂️ Repository Structure

The repository follows the modular structure specified in Section 12.3 of the project guidelines:

```text
vulnfollowup/
├── backend/                  # REST API & Business Logic (PSP, AD, SGE)
├── desktop-client/           # Desktop / Web SPA Client (DI)
├── android-app/              # Android Mobile Application (PMDM)
├── database/                 # SQL Schemas, Scripts & Migrations (AD)
├── data/                     # Synthetic & Public Seed Datasets (AD, SGE)
├── docs/                     # Documentation & Transversal Modules
│   ├── api/                  # REST API Documentation (OpenAPI/Swagger)
│   ├── english/              # Technical Glossary, Executive Summary, Demo Script
│   ├── ipe/                  # Lean Canvas, Budget, Meeting Minutes & Portfolios
│   ├── sge/                  # ICT Third-Party Provider Management & BPMN
│   ├── digitalizacion/       # Digital Transformation Report & Data Flow Maps
│   └── sostenibilidad/       # Sustainability Report, ODS Analysis & Green Practices
├── .gitignore                # Multiplatform ignore definitions
├── BITACORA.md               # Activity Log & Development History (Spanish)
└── README.md                 # Main English documentation
```

---

## 📓 Development Activity Log
For a step-by-step audit log of all technical decisions, implemented features, and changes made during development, refer to [BITACORA.md](file:///c:/Tareas%20nebrija/Trabajo%20intermodular/vulnfollowup/BITACORA.md).

