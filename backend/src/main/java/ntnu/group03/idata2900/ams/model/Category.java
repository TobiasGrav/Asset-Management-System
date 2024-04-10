package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.dto.CategoryDto;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Schema(description = "Categories which assets may have.", name = "category")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the category")
    private int id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "name", nullable = false, unique = true)
    @Schema(description = "Name of the category")
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "category")
    @Schema(description = "assets with the given category")
    private Set<Asset> assets = new LinkedHashSet<>();

    /**
     * Constructor with parameters
     *
     * @param name category name
     */
    public Category(String name) {
        this.name = name;
    }

    public Category(CategoryDto categoryDto){
        this.name = categoryDto.getName();
    }

    public Category(){

    }

}
