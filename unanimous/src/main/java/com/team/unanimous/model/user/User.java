package com.team.unanimous.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.unanimous.dto.requestDto.SignupRequestDto;
import com.team.unanimous.model.meeting.Meeting;
import com.team.unanimous.model.meeting.MeetingUser;
import com.team.unanimous.model.team.Team;
import com.team.unanimous.model.team.TeamUser;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column
    private String nickname;

    @JsonIgnore
    @Column
    private String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<TeamUser> teamList;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<MeetingUser> meeting;

    @Column
    private String userImage;

    @Column(unique = true)
    private Long kakaoId;


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

    public void update(String nickname){
        this.nickname = nickname;
    }
}
