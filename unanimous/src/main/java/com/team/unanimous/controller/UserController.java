package com.team.unanimous.controller;


import com.team.unanimous.dto.requestDto.EmailRequestDto;
import com.team.unanimous.dto.requestDto.NicknameRequestDto;
import com.team.unanimous.dto.requestDto.SignupRequestDto;
import com.team.unanimous.service.EmailService;
import com.team.unanimous.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final EmailService emailService;

    @PostMapping("/api/users/signup")
    public ResponseEntity signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @PostMapping("/api/users/nickname")
    public ResponseEntity nicknameCheck(@Valid @RequestBody NicknameRequestDto nicknameRequestDto) {
        return userService.nicknameCheck(nicknameRequestDto);
    }

    @PostMapping("/emailCheck")
    public ResponseEntity emailCheck(@Valid @RequestBody EmailRequestDto emailRequestDto) {
        return userService.emailCheck(emailRequestDto);
    }

    @PostMapping("/api/users/emails")
    @ApiOperation(value = "회원 가입시 이메인 인증", notes = "기존사용하고 있는 이메일을 통해 인증")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity emailConfirm(
            @RequestBody @ApiParam(value="이메일정보 정보", required = true) String email) throws Exception {
        return ResponseEntity.status(200).body(emailService.sendSimpleMessage(email));
    }
}