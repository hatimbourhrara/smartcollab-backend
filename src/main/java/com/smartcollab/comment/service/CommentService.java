package com.smartcollab.comment.service;

import com.smartcollab.activity.service.ActivityLogService;
import com.smartcollab.comment.model.Comment;
import com.smartcollab.comment.repository.CommentRepository;
import com.smartcollab.task.model.Task;
import com.smartcollab.task.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final ActivityLogService activityLogService;

    public CommentService(
            CommentRepository commentRepository,
            TaskService taskService,
            ActivityLogService activityLogService
    ) {
        this.commentRepository = commentRepository;
        this.taskService = taskService;
        this.activityLogService = activityLogService;
    }

    public Comment createComment(
            Long taskId,
            String content,
            String userEmail
    ) {

        Task task = taskService.getTaskById(taskId);

        Comment comment = new Comment();

        comment.setContent(content);
        comment.setCreatedBy(userEmail);
        comment.setTask(task);

        Comment savedComment = commentRepository.save(comment);

        activityLogService.logActivity(
                "Added comment to task #" + taskId,
                userEmail
        );

        return savedComment;
    }

    public List<Comment> getTaskComments(Long taskId) {

        Task task = taskService.getTaskById(taskId);

        return commentRepository.findByTask(task);
    }

    public void deleteComment(
            Long commentId,
            String userEmail
    ) {

        Comment comment = commentRepository
                .findByIdAndCreatedBy(commentId, userEmail)
                .orElseThrow(() ->
                        new RuntimeException("Comment not found")
                );

        commentRepository.delete(comment);

        activityLogService.logActivity(
                "Deleted comment #" + commentId,
                userEmail
        );
    }
}