package ntnu.group03.idata2900.ams.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.CommentDto;
import ntnu.group03.idata2900.ams.model.Comment;
import ntnu.group03.idata2900.ams.model.ServiceComment;
import ntnu.group03.idata2900.ams.model.ServiceCompleted;
import ntnu.group03.idata2900.ams.model.User;
import ntnu.group03.idata2900.ams.repositories.ServiceCommentRepository;
import ntnu.group03.idata2900.ams.services.CommentService;
import ntnu.group03.idata2900.ams.services.ServiceCompletedService;
import ntnu.group03.idata2900.ams.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
@Tag(name = "Comment API", description = "Endpoints for managing comments")
public class CommentController {

    private final CommentService commentService;

    private final ServiceCompletedService serviceCompletedService;

    private final ServiceCommentRepository serviceCommentRepository;

    private final UserService userService;

    private static final String COMMENT_NOT_FOUND = "Comment not found with id: {}";

    /**
     * Creates a new instance of CommentController.
     *
     * @param commentService commentService
     * @param serviceCompletedService serviceCompletedService
     * @param serviceCommentRepository serviceCommentRepository
     * @param userService userService
     */
    public CommentController(CommentService commentService, ServiceCompletedService serviceCompletedService, ServiceCommentRepository serviceCommentRepository, UserService userService) {
        this.commentService = commentService;
        this.serviceCompletedService = serviceCompletedService;
        this.serviceCommentRepository = serviceCommentRepository;
        this.userService = userService;
    }

    /**
     * Returns list of all comments in database
     *
     * @return List of all comments in database
     */
    @Operation(summary = "Get all comments", description = "Retrieves a list of all comments.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class)))
    @GetMapping("/admin/comments")
    public List<Comment> getAll() {
        return commentService.getAll();
    }

    /**
     * Get a comment from database matching given id if it exists.
     *
     * @param id potential id of a comments
     * @return a ModelAndView containing comments in JSON format
     */
    @Operation(summary = "Get comment by ID", description = "Retrieves a comment based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Comment found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class)))
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @GetMapping("/admin/comments/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable int id) {
        Optional<Comment> comment = this.commentService.getComment(id);
        if (comment.isEmpty()) {
            log.warn(COMMENT_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Comment found with ID: {}", id);
            return new ResponseEntity<>(comment.get(), HttpStatus.OK);
        }
    }

    /**
     * Get a comment from database matching given id if it exists.
     *
     * @param id potential id of a service completed
     * @return a ModelAndView containing comments in JSON format
     */
    @Operation(summary = "Get all comments by service completed ID for user", description = "Retrieves a list of all comments associated with a specific service completed ID for user.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class)))
    @ApiResponse(responseCode = "404", description = "Service completed not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/user/comments/serviceCompleted/{id}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByServiceCompleted(@PathVariable int id) {
        Optional<ServiceCompleted> serviceCompleted = this.serviceCompletedService.getServiceCompleted(id);
        User user = userService.getSessionUser();
        if (serviceCompleted.isEmpty()) {
            log.warn("ServiceCompleted not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Comment> comments = serviceCompletedService.getAllCommentsByServiceCompleted(serviceCompleted.get());
        if (comments.isEmpty()){
            log.warn(COMMENT_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }

        if (!userService.hasAccessToAllServiceCompletedOnSite(user, serviceCompleted.get().getAssetOnSite().getId())){
            log.warn("User with ID {} does not have access to comments", user.getId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        log.info("Found {} comments for ServiceCompleted with ID: {}", comments.size(), id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    /**
     * Get a comment from database matching given id if it exists.
     *
     * @param id potential id of a service completed
     * @return a ModelAndView containing comments in JSON format
     */
    @Operation(summary = "Get all comments by service completed ID for admin", description = "Retrieves a list of all comments associated with a specific service completed ID for admin.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class)))
    @ApiResponse(responseCode = "404", description = "Service completed not found")
    @GetMapping("/admin/comments/serviceCompleted/{id}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByServiceCompletedForAdmin(@PathVariable int id) {
        Optional<ServiceCompleted> serviceCompleted = this.serviceCompletedService.getServiceCompleted(id);
        if (serviceCompleted.isEmpty()) {
            log.warn("ServiceCompleted not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Comment> comments = serviceCompletedService.getAllCommentsByServiceCompleted(serviceCompleted.get());
        if (comments.isEmpty()){
            log.warn(COMMENT_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }

        log.info("Found {} comments for ServiceCompleted with ID: {}", comments.size(), id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


    /**
     * Creates a new comment.
     *
     * @param comment The comment object to be created.
     * @return ResponseEntity containing the created comment and HTTP status code 201 (CREATED).
     */
    @Operation(summary = "Create a new comment", description = "Creates a new comment.")
    @ApiResponse(responseCode = "201", description = "Comment created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input, object invalid")
    @PostMapping("/admin/comments")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto comment) {
        try {
            Comment createdComment = commentService.createComment(comment);
            log.info("Comment created with ID: {}", createdComment.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (Exception e) {
            log.error("Error creating comment", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Creates a new comment and attach it to a ServiceComment.
     *
     * @param comment The comment object to be created.
     * @param id The id of the service completed
     * @return ResponseEntity containing the created comment and HTTP status code 201 (CREATED).
     */
    @Operation(summary = "Create a new comment and attach it to a service comment", description = "Creates a new comment and associates it with a service comment based on the provided service completed ID.")
    @ApiResponse(responseCode = "201", description = "Comment created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input, object invalid")
    @ApiResponse(responseCode = "404", description = "Service completed not found")
    @PostMapping("/technician/comments/serviceCompleted/{id}")
    public ResponseEntity<Comment> createServiceComment(@RequestBody CommentDto comment, @PathVariable int id) {
        try {
            Comment createdComment = commentService.createComment(comment);
            log.info("Comment created with ID: {}", createdComment.getId());
            Optional<ServiceCompleted> serviceCompleted = this.serviceCompletedService.getServiceCompleted(id);
            if (serviceCompleted.isEmpty()){
                log.warn("Service completed not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                ServiceComment serviceComment = new ServiceComment();
                serviceComment.setComment(createdComment);
                serviceComment.setServiceCompleted(serviceCompleted.get());
                serviceCommentRepository.save(serviceComment);
                log.info("Service Completed found with ID: {}, and comment was created successfully", id);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
            }
        } catch (Exception e) {
            log.error("Error creating comment", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    /**
     * Updates an existing comment.
     *
     * @param id             The ID of the comment to be updated.
     * @param updatedComment The updated comment object.
     * @return ResponseEntity containing the updated comment (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the comment with the given ID doesn't exist.
     */
    @Operation(summary = "Update an existing comment", description = "Updates an existing comment based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Comment updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class)))
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @PutMapping("/admin/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable int id, @RequestBody CommentDto updatedComment) {
        Optional<Comment> existingComment = commentService.getComment(id);
        if (existingComment.isEmpty()) {
            log.warn(COMMENT_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Comment commentToUpdate = existingComment.get();
            commentToUpdate.setComment(updatedComment.getComment());
            commentService.updateComment(commentToUpdate);
            log.info("Comment updated with ID: {}", id);
            return new ResponseEntity<>(commentToUpdate, HttpStatus.OK);
        }
    }

    /**
     * Deletes a comment.
     *
     * @param id The ID of the comment to be deleted.
     * @return ResponseEntity with HTTP status code 204 (NO_CONTENT) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the comment with the given ID doesn't exist.
     */
    @Operation(summary = "Delete a comment", description = "Deletes an existing comment based on the provided ID.")
    @ApiResponse(responseCode = "204", description = "Comment deleted")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @DeleteMapping("/admin/comments/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable int id) {
        Optional<Comment> existingComment = commentService.getComment(id);
        if (existingComment.isEmpty()) {
            log.warn(COMMENT_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            commentService.deleteComment(id);
            log.info("Comment deleted with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
