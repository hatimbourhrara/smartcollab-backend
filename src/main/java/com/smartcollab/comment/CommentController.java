package com.smartcollab.comment;

import com.smartcollab.comment.dto.CommentResponse;
import com.smartcollab.comment.dto.CreateCommentRequest;
import com.smartcollab.comment.model.Comment;
import com.smartcollab.comment.service.CommentService;
import com.smartcollab.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final JwtService jwtService;

    public CommentController(
            CommentService commentService,
            JwtService jwtService
    ) {
        this.commentService = commentService;
        this.jwtService = jwtService;
    }

    @PostMapping("/api/tasks/{taskId}/comments")
    public CommentResponse createComment(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateCommentRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        Comment comment = commentService.createComment(
                taskId,
                request.getContent(),
                userEmail
        );

        return new CommentResponse(comment);
    }

    @GetMapping("/api/tasks/{taskId}/comments")
    public List<CommentResponse> getTaskComments(
            @PathVariable Long taskId
    ) {

        return commentService.getTaskComments(taskId)
                .stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
    @DeleteMapping("/api/comments/{id}")
public void deleteComment(
        @PathVariable Long id,
        @RequestHeader("Authorization") String authHeader
) {

    String token = authHeader.replace("Bearer ", "");
    String userEmail = jwtService.extractEmail(token);

    commentService.deleteComment(
            id,
            userEmail
    );
}
}