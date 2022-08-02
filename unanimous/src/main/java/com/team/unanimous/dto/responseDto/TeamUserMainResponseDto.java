package com.team.unanimous.dto.responseDto;

import com.team.unanimous.model.team.Team;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamUserMainResponseDto {


    private Long teamid;

    private String teamname;

    private Long teamManagerId;
    private String teamImage;

    private String teamManager;

    private String uuid;
    private List<NicknameResponseDto> user;

    public TeamUserMainResponseDto(Team team,String teamManager, List<NicknameResponseDto> nicknameResponseDtos) {
        this.teamid = team.getId();
        this.teamname = team.getTeamname();
        this.teamManagerId = team.getTeamManager();
        this.teamManager = teamManager;
        this.uuid = team.getUuid();
        this.teamImage = team.getTeamImage().getTeamImageUrl();
        this.user = nicknameResponseDtos;
    }
}
