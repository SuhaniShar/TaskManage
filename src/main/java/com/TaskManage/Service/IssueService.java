package com.TaskManage.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManage.DTO.IssueDTO;
import com.TaskManage.Entity.Issue;
import com.TaskManage.Entity.IssueComment;
import com.TaskManage.Entity.Sprint;
import com.TaskManage.Enum.IssueStatus;
import com.TaskManage.Repository.IssueCommentRepository;
import com.TaskManage.Repository.IssueRepository;
import com.TaskManage.Repository.SprintRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor   // ✅ constructor injection
public class IssueService {

    private final IssueRepository issueRepo;
    private final IssueCommentRepository issueCommentRepo;
    private final SprintRepository sprintRepo;

    private String generateIssueKey(Long id) {
        return "PROJECT_" + id;
    }

    // ---------------- CREATE ISSUE ----------------
    @Transactional
    public IssueDTO createIssue(IssueDTO dto) {

        Issue issue = new Issue();
        issue.setIssueTitle(dto.getIssueTitle());
        issue.setIssueDescription(dto.getIssueDescription());
        issue.setIssueType(dto.getIssueType());
        issue.setPriority(dto.getPriority());
        issue.setIssueStatus(dto.getIssueStatus());
        issue.setAssignedEmail(dto.getAssignedEmail());
        issue.setReporterEmail(dto.getReporterEmail());
        issue.setDueDate(dto.getDueDate());

        // save first → generate ID
        issueRepo.save(issue);

        // now ID exists
        issue.setIssueKey(generateIssueKey(issue.getId()));

        return toDTO(issue);
    }

    // ---------------- ADD COMMENT ----------------
    @Transactional
    public IssueComment addComment(Long issueId, String authorEmail, String body) {

        issueRepo.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        IssueComment comment = new IssueComment();
        comment.setIssueId(issueId);
        comment.setAuthorEmail(authorEmail);
        comment.setBody(body);

        return issueCommentRepo.save(comment);
    }

    // ---------------- GET ISSUE ----------------
    public IssueDTO getById(Long id) {
        Issue issue = issueRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        return toDTO(issue);
    }

    public List<IssueDTO> getByAssignedEmail(String email) {
        return issueRepo.findByAssignedEmail(email)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<IssueDTO> getBySprint(Long sprintId) {
        return issueRepo.findBySprintId(sprintId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ---------------- UPDATE STATUS ----------------
    @Transactional
    public IssueDTO updateIssueStatus(Long id, IssueStatus status, String performedBy) {

        Issue issue = issueRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        issue.setIssueStatus(status);
        issue.setUpdatedAt(LocalDateTime.now());
        issueRepo.save(issue);

        return toDTO(issue);
    }

    // ---------------- SPRINT ----------------
    @Transactional
    public Sprint createSprint(Sprint sprint) {
        return sprintRepo.save(sprint);
    }

    // ---------------- SEARCH ----------------
    public List<IssueDTO> search(Map<String, String> filters) {

        if (filters.containsKey("assignee")) {
            return getByAssignedEmail(filters.get("assignee"));
        }

        if (filters.containsKey("sprint")) {
            Long sprintId = Long.parseLong(filters.get("sprint"));
            return getBySprint(sprintId);
        }

        if (filters.containsKey("status")) {
            IssueStatus status = IssueStatus.valueOf(filters.get("status").toUpperCase());
            return issueRepo.findByIssueStatus(status)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

        return issueRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ---------------- ENTITY → DTO ----------------
    private IssueDTO toDTO(Issue issue) {
        IssueDTO dto = new IssueDTO();

        dto.setId(issue.getId());
        dto.setIssueKey(issue.getIssueKey());
        dto.setIssueTitle(issue.getIssueTitle());
        dto.setIssueDescription(issue.getIssueDescription());
        dto.setIssueType(issue.getIssueType());
        dto.setPriority(issue.getPriority());
        dto.setIssueStatus(issue.getIssueStatus());
        dto.setAssignedEmail(issue.getAssignedEmail());
        dto.setReporterEmail(issue.getReporterEmail());
        dto.setSprintId(issue.getSprintId());
        dto.setEpicId(issue.getEpicId());
        dto.setCreatedAt(issue.getCreatedAt());
        dto.setUpdatedAt(issue.getUpdatedAt());
        dto.setDueDate(issue.getDueDate());

        return dto;
    }
}
