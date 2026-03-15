package com.TaskManage.Entity;

import com.TaskManage.Enum.IssueStatus;
import com.TaskManage.Enum.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "workflow_transactions",
    indexes = {
        @Index(
            name = "idx_workflow_from_to",
            columnList = "workFlow_id, from_status, to_status"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkFlowTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workFlow_id", nullable = false)
    private WorkFlow workFlow;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_status", nullable = false)
    private IssueStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_status", nullable = false)
    private IssueStatus toStatus;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "allowed_role")
    private Role allowedRole;
}

