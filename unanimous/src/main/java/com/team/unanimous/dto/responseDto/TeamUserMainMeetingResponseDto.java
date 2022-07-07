package com.team.unanimous.dto.responseDto;

import com.team.unanimous.model.meeting.Meeting;
import com.team.unanimous.model.meeting.MeetingUser;
import com.team.unanimous.model.team.Team;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamUserMainMeetingResponseDto {

    private List<Meeting> meetings;

    private List<MeetingUser> meetingUsers;

    public TeamUserMainMeetingResponseDto(Team team, List<MeetingUser> meetingUsers) {
        this.meetings = team.getMeetingList();
        this.meetingUsers = meetingUsers;
    }
}
