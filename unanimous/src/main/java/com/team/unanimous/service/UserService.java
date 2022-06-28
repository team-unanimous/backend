package com.team.unanimous.service;

import com.team.unanimous.dto.requestDto.EmailRequestDto;
import com.team.unanimous.dto.requestDto.NicknameRequestDto;
import com.team.unanimous.dto.requestDto.SignupRequestDto;
import com.team.unanimous.exceptionHandler.CustomException;
import com.team.unanimous.exceptionHandler.ErrorCode;
import com.team.unanimous.model.User;
import com.team.unanimous.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
// 생성자를 만드는 번거러움을 없앨 수 있다.
@RequiredArgsConstructor
public class UserService {

    private final String AUTH_HEADER = "Authorization";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity signup(SignupRequestDto requestDto) {
        String pattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^*+=-]).{4,20}$";
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String nickname = requestDto.getNickname();
        String usernamePattern = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        if(username.equals("")) {
            throw new CustomException(ErrorCode.EMPTY_USERNAME);
        } else if(!Pattern.matches(usernamePattern, username)) {
            throw new CustomException(ErrorCode.USERNAME_WRONG);
        } else if(userRepository.findByUsername(username).isPresent()){
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        //닉네임 정규식에 맞지 않는 경우 오류메시지를 전달해준다.
        if(nickname.equals("")) {
            throw new CustomException(ErrorCode.EMPTY_NICKNAME);
        } else if(nickname.length() < 2 || nickname.length() > 15) {
            throw new CustomException(ErrorCode.NICKNAME_LEGNTH);
        } else if (userRepository.findByNickname(requestDto.getNickname()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        // 비밀번호 정규식에 맞지 않는 경우 오류메세지를 전달해준다.
        if(requestDto.getPassword().equals("")){
            throw new CustomException(ErrorCode.EMPTY_PASSWORD);
        }else if( 4 > password.length() || 20 < password.length()){
            throw new CustomException(ErrorCode.PASSWORD_LEGNTH);
        }else if(!Pattern.matches(pattern,password)){
            throw new CustomException(ErrorCode.PASSWORD_WRONG);
        }

        //user 객체에 requestDto에서 받아온값을 넣는다.
        User user = new User(requestDto);
        //비밀번호를 암호화한다.
        password = passwordEncoder.encode(requestDto.getPassword());
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