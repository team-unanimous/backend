package com.team.unanimous.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.team.unanimous.dto.requestDto.EmailRequestDto;
import com.team.unanimous.dto.requestDto.NicknameRequestDto;
import com.team.unanimous.dto.requestDto.PasswordRequestDto;
import com.team.unanimous.dto.requestDto.SignupRequestDto;
import com.team.unanimous.exceptionHandler.CustomException;
import com.team.unanimous.exceptionHandler.ErrorCode;
import com.team.unanimous.service.EmailService;
import com.team.unanimous.service.GoogleUserService;
import com.team.unanimous.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final EmailService emailService;

//    private final KakaoUserService kakaoUserService;

    private final GoogleUserService googleUserService;

    //이메일 인증 및 회원가입
    @PostMapping("/api/users/emails")
    public ResponseEntity email(@RequestBody EmailRequestDto emailRequestDto) {
        return userService.email(emailRequestDto);
    }

    @PostMapping("/api/users/signup")
    public ResponseEntity signup(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

//    //비밀번호 수정
//    @PatchMapping("/api/users/password/{userId}")
//    public ResponseEntity password(@PathVariable Long userId, @RequestBody PasswordRequestDto passwordRequestDto) {
//        return userService.password(userId, passwordRequestDto);
//    }

    //닉네임중복체크
    @PostMapping("/api/users/nickname")
    public ResponseEntity nicknameCheck(@RequestBody NicknameRequestDto nicknameRequestDto) {
        return userService.nicknameCheck(nicknameRequestDto);
    }

    // 닉네임 수정
    @PatchMapping("/api/users/nickname/{userId}")
    public ResponseEntity nickname(@PathVariable Long userId, @RequestBody NicknameRequestDto nicknameRequestDto) {
        return userService.nickname(userId, nicknameRequestDto);
    }

//
//    //이메일 인증 요청
//    @PostMapping("/api/users/emails")
//    @ApiOperation(value = "회원 가입시 이메인 인증", notes = "기존사용하고 있는 이메일을 통해 인증")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "성공"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
//            @ApiResponse(code = 500, message = "서버 오류")
//    })
//    public ResponseEntity emailConfirm(@RequestBody @ApiParam(value="이메일정보 정보", required = true) String email) throws Exception {
//        String confirm = emailService.sendSimpleMessage(email);
//        return ResponseEntity.status(200).body(confirm);
//    }
//
//
//    //카카오 로그인
//    @GetMapping("/user/kakao/callback")
//    public ResponseEntity kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
//        try {
//            kakaoUserService.kakaoLogin(code, response);
//        } catch (Exception e) {
//            throw new CustomException(ErrorCode.INVALID_LOGIN_ATTEMPT);
//        }
//        return new ResponseEntity("카카오 사용자로 로그인 처리 되었습니다", HttpStatus.OK);
//    }
    //구글 로그인
    @GetMapping("/login/google/callback")
    public ResponseEntity googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        try {
            System.out.println("시작확인");
            googleUserService.googleLogin(code, response);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_LOGIN_ATTEMPT);
        }
        return new ResponseEntity("구글 사용자로 로그인 처리 되었습니다", HttpStatus.OK);
    }

    //프로필 사진
    @PostMapping("/api/users/signup/{userId}")
    public ResponseEntity signupImage(@RequestParam("profileImage") MultipartFile file, @PathVariable Long userId) throws IOException {
        return userService.signupImage(file, userId);
    }
}