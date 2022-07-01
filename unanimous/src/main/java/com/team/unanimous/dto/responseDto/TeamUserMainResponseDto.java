package com.team.unanimous.dto.responseDto;

import com.team.unanimous.model.meeting.Meeting;
import com.team.unanimous.model.team.Team;
import com.team.unanimous.model.team.TeamUser;
import com.team.unanimous.model.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamUserMainResponseDto {

    private Long teamid;

    private String teamname;

//    private List<TeamUser> users;


    private List<NicknameResponseDto> user;

//    private List<TeamUserMainMeetingResponseDto> meeting;

    public TeamUserMainResponseDto(Team team, List<NicknameResponseDto> nicknameResponseDtos) {
        this.teamid = team.getId();
        this.teamname = team.getTeamname();
        this.user = nicknameResponseDtos;
//        this.users = team.getUserList();
//        this.meeting = teamUserMainMeetingResponseDtos;
    }
}
