package com.team.unanimous.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.unanimous.dto.requestDto.EmailRequestDto;
import com.team.unanimous.dto.requestDto.SignupRequestDto;
import com.team.unanimous.model.Image;
import com.team.unanimous.model.meeting.MeetingUser;
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

    @Column
    private boolean isGoogle;

    @OneToOne
    @JoinColumn(name = "ImageId")
    private Image image;

    @Column
    private int count;


    public User(String username, String password, boolean isGoogle, String nickname) {
        this.username = username;
        this.password = password;
        this.isGoogle = isGoogle;
        this.nickname = nickname;
    }
//    public User(String password) {
//        this.password = password;
//    }

    //카카오 회원가입 + 구글 회원가입
    @Builder
    public User( String username, String nickname, String password, String userImage, boolean isGoogle) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.userImage = userImage;
        this.isGoogle = isGoogle;
    }

    public void update(String nickname){
        this.nickname = nickname;
    }

    public void updateImage(Image image){
        this.image = image;
    }
}
