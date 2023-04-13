package com.example.ctsbe.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Integer id;
    @NotBlank(message = "Username is required")
    private String username;
    private String email;
    private String staffName;
    private String roleName;
    private String groupName;
    private Integer groupId;
    private boolean enable;
}
