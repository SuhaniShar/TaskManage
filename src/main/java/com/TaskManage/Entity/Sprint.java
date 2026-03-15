package com.TaskManage.Entity;

import java.time.LocalDateTime;
import com.TaskManage.Enum.SprintState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="sprints")
public class Sprint {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private Long projectId;

    @Column(nullable=false)
    private String sprintName;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private SprintState sprinState;

    @Column(length=2000)
    private String sprintDescription;

    private LocalDateTime createdAt = LocalDateTime.now();
}