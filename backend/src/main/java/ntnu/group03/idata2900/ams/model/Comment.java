package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Schema(description = "Comment on what needs to be commented on", name = "datasheet")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the comment")
    private int id;

    @Column(name = "comment", nullable = false, unique = true)
    @Schema(description = "comment on what needs to be commented on")
    private String comment;

    @JsonBackReference
    @OneToMany(mappedBy = "comment")
    @Schema(description = "services connected to given comment")
    private Set<ServiceComment> serviceComments = new LinkedHashSet<>();

    public int getId() {
        return this.id;
    }

    public String getComment() {
        return this.comment;
    }

    public Set<ServiceComment> getServiceComments() {
        return this.serviceComments;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setServiceComments(Set<ServiceComment> serviceComments) {
        this.serviceComments = serviceComments;
    }
}
