package com.team.unanimous.service;

import com.team.unanimous.dto.requestDto.NicknameRequestDto;
import com.team.unanimous.dto.requestDto.SignupRequestDto;
import com.team.unanimous.dto.responseDto.SignupResponseDto;
import com.team.unanimous.exceptionHandler.CustomException;
import com.team.unanimous.exceptionHandler.ErrorCode;
import com.team.unanimous.model.user.User;
import com.team.unanimous.repository.user.UserRepository;
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

    // 회원가입
    public ResponseEntity signup(SignupRequestDto requestDto) {
        String pattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^*+=-]).{6,12}$";
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();
        String usernamePattern = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

        // 아아디 정규식 맞지않는 경우 오류메세지를 전달해준다.
        if(username.equals("")) {
            throw new CustomException(ErrorCode.EMPTY_USERNAME);
        } else if(!Pattern.matches(usernamePattern, username)) {
            throw new CustomException(ErrorCode.USERNAME_WRONG);
        } else if(userRepository.findByUsername(username).isPresent()){
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 비밀번호 정규식에 맞지 않는 경우 오류메세지를 전달해준다.
        if(requestDto.getPassword().equals("")){
            throw new CustomException(ErrorCode.EMPTY_PASSWORD);
        }else if( 6 > password.length() || 12 < password.length()){
            throw new CustomException(ErrorCode.PASSWORD_LEGNTH);
        }else if(!Pattern.matches(pattern,password)){
            throw new CustomException(ErrorCode.PASSWORD_WRONG);
        } else if(!requestDto.getPassword().equals(passwordCheck)){
            throw new CustomException(ErrorCode.PASSWORD_CHECK);
        }

        //user 객체에 requestDto에서 받아온값을 넣는다.
        User user = new User(requestDto);
        //비밀번호를 암호화한다.
        password = passwordEncoder.encode(requestDto.getPassword());
        //비밀번호를 user 객체에 넣는다.
        user.setPassword(password);
        //user 객체를 저장한다.
        userRepository.save(user);
        SignupResponseDto signupResponseDto = new SignupResponseDto(user,"회원가입 성공");
        //성공적으로 저장했다면 성공적으로 처리된 메세지를 전달해준다.
        return new ResponseEntity(signupResponseDto, HttpStatus.OK);
    }

    // 닉네임 중복 체크
    public ResponseEntity nicknameCheck(NicknameRequestDto nicknameRequestDto) {
        String nickname = nicknameRequestDto.getNickname();
        if(nickname.equals("")) {
            throw new CustomException(ErrorCode.EMPTY_NICKNAME);
        } else if(nickname.length() < 2 || nickname.length() > 10) {
            throw new CustomException(ErrorCode.NICKNAME_LEGNTH);
        } else if (userRepository.findByNickname(nickname).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
        return new ResponseEntity("사용가능한 닉네임입니다", HttpStatus.OK);
    }

// 닉네임 저장
//    User user = userRepository.findById(userId).orElseThrow(
//            () -> new CustomException(ErrorCode.NOT_FOUND_USER));
//        user.update(nickname);
//        userRepository.save(user);
}