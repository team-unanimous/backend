package com.team.unanimous.model;


import com.team.unanimous.dto.requestDto.SignupRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String nickname;

    @Column
    private String password;

    @Column
    private String userImage;

    @Column(unique = true)
    private Long kakaoId;

    public void update(String nickname) {
        this.nickname = nickname;
    }

    public User(SignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.kakaoId = null;
    }

    //카카오 회원가입 + 구글 회원가입
    @Builder
    public User( String username, String nickname, String password, String userImage, Long kakaoId) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.userImage = userImage;
        this.kakaoId = kakaoId;
    }
}
