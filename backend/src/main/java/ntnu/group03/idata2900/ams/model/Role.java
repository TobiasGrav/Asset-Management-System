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
@Schema(description = "Roles which users may have.", name = "roles")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the role")
    private int id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "name", nullable = false, unique = true)
    @Schema(description = "Name of the role")
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    @Schema(description = "Users with the given role")
    private Set<User> users = new LinkedHashSet<>();

    /**
     * Empty constructor.
     */
    public Role() {
    }

    /**
     * Sets the value of the name field to given value.
     *
     * @param name name
     */
    public Role(String name) {
        this.name = name;
    }

}
