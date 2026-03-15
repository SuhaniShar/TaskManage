package com.TaskManage.Entity;


import jakarta.persistence.*;

import java.util.Date;

import com.TaskManage.Enum.Role;

import lombok.*;

@Entity
@Table(name="user_auth")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class UserAuth {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long id;
	@Column(nullable=false)
	private String userName;
	
	@Column(unique=true,nullable=false)
	private String userOfficialEmail;
	
	@Column(nullable=false)
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	
    private String resetToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date resetTokenExpiry;
	
	
//	public UserAuth() {}//NoArgsConstructor
//	public UserAuth(Long id,String userName,String userOffialEmail,String password,Role role) {
//		this.id=id;
//		this.userName=userName;
//		this.userOffialEmail=userOffialEmail;
//		this.password=password;
//		this.role=role;
//		
//	}

	
	
	
	

}
