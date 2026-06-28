package com.smartcollab.attachment.repository;

import com.smartcollab.attachment.model.Attachment;
import com.smartcollab.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findByTask(Task task);
}