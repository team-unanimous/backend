package com.team.unanimous.dto.responseDto;

import com.team.unanimous.model.team.Team;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamUserMainResponseDto {

    private Long teamid;

    private String teamname;
    private List<NicknameResponseDto> user;

    public TeamUserMainResponseDto(Team team, List<NicknameResponseDto> nicknameResponseDtos) {
        this.teamid = team.getId();
        this.teamname = team.getTeamname();
        this.user = nicknameResponseDtos;
    }
}
