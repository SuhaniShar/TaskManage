package com.TaskManage.DTO;

import java.time.LocalDateTime;

import com.TaskManage.Enum.IssuePriority;
import com.TaskManage.Enum.IssueStatus;
import com.TaskManage.Enum.IssueType;

public class IssueDTO {

    private Long id;
    private String issueKey;
    private String issueTitle;
    private String issueDescription;
    
    private IssueType issueType;
    private IssuePriority priority;
    private IssueStatus issueStatus;
    
    private String assignedEmail;
    private String reporterEmail;
    
    private Long sprintId;
    private Long epicId;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;

    // ✅ GETTERS & SETTERS

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getIssueKey() {
        return issueKey;
    }
    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getIssueTitle() {
        return issueTitle;
    }
    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueDescription() {
        return issueDescription;
    }
    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public IssueType getIssueType() {
        return issueType;
    }
    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public IssuePriority getPriority() {
        return priority;
    }
    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }
    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getAssignedEmail() {
        return assignedEmail;
    }
    public void setAssignedEmail(String assignedEmail) {
        this.assignedEmail = assignedEmail;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }
    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }

    public Long getSprintId() {
        return sprintId;
    }
    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Long getEpicId() {
        return epicId;
    }
    public void setEpicId(Long epicId) {
        this.epicId = epicId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}

