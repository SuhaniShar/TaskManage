package com.TaskManage.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TaskManage.Entity.WorkFlow;
import com.TaskManage.Entity.WorkFlowTransaction;
import com.TaskManage.Repository.WorkFlowRepository;
import com.TaskManage.Repository.WorkFlowTransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkFlowService {

    private final WorkFlowRepository workFlowRepo;
    private final WorkFlowTransactionRepository transactionRepo;

    // ---------------- CREATE WORKFLOW ----------------
    @Transactional
    public WorkFlow createWorkFlow(WorkFlow wf) {
        if (wf.getTransactions() != null) {
            for (WorkFlowTransaction t : wf.getTransactions()) {
                t.setWorkFlow(wf);
            }
        }
        return workFlowRepo.save(wf);
    }

    // ---------------- UPDATE WORKFLOW ----------------
    @Transactional
    public WorkFlow updateWorkFlow(Long id, WorkFlow updated) {

        WorkFlow wf = workFlowRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Workflow not found with id " + id));

        wf.setName(updated.getName());
        wf.setDescription(updated.getDescription());

        // Clear old transactions
        if (wf.getTransactions() != null) {
            wf.getTransactions().clear();
        }

        // Add new transactions
        if (updated.getTransactions() != null) {
            for (WorkFlowTransaction t : updated.getTransactions()) {
                t.setWorkFlow(wf);
                wf.getTransactions().add(t);
            }
        }

        return workFlowRepo.save(wf);
    }

    // ---------------- GET BY ID ----------------
    public WorkFlow getById(Long id) {
        return workFlowRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Workflow not found with id " + id));
    }

    // ---------------- GET BY NAME ----------------
    public WorkFlow getWorkFlowByName(String name) {
        return workFlowRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("Workflow not found with name " + name));
    }

    // ---------------- GET ALL ----------------
    public List<WorkFlow> getAllWorkFlow() {
        return workFlowRepo.findAll();
    }

    // ---------------- DELETE ----------------
    public void deleteWorkFlow(Long id) {
        if (!workFlowRepo.existsById(id)) {
            throw new RuntimeException("Workflow not found with id " + id);
        }
        workFlowRepo.deleteById(id);
    }
}