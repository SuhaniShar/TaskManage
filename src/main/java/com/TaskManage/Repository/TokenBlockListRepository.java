package com.TaskManage.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.TaskManage.Entity.TokenBlockList;

public interface TokenBlockListRepository extends JpaRepository<TokenBlockList,Long> {
	boolean existsByToken(String token);

}
