package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.CommentDto;
import ntnu.group03.idata2900.ams.model.Comment;
import ntnu.group03.idata2900.ams.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Represents service for managing comments.
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * Constructor for CommentService.
     *
     * @param commentRepository commentRepository
     */
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Fetches all Comment entities from the database.
     *
     * @return a list of all Comment.
     */
    public List<Comment> getAll() {
        List<Comment> comments = new LinkedList<>();
        this.commentRepository.findAll().forEach(comments::add);
        return comments;
    }

    /**
     * Fetches a Comment by ID.
     *
     * @param id the ID of the Comment.
     * @return an Optional containing the Comment if found, or an empty Optional if not.
     */
    public Optional<Comment> getComment(int id) {
        return this.commentRepository.findById(id);
    }

    /**
     * Creates a new Comment entity based on the given DTO.
     *
     * @param commentDto the DTO containing data for the new Comment.
     * @return the created Comment.
     */
    public Comment createComment(CommentDto commentDto) {
        Comment comment = new Comment(commentDto);
        return this.commentRepository.save(comment);
    }

    /**
     * Updates an existing Comment.
     *
     * @param comment the Comment to update.
     */
    public void updateComment(Comment comment) {
        this.commentRepository.save(comment);
    }

    /**
     * Deletes a Comment by ID.
     *
     * @param id the ID of the Comment to delete.
     */
    public void deleteComment(int id) {
        this.commentRepository.deleteById(id);
    }
}
