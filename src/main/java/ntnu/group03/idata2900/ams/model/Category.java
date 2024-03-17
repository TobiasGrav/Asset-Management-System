package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Schema(description = "Categories which assets may have.", name = "category")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the category")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    @Schema(description = "Name of the category")
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "category")
    @Schema(description = "assets with the given category")
    private Set<Asset> assets = new LinkedHashSet<>();
}
