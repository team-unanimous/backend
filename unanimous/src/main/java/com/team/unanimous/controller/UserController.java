package com.team.unanimous.controller;


import com.team.unanimous.dto.requestDto.*;
import com.team.unanimous.security.UserDetailsImpl;
import com.team.unanimous.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    private final EmailService emailService;
////    private final MailService mailService;
//    private final GoogleUserService googleUserService;


//    //이메일 인증 및 회원가입
//    @PostMapping("/api/users/emails")
//    public ResponseEntity mailSend(@RequestBody EmailRequestDto emailRequestDto) {
//        return emailService.mailSend(emailRequestDto);
//    }

//    //이메일 인증 코드 확인
//    @PostMapping("/api/users/emails/code")
//    public ResponseEntity emailCode(@RequestBody EmailCodeRequestDto emailCodeRequestDto) {
//        return userService.emailCode(emailCodeRequestDto);
//    }

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

    //비밀번호 변경전 확인
    @PostMapping("/api/users/passwordCheck")
    public ResponseEntity passwordCheck(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordCheckRequestDto passwordCheckRequestDto) {
        return userService.passwordCheck(userDetails, passwordCheckRequestDto);
    }

    //비밀번호 변경전 확인
    @PostMapping("/api/users/passwordChange")
    public ResponseEntity passwordChange(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto passwordRequestDto) {
        return userService.passwordChange(userDetails, passwordRequestDto);
    }
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
//    //구글 로그인
//    @GetMapping("/login/google/callback")
//    public ResponseEntity googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
//        try {
//            System.out.println("시작확인");
//            googleUserService.googleLogin(code, response);
//        } catch (Exception e) {
//            throw new CustomException(ErrorCode.INVALID_LOGIN_ATTEMPT);
//        }
//        return new ResponseEntity("구글 사용자로 로그인 처리 되었습니다", HttpStatus.OK);
//    }

    //프로필 사진
    @PostMapping("/api/users/signup/{userId}")
    public ResponseEntity signupImage(@RequestParam("profileImage") MultipartFile file, @PathVariable Long userId) throws IOException {
        return userService.signupImage(file, userId);
    }
}