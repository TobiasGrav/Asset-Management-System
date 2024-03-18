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

    /**
     * returns id
     */
    public int getId() {
        return id;
    }

    /**
     * sets the name of the id field to given value.
     *
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns users with role.
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Sets the value of the users field to given value.
     *
     * @param users users
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * returns name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value fo the name filed to given value.
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
}
