package com.TaskManage.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManage.Entity.Board;

@Repository

public interface BoardRepository extends JpaRepository<Board , Long>{
	Optional<Board>findByProjectKey(String projectKey);
 
}
