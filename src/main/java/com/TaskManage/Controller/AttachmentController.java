package com.TaskManage.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManage.Entity.Attachment;
import com.TaskManage.Service.AttachmentService;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping(value="/{issueId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Attachment> upload(
            @PathVariable Long issueId,
            @RequestParam("file") MultipartFile file,
            @RequestHeader(value="X-User-Email", required=false) String userOfficialEmail) {

        String author = (userOfficialEmail == null || userOfficialEmail.isEmpty())
                ? "system@gmail"
                : userOfficialEmail;

        Attachment attach = attachmentService.upload(issueId, file, author);

        return ResponseEntity.ok(attach);
    }

    @GetMapping("/{attachmentId}/download")
    public ResponseEntity<Void> download(@PathVariable Long attachmentId) {

        String attachment = attachmentService.download(attachmentId);

        return ResponseEntity
                .status(302)
                .header(HttpHeaders.LOCATION, attachment)
                .build();
    }
}