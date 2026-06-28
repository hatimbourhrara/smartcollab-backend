package com.smartcollab.attachment;

import com.smartcollab.attachment.dto.AttachmentResponse;
import com.smartcollab.attachment.model.Attachment;
import com.smartcollab.attachment.service.AttachmentService;
import com.smartcollab.security.JwtService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final JwtService jwtService;

    public AttachmentController(
            AttachmentService attachmentService,
            JwtService jwtService
    ) {
        this.attachmentService = attachmentService;
        this.jwtService = jwtService;
    }

    @PostMapping(
            value = "/tasks/{taskId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public AttachmentResponse uploadAttachment(
            @PathVariable Long taskId,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Authorization") String authHeader
    ) throws IOException {

        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        Attachment attachment = attachmentService.uploadAttachment(
                taskId,
                file,
                userEmail
        );

        return new AttachmentResponse(attachment);
    }

    @GetMapping("/tasks/{taskId}")
    public List<AttachmentResponse> getTaskAttachments(
            @PathVariable Long taskId
    ) {

        return attachmentService.getTaskAttachments(taskId)
                .stream()
                .map(AttachmentResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadAttachment(
            @PathVariable Long id
    ) throws MalformedURLException {

        Attachment attachment = attachmentService.getAttachmentById(id);
        Path filePath = attachmentService.getAttachmentPath(id);

        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName() + "\""
                )
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .body(resource);
    }
}