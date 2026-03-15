package com.TaskManage.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.TaskManage.Entity.Issue;
import com.TaskManage.Service.BackLogService;

@RestController
@RequestMapping("/api/back_log")
@RequiredArgsConstructor
public class BackLogController {

    private final BackLogService backLogService;

    @GetMapping("/{projectId}")
    public ResponseEntity<List<Issue>> getBackLog(@PathVariable Long projectId) {
        return ResponseEntity.ok(backLogService.getBackLog(projectId));
    }

    @PostMapping("/{projectId}/record")
    public ResponseEntity<String> record(
            @PathVariable Long projectId,
            @RequestBody List<Long> orderedIssueIds) {

        backLogService.recordBackLog(projectId, orderedIssueIds);
        return ResponseEntity.ok("BackLog recorded");
    }

    @PutMapping("/add-to-sprint/{issueId}/{sprintId}")
    public ResponseEntity<Issue> addIssueToSprint(
            @PathVariable Long issueId,
            @PathVariable Long sprintId) {

        return ResponseEntity.ok(backLogService.addIssueToSprint(issueId, sprintId));
    }

    @GetMapping("/{projectId}/hierarchy")
    public ResponseEntity<Map<String, Object>> getHierarchy(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(backLogService.getBackLogHierarchy(projectId));
    }
}