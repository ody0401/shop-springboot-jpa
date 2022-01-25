package com.shop.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 작성해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(max = 15)
    private String userPw;

    @NotBlank(message = "비밀번호를 확인해주세요.")
    @Size(max = 15)
    private String userPwCheck;
}
