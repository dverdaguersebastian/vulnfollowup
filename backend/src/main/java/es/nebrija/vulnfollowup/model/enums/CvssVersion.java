package es.nebrija.vulnfollowup.model.enums;

/**
 * Versión de la especificación CVSS (Common Vulnerability Scoring System).
 */
public enum CvssVersion {
    V3_1("3.1"),
    V4_0("4.0");

    private final String versionString;

    CvssVersion(String versionString) {
        this.versionString = versionString;
    }

    public String getVersionString() {
        return versionString;
    }

    public static CvssVersion fromString(String text) {
        for (CvssVersion v : CvssVersion.values()) {
            if (v.versionString.equalsIgnoreCase(text) || v.name().equalsIgnoreCase(text)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Versión CVSS no válida: " + text);
    }
}
