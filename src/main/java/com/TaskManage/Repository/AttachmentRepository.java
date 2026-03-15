package com.TaskManage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TaskManage.Entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long >{
	List<Attachment>findByIssueId(Long issueId);

}
