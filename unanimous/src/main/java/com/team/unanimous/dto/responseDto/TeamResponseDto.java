package com.team.unanimous.dto.responseDto;

import lombok.Getter;

@Getter
public class TeamResponseDto {

    private Long id;
    private String teamname;

    private String uuid;

    public TeamResponseDto(Long id, String teamname, String uuid) {
        this.id = id;
        this.teamname = teamname;
        this.uuid = uuid;
    }


}
