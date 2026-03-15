package com.TaskManage.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TaskManage.DTO.IssueDTO;

import com.TaskManage.Entity.IssueComment;

import com.TaskManage.Enum.IssueStatus;
import com.TaskManage.Service.IssueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    // Create Issue
    @PostMapping("/create")
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueDTO issue) {
        return ResponseEntity.ok(issueService.createIssue(issue));
    }

    // Get Issue by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<IssueDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.getById(id));
    }

    // Get Issues by Assigned Email
    @GetMapping("/email/{email}")
    public ResponseEntity<List<IssueDTO>> getByEmail(@PathVariable("email") String userOfficialEmail) {
        return ResponseEntity.ok(issueService.getByAssignedEmail(userOfficialEmail));
    }

    // Add Comment to Issue
    @PostMapping("/{id}/comment")
    public ResponseEntity<IssueComment> addComment(
            @PathVariable Long id,
            @RequestParam String author,
            @RequestParam String body) {

        return ResponseEntity.ok(issueService.addComment(id, author, body));
    }

    // Get Issues by Sprint
    @GetMapping("/sprint/{sprintId}")
    public ResponseEntity<List<IssueDTO>> getBySprint(@PathVariable Long sprintId) {
        return ResponseEntity.ok(issueService.getBySprint(sprintId));
    }

    // Update Issue Status
    @PutMapping("/{id}/status")
    public ResponseEntity<IssueDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam IssueStatus status,
            @RequestParam String performedBy) {

        return ResponseEntity.ok(issueService.updateIssueStatus(id, status, performedBy));
    }

    // Search Issues
    @GetMapping("/search")
    public ResponseEntity<List<IssueDTO>> search(@RequestParam Map<String, String> filter) {
        return ResponseEntity.ok(issueService.search(filter));
    }
}
