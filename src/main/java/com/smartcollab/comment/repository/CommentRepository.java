package com.smartcollab.comment.repository;

import com.smartcollab.comment.model.Comment;
import com.smartcollab.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTask(Task task);
}