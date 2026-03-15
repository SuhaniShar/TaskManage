package com.TaskManage.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManage.Storage.StorageService;
import com.TaskManage.Entity.Attachment;
import com.TaskManage.Entity.Issue;
import com.TaskManage.Repository.AttachmentRepository;
import com.TaskManage.Repository.IssueRepository;

@Service
public class AttachmentService {
	
@Autowired
private AttachmentRepository attachRepo;

@Autowired
private StorageService storage;

@Autowired
private IssueRepository issueRepo;



@Transactional
public Attachment upload(Long issueId, MultipartFile file, String uploadBy) {

    // Fetch issue from DB
    Issue issue = issueRepo.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found with ID: " + issueId));

    // Store file in cloud/storage
    String cloudURL = storage.store(file, "issues/" + issueId);

    // Create attachment
    Attachment attach = new Attachment();
    attach.setIssue(issue);
    attach.setFileName(file.getOriginalFilename());
    attach.setContentType(file.getContentType());
    attach.setSizeBytes(file.getSize());
    attach.setStoragePath(cloudURL);
    attach.setUpLoadedBy(uploadBy);

    return attachRepo.save(attach);
}

public String download(Long id) {
	throw new UnsupportedOperationException("User Cloudinary URL directly for downloading files.");
}
}

