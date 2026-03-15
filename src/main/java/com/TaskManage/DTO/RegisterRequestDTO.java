
package com.TaskManage.DTO;

import com.TaskManage.Enum.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequestDTO {
	
	public String userName;
	public String userOfficialEmail;
	public String password;
	public Role role;
	
	

}

