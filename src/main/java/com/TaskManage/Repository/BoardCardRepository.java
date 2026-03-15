package com.TaskManage.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.TaskManage.Entity.BoardCard;
import com.TaskManage.Entity.Issue;

@Repository


public interface BoardCardRepository extends JpaRepository<BoardCard, Long>{
 
 Optional<BoardCard> findByIssue(Issue issue);
 List<BoardCard> findByBoardIdAndColumnIdOrderByPosition(Long boardId, Long columnId);
 
 
 long countByBoardIdAndColumnId(Long boardId, Long columnId);
}
