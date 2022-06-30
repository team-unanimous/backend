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

    @Column(nullable = false)
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


    public User(SignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.nickname = requestDto.getNickname();
        this.password = requestDto.getPassword();
        this.userImage = requestDto.getUserImage();
    }
}
