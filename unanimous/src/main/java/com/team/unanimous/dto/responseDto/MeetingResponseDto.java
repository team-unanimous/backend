package com.team.unanimous.dto.responseDto;

import com.team.unanimous.model.meeting.Issue;
import com.team.unanimous.model.meeting.Meeting;
import lombok.Getter;

import java.util.List;

@Getter
public class MeetingResponseDto {

    private Long meetingId;

    private Meeting.Status meetingStatus;

    private String meetingTitle;

    private String meetingDate;

    private String meetingTime;

    private String meetingSum;

    private String meetingTheme;

    private String meetingDuration;

    private List<Issue> issues;

    public MeetingResponseDto(Meeting meeting){
        this.meetingId = meeting.getId();
        this.meetingStatus = meeting.getMeetingStatus();
        this.meetingTitle = meeting.getMeetingTitle();
        this.meetingDate = meeting.getMeetingDate();
        this.meetingTime = meeting.getMeetingTime();
        this.meetingSum = meeting.getMeetingSum();
        this.meetingTheme = meeting.getMeetingTheme();
        this.meetingDuration = meeting.getMeetingDuration();
        this.issues = meeting.getMeetingIssue();
    }
}
