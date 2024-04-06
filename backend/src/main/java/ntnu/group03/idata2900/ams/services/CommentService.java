package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.CommentDto;
import ntnu.group03.idata2900.ams.model.Comment;
import ntnu.group03.idata2900.ams.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAll() {
        List<Comment> comments = new LinkedList<>();
        this.commentRepository.findAll().forEach(comments::add);
        return comments;
    }

    public Optional<Comment> getComment(int id){
        return this.commentRepository.findById(id);
    }

    public Comment createComment(CommentDto commentDto){
        Comment comment = new Comment(commentDto);
        return this.commentRepository.save(comment);
    }

    public void updateComment(Comment comment) {
        this.commentRepository.save(comment);
    }

    public void deleteComment(int id){
        this.commentRepository.deleteById(id);
    }
}
