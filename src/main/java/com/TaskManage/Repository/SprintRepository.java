
package com.TaskManage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManage.Enum.SprintState;
import com.TaskManage.Entity.Sprint;


@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
    List<Sprint> findBySprintState(SprintState sprintState); // field name 'state' ka match
}
