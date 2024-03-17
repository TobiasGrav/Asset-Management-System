package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Schema(description = "Roles which users may have.", name = "roles")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the role")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    @Schema(description = "Name of the role")
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    @Schema(description = "Users with the given role")
    private Set<User> users = new LinkedHashSet<>();
}
