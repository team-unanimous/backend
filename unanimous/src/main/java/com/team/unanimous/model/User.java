package com.team.unanimous.model;

import com.team.unanimous.dto.requestDto.SignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    public User(SignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.nickname = requestDto.getNickname();
        this.password = requestDto.getPassword();
        this.userImage = requestDto.getUserImage();
    }
}
