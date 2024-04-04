package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Schema(description = "Datasheet to a given asset.", name = "datasheet")
@Entity
public class Datasheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the datasheet")
    private int id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "name", nullable = false, unique = true)
    @Schema(description = "name of the datasheet")
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "reference_number", nullable = false, unique = true)
    @Schema(description = "reference number of the datasheet")
    private String referenceNumber;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "pdf_url", nullable = false, unique = true)
    @Schema(description = "url to the datasheet pdf")
    private String pdfUrl;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "datasheet")
    @Schema(description = "assets that has the given datasheet")
    private Set<Asset> assets = new LinkedHashSet<>();


    /**
     * Constructor with parameters
     *
     * @param name              datasheet name
     * @param referenceNumber   datasheet referenceNumber
     * @param pdfUrl            datasheet pdfUrl
     */
    public Datasheet(String name, String referenceNumber, String pdfUrl) {
        this.name = name;
        this.referenceNumber = referenceNumber;
        this.pdfUrl = pdfUrl;
    }

    public Datasheet(){

    }

}
