package com.example.server.Object.User.tranfer;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {
    private final String userCode;
    private final String userEmail;
    private final String userId;
    private final String userPwd;
    private final String userName;
    private final LocalDate userBirth;
    private final String userPhoneNumber;
}
