//package com.team.unanimous.RefreshToken;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/user")
//public class AccountController {
//    private final AccountService accountService;
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginDto) {
//        LoginResponseDto responseDto = accountService.login(loginDto.getUsername(), loginDto.getPassword());
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }
//}