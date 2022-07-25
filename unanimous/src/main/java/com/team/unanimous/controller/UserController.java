package com.team.unanimous.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.team.unanimous.dto.requestDto.*;
import com.team.unanimous.security.UserDetailsImpl;
import com.team.unanimous.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";
    private final UserService userService;

    // 회원가입(이메일 코드 없는 버전)
    @PostMapping("/api/users/signup")
    public ResponseEntity signup(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    // 비밀번호 찾기 (변경)
    @PatchMapping("/api/passwordChange")
    public ResponseEntity findPasswordChange(@RequestBody FindPasswordRequestDto findPasswordRequestDto) {
        return userService.findPasswordChange(findPasswordRequestDto);
    }

    //닉네임중복체크
    @PostMapping("/api/users/nickname")
    public ResponseEntity nicknameCheck(@RequestBody NicknameRequestDto nicknameRequestDto) {
        return userService.nicknameCheck(nicknameRequestDto);
    }

    //회원가입, 마이페이지 닉네임 수정
    @PatchMapping("/api/users/nickname/{userId}")
    public ResponseEntity nickname(@PathVariable Long userId,
                                   @RequestBody NicknameRequestDto nicknameRequestDto,
                                   final HttpServletResponse response) {
        return userService.nickname(userId, nicknameRequestDto, response);
    }

    //마이페이지 비밀번호 변경전 확인
    @PostMapping("/api/users/passwordCheck")
    public ResponseEntity passwordCheck(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordCheckRequestDto passwordCheckRequestDto) {
        return userService.passwordCheck(userDetails, passwordCheckRequestDto);
    }

    //마이페이지에서 비밀번호 변경
    @PatchMapping("/api/users/passwordChange")
    public ResponseEntity passwordChange(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto passwordRequestDto) {
        return userService.passwordChange(userDetails, passwordRequestDto);
    }

   // 마이페이지 프로필 사진 변경
    @PostMapping("/api/users/signup/{userId}")
    public ResponseEntity signupImage(@RequestParam("profileImage") MultipartFile file,
                                      @PathVariable Long userId,
                                      final HttpServletResponse response
                                      ) throws IOException {
        return userService.signupImage(file, userId, response);
    }
}