package com.TaskManage.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import com.TaskManage.Enum.IssuePriority;
import com.TaskManage.Enum.IssueStatus;
import com.TaskManage.Enum.IssueType;

import lombok.*;

@Entity
@Table(name="issue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Issue {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private Long projectId;       // <-- ye hi service me use ho raha hai
    private Long sprintId;

    @Column(nullable=false)
    private String issueTitle;

    @Column(unique=true,nullable=false)
    private String issueKey;

    @Column(length=2000)
    private String issueDescription;

    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @Enumerated(EnumType.STRING)
    private IssueStatus issueStatus;

    @Enumerated(EnumType.STRING)
    private IssuePriority priority;

    private String assignedEmail;
    private String reporterEmail;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime dueDate;

 
    private Long epicId;
    private Long parentIssueId;
   

    private Integer backLogPosition;

    @OneToMany(mappedBy="issue", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Attachment> attachments;

    @PreUpdate
    public void updateTimestamp(){
        this.updatedAt = LocalDateTime.now();
    }
}