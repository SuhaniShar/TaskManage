package com.TaskManage.Service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TaskManage.Enum.Role;
import com.TaskManage.DTO.AuthResponseDTO;
import com.TaskManage.DTO.LoggedRequestDTO;
import com.TaskManage.DTO.LoginRequestDTO;
import com.TaskManage.DTO.RegisterRequestDTO;
import com.TaskManage.Entity.TokenBlockList;
import com.TaskManage.Entity.UserAuth;
import com.TaskManage.Repository.TokenBlockListRepository;
import com.TaskManage.Repository.UserAuthRepository;
import com.TaskManage.Security.JWTUtil;

import io.jsonwebtoken.Claims;

@Service
public class UserAuthService {

    @Autowired
    private UserAuthRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private TokenBlockListRepository tokenBlockRepo;

    @Autowired
    private EmailService emailService;

    // ---------------- REGISTER ----------------
    public void register(RegisterRequestDTO register) {
        if (userRepo.findByUserOfficialEmail(register.getUserOfficialEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        UserAuth user = new UserAuth();
        user.setUserName(register.getUserName());
        user.setUserOfficialEmail(register.getUserOfficialEmail());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setRole(Role.valueOf(register.getRole().name()));

        userRepo.save(user);
    }

    // ---------------- LOGIN ----------------
    public AuthResponseDTO login(LoginRequestDTO login) {
        UserAuth user = userRepo.findByUserOfficialEmail(login.getUserOfficialEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);

        return new AuthResponseDTO(token, "Login successful");
    }

    // ---------------- FORGOT PASSWORD ----------------
    public void forgotPassword(String userOfficialEmail) {
        UserAuth user = userRepo.findByUserOfficialEmail(userOfficialEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(new Date(System.currentTimeMillis() + 10 * 60 * 1000)); // 10 minutes
        userRepo.save(user);

        String resetLink = "http://localhost:5050/api/Authentication/reset_password?token=" + token;
        emailService.sendResetEmail(user.getUserOfficialEmail(), resetLink);
    }

    // ---------------- RESET PASSWORD ----------------
    public void resetPassword(String token, String newPassword) {
        UserAuth user = userRepo.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().before(new Date())) {
            throw new RuntimeException("Token expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepo.save(user);
    }

    // ---------------- LOGOUT ----------------
    public void logout(LoggedRequestDTO loggedOut) {
        Claims claims = jwtUtil.getClaims(loggedOut.getToken());

        TokenBlockList killToken = new TokenBlockList();
        killToken.setToken(loggedOut.getToken());
        killToken.setExpiry(claims.getExpiration());

        tokenBlockRepo.save(killToken);
    }
}