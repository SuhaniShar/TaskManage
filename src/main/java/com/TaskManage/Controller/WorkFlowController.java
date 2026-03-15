package com.TaskManage.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TaskManage.Entity.WorkFlow;
import com.TaskManage.Service.WorkFlowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
public class WorkFlowController {

    private final WorkFlowService workFlowService;

    // CREATE
    @PostMapping
    public ResponseEntity<WorkFlow> createWorkFlow(
            @RequestBody WorkFlow workFlow) {

        WorkFlow saved = workFlowService.createWorkFlow(workFlow);
        return ResponseEntity.ok(saved);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<WorkFlow> updateWorkFlow(
            @PathVariable Long id,
            @RequestBody WorkFlow wf) {

        WorkFlow updated = workFlowService.updateWorkFlow(id, wf);
        return ResponseEntity.ok(updated);
    }

    // GET BY ID
    @GetMapping("/id/{id}")
    public ResponseEntity<WorkFlow> getWorkFlowById(@PathVariable Long id) {
        return ResponseEntity.ok(workFlowService.getById(id));
    }

    // GET BY NAME
    @GetMapping("/name/{name}")
    public ResponseEntity<WorkFlow> getWorkFlowByName(@PathVariable String name) {
        return ResponseEntity.ok(workFlowService.getWorkFlowByName(name));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<WorkFlow>> getAllWorkFlow() {
        return ResponseEntity.ok(workFlowService.getAllWorkFlow());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        workFlowService.deleteWorkFlow(id);
        return ResponseEntity.ok("Workflow deleted successfully");
    }
}
