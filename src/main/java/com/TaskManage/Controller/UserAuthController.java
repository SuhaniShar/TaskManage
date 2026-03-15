package com.TaskManage.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManage.DTO.AuthResponseDTO;
import com.TaskManage.DTO.ForgotPasswordDTO;
import com.TaskManage.DTO.LoggedRequestDTO;
import com.TaskManage.DTO.LoginRequestDTO;
import com.TaskManage.DTO.RegisterRequestDTO;
import com.TaskManage.DTO.ResetPasswordDTO;
import com.TaskManage.Service.UserAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/Authentication")
@RequiredArgsConstructor
public class UserAuthController {
	
	@Autowired
	private UserAuthService userService;
	
	@PostMapping("/register")
	public ResponseEntity<String>register(@RequestBody RegisterRequestDTO register){
		userService.register(register);
		return ResponseEntity.ok("Register successful");
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO>login(@RequestBody LoginRequestDTO login){
		return ResponseEntity.ok(userService.login(login));
	}
	
	@PostMapping("/forgot_password")
	public ResponseEntity<String>forgotPassword(@RequestBody ForgotPasswordDTO forgotpassword){
		userService.forgotPassword(forgotpassword.getUserOfficialEmail());
		return ResponseEntity.ok("Reset password Email sent on your Email");
	}
	
	@PostMapping("/reset_password")
	public ResponseEntity<String>resetPassword(@RequestBody ResetPasswordDTO resetpassword){
		userService.resetPassword(resetpassword.token, resetpassword.newPassword);
		return ResponseEntity.ok("Password reset Successful");
	}
	
	
	@PostMapping("/loggedOut")
	public ResponseEntity<String>loggedOut(@RequestBody LoggedRequestDTO loggedOut,@RequestHeader("Authorization") String authHeader){
		String token = authHeader.substring(7);
		
		userService.logout(loggedOut);
		
		return ResponseEntity.ok("Logged out Successful");
	}

}
