package com.smartcollab.attachment.service;

import com.smartcollab.activity.service.ActivityLogService;
import com.smartcollab.attachment.model.Attachment;
import com.smartcollab.attachment.repository.AttachmentRepository;
import com.smartcollab.common.exception.ResourceNotFoundException;
import com.smartcollab.task.model.Task;
import com.smartcollab.task.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final TaskService taskService;
    private final ActivityLogService activityLogService;

    public AttachmentService(
            AttachmentRepository attachmentRepository,
            TaskService taskService,
            ActivityLogService activityLogService
    ) {
        this.attachmentRepository = attachmentRepository;
        this.taskService = taskService;
        this.activityLogService = activityLogService;
    }

    public Attachment uploadAttachment(
            Long taskId,
            MultipartFile file,
            String userEmail
    ) throws IOException {

        Task task = taskService.getTaskById(taskId);

        String uploadDir = "uploads";
        File directory = new File(uploadDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = uploadDir + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        file.transferTo(new File(filePath));

        Attachment attachment = new Attachment();

        attachment.setFileName(file.getOriginalFilename());
        attachment.setFilePath(filePath);
        attachment.setFileType(file.getContentType());
        attachment.setUploadedBy(userEmail);
        attachment.setTask(task);

        Attachment savedAttachment = attachmentRepository.save(attachment);

        activityLogService.logActivity(
                "Uploaded attachment: " + savedAttachment.getFileName(),
                userEmail
        );

        return savedAttachment;
    }

    public List<Attachment> getTaskAttachments(Long taskId) {

        Task task = taskService.getTaskById(taskId);

        return attachmentRepository.findByTask(task);
    }

    public Attachment getAttachmentById(Long id) {

        return attachmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attachment not found")
                );
    }
}