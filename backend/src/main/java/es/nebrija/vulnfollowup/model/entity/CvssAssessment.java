package es.nebrija.vulnfollowup.model.entity;

import es.nebrija.vulnfollowup.model.enums.Severity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad de Valoración CVSS Base (v3.1 o v4.0).
 */
@Entity
@Table(name = "cvss_assessments")
public class CvssAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vulnerability_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cvss_vulnerability"))
    private Vulnerability vulnerability;

    @Column(name = "version", nullable = false, length = 10)
    private String version;

    @Column(name = "vector_string", nullable = false, length = 150)
    private String vectorString;

    @Column(name = "base_score", nullable = false, precision = 3, scale = 1)
    private BigDecimal baseScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 20)
    private Severity severity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessed_by_user_id", foreignKey = @ForeignKey(name = "fk_cvss_assessed_by"))
    private User assessedBy;

    @Column(name = "assessment_date", nullable = false)
    private LocalDateTime assessmentDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    public CvssAssessment() {
    }

    public CvssAssessment(String version, String vectorString, BigDecimal baseScore, Severity severity) {
        this.version = version;
        this.vectorString = vectorString;
        this.baseScore = baseScore;
        this.severity = severity;
    }

    @PrePersist
    protected void onCreate() {
        if (this.assessmentDate == null) {
            this.assessmentDate = LocalDateTime.now();
        }
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vulnerability getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVectorString() {
        return vectorString;
    }

    public void setVectorString(String vectorString) {
        this.vectorString = vectorString;
    }

    public BigDecimal getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(BigDecimal baseScore) {
        this.baseScore = baseScore;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public User getAssessedBy() {
        return assessedBy;
    }

    public void setAssessedBy(User assessedBy) {
        this.assessedBy = assessedBy;
    }

    public LocalDateTime getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDateTime assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CvssAssessment that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "CvssAssessment{" +
                "id=" + id +
                ", version='" + version + '\'' +
                ", vectorString='" + vectorString + '\'' +
                ", baseScore=" + baseScore +
                ", severity=" + severity +
                ", assessmentDate=" + assessmentDate +
                '}';
    }
}
