package com.TaskManage.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class ResetPasswordDTO {

	public String token;
	public String newPassword;

}
