package com.team.unanimous.dto.responseDto;


import com.team.unanimous.model.team.Team;
import com.team.unanimous.model.user.User;
import lombok.Getter;

@Getter
public class TeamUserResponseDto {

    private Long id;

//    private Team team;
//
//    private User user;

    private String teamname;

    private String nickname;

//    public TeamUserResponseDto(Long id, Team team, User user) {
//        this.id = id;
//        this.team = team;
//        this.user = user;
//    }

    public TeamUserResponseDto(Team team, User user) {
        this.id = team.getId();
        this.teamname = team.getTeamname();
        this.nickname = user.getNickname();
    }
}
