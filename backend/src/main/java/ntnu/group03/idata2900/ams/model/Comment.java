package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.dto.CommentDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Schema(description = "Comment on what needs to be commented on", name = "comment")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the comment")
    private int id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "comment", nullable = false, unique = false)
    @Schema(description = "comment on what needs to be commented on")
    private String comment;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "creation_date", nullable = false, unique = false)
    @Schema(description = "the date comment was created")
    private LocalDateTime creationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "comment")
    @Schema(description = "services connected to given comment")
    private Set<ServiceComment> serviceComments = new LinkedHashSet<>();

    public Comment (){

    }

    public Comment(String comment, LocalDateTime creationDate) {
        this.comment = comment;
        this.creationDate = creationDate;
    }

    public Comment(CommentDto commentDto) {
        this.comment = commentDto.getComment();
        this.creationDate = LocalDateTime.now();
    }
}
