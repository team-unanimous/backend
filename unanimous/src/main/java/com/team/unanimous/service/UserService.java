package com.team.unanimous.service;

import com.team.unanimous.dto.requestDto.EmailRequestDto;
import com.team.unanimous.dto.requestDto.NicknameRequestDto;
import com.team.unanimous.dto.requestDto.SignupRequestDto;
import com.team.unanimous.exceptionHandler.CustomException;
import com.team.unanimous.exceptionHandler.ErrorCode;
import com.team.unanimous.model.user.User;
import com.team.unanimous.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
// 생성자를 만드는 번거러움을 없앨 수 있다.
@RequiredArgsConstructor
public class UserService {

    private final String AUTH_HEADER = "Authorization";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity signup(SignupRequestDto requestDto) {
        //만약 서버에 닉네임이 이미 존재하는 아이디라면 오류메세지를 전달해준다.
        if (userRepository.findByNickname(requestDto.getNickname()).isPresent()) {
            //오류메세지를 전달해준다.
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
        //이메일 인증 하기

        //user 객체에 requestDto에서 받아온값을 넣는다.
        User user = new User(requestDto);
        //비밀번호를 암호화한다.
        String password = passwordEncoder.encode(requestDto.getPassword());
        //비밀번호를 user 객체에 넣는다.
        user.setPassword(password);
        //user 객체를 저장한다.
        userRepository.save(user);
        //성공적으로 저장했다면 성공적으로 처리된 메세지를 전달해준다.
        return new ResponseEntity("회원가입이 정상적으로 처리되었습니다", HttpStatus.OK);
    }

    public ResponseEntity emailCheck(EmailRequestDto emailRequestDto) {
        //만약 서버에 이메일이 이미 존재하는 아이디라면 오류메세지를 전달해준다.
        if (userRepository.findByUsername(emailRequestDto.getUsername()).isPresent()) {
            //오류메세지를 전달해준다.
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
            //성공적으로 저장했다면 성공적으로 처리된 메세지를 전달해준다.
        }
        return new ResponseEntity("사용가능한 이메일입니다", HttpStatus.OK);
    }

    public ResponseEntity nicknameCheck(NicknameRequestDto nicknameRequestDto) {
        //만약 서버에 이메일이 이미 존재하는 아이디라면 오류메세지를 전달해준다.
        if (userRepository.findByNickname(nicknameRequestDto.getNickname()).isPresent()) {
            //오류메세지를 전달해준다.
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
            //성공적으로 저장했다면 성공적으로 처리된 메세지를 전달해준다.
        }
        return new ResponseEntity("사용가능한 닉네임입니다", HttpStatus.OK);
    }
}