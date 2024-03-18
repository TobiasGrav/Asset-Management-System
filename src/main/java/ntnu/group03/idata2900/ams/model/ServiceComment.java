package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(description = "Comment on service completed.", name = "ServiceComment")
@Entity
public class ServiceComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the service comment")
    private int id;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumns({
            @JoinColumn(name = "serviceCompleted_id", referencedColumnName = "id")
    })
    @Schema(description = "the completed service that is to be commented on")
    private ServiceCompleted serviceCompleted;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumns({
            @JoinColumn(name = "comment_id", referencedColumnName = "id")
    })
    @Schema(description = "the comment to be commented on service completed")
    private Comment comment;
}
