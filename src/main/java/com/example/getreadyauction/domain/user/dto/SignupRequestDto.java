package com.example.getreadyauction.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    @NotBlank(message = "id는 필수 값입니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "유저명은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성해주세요.")
    @Size(min = 4, max = 10)
    private String username;

    @NotBlank(message = "password는 필수 값입니다.")
    @Pattern(regexp = "^[a-zA-Z\\p{Punct}0-9]*$", message = "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9),특수문자로 구성해주세요.")
    @Size(min = 8, max = 15)
    private String password;

}