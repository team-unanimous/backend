package com.team.unanimous.dto.responseDto;

import com.team.unanimous.model.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamMainResponseDto {
    private Long teamId;
    private String teamname;
    List<User> userList;

    List<TeamMainMeetingResponseDto> meetingResponseDtos;

    public TeamMainResponseDto(Long teamId, String teamname, List<User> userList, List<TeamMainMeetingResponseDto> meetingResponseDtos) {
        this.teamId = teamId;
        this.teamname = teamname;
        this.userList = userList;
        this.meetingResponseDtos = meetingResponseDtos;
    }
}
