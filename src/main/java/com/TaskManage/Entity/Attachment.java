package com.TaskManage.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String contentType;
    private Long sizeBytes;
    private String storagePath;
    private Long cloudId;
    private String upLoadedBy;

    // 🔹 Correct ManyToOne mapping
    @ManyToOne
    @JoinColumn(name = "issue_id") // foreign key column
    private Issue issue; // <-- type should be Issue, name can be 'issue'

    // ✅ Getters & Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }
    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public String getStoragePath() {
        return storagePath;
    }
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public Long getCloudId() {
        return cloudId;
    }
    public void setCloudId(Long cloudId) {
        this.cloudId = cloudId;
    }

    public String getUpLoadedBy() {
        return upLoadedBy;
    }
    public void setUpLoadedBy(String upLoadedBy) {
        this.upLoadedBy = upLoadedBy;
    }

    public Issue getIssue() {
        return issue;
    }
    public void setIssue(Issue issue) {
        this.issue = issue;
    }
}