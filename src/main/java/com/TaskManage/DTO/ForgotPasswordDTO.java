package com.TaskManage.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordDTO {
    private String userOfficialEmail;  // <-- use private + Lombok @Data
}

