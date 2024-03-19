package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Schema(description = "Datasheet to a given asset.", name = "datasheet")
@Entity
public class Datasheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the datasheet")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    @Schema(description = "name of the datasheet")
    private String name;
    @Column(name = "reference_number", nullable = false, unique = true)
    @Schema(description = "reference number of the datasheet")
    private String referenceNumber;
    @Column(name = "pdf_url", nullable = false, unique = true)
    @Schema(description = "url to the datasheet pdf")
    private String pdfUrl;

    @JsonBackReference
    @OneToMany(mappedBy = "datasheet")
    @Schema(description = "assets that has the given datasheet")
    private Set<Asset> assets = new LinkedHashSet<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getReferenceNumber() {
        return this.referenceNumber;
    }

    public String getPdfUrl() {
        return this.pdfUrl;
    }

    public Set<Asset> getAssets() {
        return this.assets;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }
}
