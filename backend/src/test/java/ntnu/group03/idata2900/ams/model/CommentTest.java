package ntnu.group03.idata2900.ams.model;

import ntnu.group03.idata2900.ams.dto.CommentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    private Comment comment;
    private CommentDto commentDto;

    @BeforeEach
    public void setUp() {
        LocalDateTime testCreationDate = LocalDateTime.now();
        comment = new Comment("testComment", testCreationDate);
        commentDto = new CommentDto("testComment", testCreationDate);
    }

    @Test
    void testCommentConstructorWithParameters() {
        assertEquals("testComment", comment.getComment());
        assertEquals(LocalDateTime.now().getYear(), comment.getCreationDate().getYear());
    }

    @Test
    void testCommentConstructorWithDto() {
        Comment newComment = new Comment(commentDto);

        assertEquals("testComment", newComment.getComment());
        assertEquals(LocalDateTime.now().getYear(), newComment.getCreationDate().getYear());
    }

    @Test
    void testCommentDefaultConstructor() {
        Comment defaultComment = new Comment();
        assertNull(defaultComment.getComment());
        assertNull(defaultComment.getCreationDate());
    }

    @Test
    void testCommentSettersAndGetters() {
        comment.setComment("newTestComment");
        assertEquals("newTestComment", comment.getComment());

        LocalDateTime newTestCreationDate = LocalDateTime.now().plusDays(1);
        comment.setCreationDate(newTestCreationDate);
        assertEquals(newTestCreationDate, comment.getCreationDate());

        Set<ServiceComment> serviceComments = new LinkedHashSet<>();
        comment.setServiceComments(serviceComments);
        assertEquals(serviceComments, comment.getServiceComments());
    }

    @Test
    void testCommentDtoConstructorWithParameters() {
        assertEquals("testComment", commentDto.getComment());
        assertEquals(LocalDateTime.now().getYear(), commentDto.getCreationDate().getYear());
    }

    @Test
    void testCommentDtoDefaultConstructor() {
        CommentDto defaultCommentDto = new CommentDto();
        assertNull(defaultCommentDto.getComment());
        assertNull(defaultCommentDto.getCreationDate());
    }

    @Test
    void testCommentDtoSettersAndGetters() {
        commentDto.setComment("newTestComment");
        assertEquals("newTestComment", commentDto.getComment());

        LocalDateTime newTestCreationDate = LocalDateTime.now().plusDays(1);
        commentDto.setCreationDate(newTestCreationDate);
        assertEquals(newTestCreationDate, commentDto.getCreationDate());
    }
}
