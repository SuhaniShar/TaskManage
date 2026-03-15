package com.TaskManage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TaskManage.Enum.IssueStatus;

public interface WorkFlowTransactionRepository extends JpaRepository<WorkFlowTransactionRepository, Long >{
   List<WorkFlowTransactionRepository> findByWorkFlowById(Long workFlowId);
   
   List<WorkFlowTransactionRepository> findByWorkFlowIdAndFromStatus(Long workFlowId, IssueStatus fromStatus);
}
