package ntnu.group03.idata2900.ams.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {

    private String comment;
    private LocalDateTime creationDate;

    public CommentDto(String comment, LocalDateTime creationDate) {
        this.comment = comment;
        this.creationDate = creationDate;
    }

    public CommentDto(){

    }
}
