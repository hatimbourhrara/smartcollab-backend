package com.smartcollab.comment.service;

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

    public CommentService(
            CommentRepository commentRepository,
            TaskService taskService
    ) {
        this.commentRepository = commentRepository;
        this.taskService = taskService;
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

        return commentRepository.save(comment);
    }

    public List<Comment> getTaskComments(Long taskId) {

        Task task = taskService.getTaskById(taskId);

        return commentRepository.findByTask(task);
    }
}