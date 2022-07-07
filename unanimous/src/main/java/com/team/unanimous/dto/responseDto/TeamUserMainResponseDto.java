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

    private String uuid;
    private List<NicknameResponseDto> user;

    public TeamUserMainResponseDto(Team team, List<NicknameResponseDto> nicknameResponseDtos) {
        this.teamid = team.getId();
        this.teamname = team.getTeamname();
        this.uuid = team.getUuid();
        this.user = nicknameResponseDtos;
    }
}
