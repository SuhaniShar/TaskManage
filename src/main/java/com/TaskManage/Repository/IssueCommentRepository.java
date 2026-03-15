package com.TaskManage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManage.Entity.IssueComment;

@Repository
public interface IssueCommentRepository extends JpaRepository<IssueComment,Long> {
	List<IssueComment>findByIssueOrderByCreatedAt(Long issueId);

}
