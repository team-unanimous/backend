package com.team.unanimous.email;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    //회원가입시 코드 전송
    @PostMapping("/api/users/emails")
    public ResponseEntity emailSend(@RequestBody EmailRequestDto emailRequestDto) {
        return emailService.emailSend(emailRequestDto);
    }
    // 회원가입시 코드 확인
    @PostMapping("/api/users/emails/code")
    public ResponseEntity emailsCode(@RequestBody EmailCodeRequestDto emailCodeRequestDto) {
        return emailService.emailsCode(emailCodeRequestDto);
    }
    //팀 참가시 UUID 보내기
//    @PostMapping("/api/teams/emails")
//    public ResponseEntity teamEmailSend(@RequestBody TeamEmailRequestDto teamEmailRequestDto) {
//        return emailService.teamEmailSend(teamEmailRequestDto);
//    }
    //팀 참가시 코드 확인
    @PostMapping("/api/teams/emails/code")
    public ResponseEntity teamEmailCode(@RequestBody EmailCodeRequestDto emailCodeRequestDto) {
        return emailService.teamEmailCode(emailCodeRequestDto);
    }
}
