package com.team.unanimous.dto.requestDto;

import lombok.Getter;

import javax.validation.constraints.Email;


@Getter
public class EmailRequestDto {
    @Email
    private String username;
}
