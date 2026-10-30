package es.nebrija.vulnfollowup.model.entity;

import es.nebrija.vulnfollowup.model.enums.Criticality;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entidad de Proveedor TIC conforme al marco normativo DORA (Reglamento UE 2022/2554).
 */
@Entity
@Table(name = "tic_providers")
public class TicProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "service_provided", nullable = false, length = 150)
    private String serviceProvided;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = false, length = 20)
    private Criticality criticality;

    @Column(name = "contract_ref", length = 50)
    private String contractRef;

    @Column(name = "contract_start_date")
    private LocalDate contractStartDate;

    @Column(name = "contract_review_date", nullable = false)
    private LocalDate contractReviewDate;

    @Column(name = "contact_name", length = 100)
    private String contactName;

    @Column(name = "contact_email", length = 120)
    private String contactEmail;

    @Column(name = "contact_phone", length = 30)
    private String contactPhone;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "provider", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Asset> assets = new ArrayList<>();

    public TicProvider() {
    }

    public TicProvider(String name, String serviceProvided, Criticality criticality, LocalDate contractReviewDate) {
        this.name = name;
        this.serviceProvided = serviceProvided;
        this.criticality = criticality;
        this.contractReviewDate = contractReviewDate;
        this.isActive = true;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.isActive == null) {
            this.isActive = true;
        }
    }

    // Métodos de conveniencia para mantener sincronía bidireccional 1:N
    public void addAsset(Asset asset) {
        assets.add(asset);
        asset.setProvider(this);
    }

    public void removeAsset(Asset asset) {
        assets.remove(asset);
        asset.setProvider(null);
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceProvided() {
        return serviceProvided;
    }

    public void setServiceProvided(String serviceProvided) {
        this.serviceProvided = serviceProvided;
    }

    public Criticality getCriticality() {
        return criticality;
    }

    public void setCriticality(Criticality criticality) {
        this.criticality = criticality;
    }

    public String getContractRef() {
        return contractRef;
    }

    public void setContractRef(String contractRef) {
        this.contractRef = contractRef;
    }

    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public LocalDate getContractReviewDate() {
        return contractReviewDate;
    }

    public void setContractReviewDate(LocalDate contractReviewDate) {
        this.contractReviewDate = contractReviewDate;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicProvider that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "TicProvider{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", serviceProvided='" + serviceProvided + '\'' +
                ", criticality=" + criticality +
                ", contractReviewDate=" + contractReviewDate +
                ", isActive=" + isActive +
                '}';
    }
}
