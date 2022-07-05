package com.team.unanimous.service;

import com.team.unanimous.dto.requestDto.MeetingRequestDto;
import com.team.unanimous.dto.responseDto.IssueResponseDto;
import com.team.unanimous.dto.responseDto.MeetingResponseDto;
import com.team.unanimous.dto.responseDto.NicknameResponseDto;
import com.team.unanimous.exceptionHandler.CustomException;
import com.team.unanimous.exceptionHandler.ErrorCode;
import com.team.unanimous.model.meeting.Issue;
import com.team.unanimous.model.meeting.Meeting;
import com.team.unanimous.model.meeting.MeetingUser;
import com.team.unanimous.model.team.Team;
import com.team.unanimous.model.user.User;
import com.team.unanimous.repository.issue.IssueRepository;
import com.team.unanimous.repository.meeting.MeetingRepository;
import com.team.unanimous.repository.meeting.MeetingUserRepository;
import com.team.unanimous.repository.team.TeamRepository;
import com.team.unanimous.repository.team.TeamUserRepository;
import com.team.unanimous.repository.user.UserRepository;
import com.team.unanimous.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final UserRepository userRepository;

    private final TeamRepository teamRepository;

    private final TeamUserRepository teamUserRepository;

    private final MeetingRepository meetingRepository;

    private final MeetingUserRepository meetingUserRepository;

    private final IssueRepository issueRepository;

    // 미팅 생성
    @Transactional
    public ResponseEntity createMeeting(MeetingRequestDto meetingRequestDto,
                                        UserDetailsImpl userDetails,
                                        Long teamId){
        String meetingTitle = meetingRequestDto.getMeetingTitle();
        String meetingDate = meetingRequestDto.getMeetingDate();
        User user = userRepository.findUserById(userDetails.getUser().getId());
        if (user == null){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        Team team = teamRepository.findTeamById(teamId);
        if (team == null){
            throw new CustomException(ErrorCode.TEAM_NOT_FOUND);
        }

        Meeting meeting = Meeting.builder()
                .meetingStatus(Meeting.Status.YET)
                .meetingTitle(meetingTitle)
                .meetingDate(meetingDate)
                .meetingTime(meetingRequestDto.getMeetingTime())
                .meetingSum(meetingRequestDto.getMeetingSum())
                .meetingTheme(meetingRequestDto.getMeetingTheme())
                .meetingDuration(meetingRequestDto.getMeetingDuration())
                .meetingCreator(user)
                .team(team)
                .build();
        meetingRepository.save(meeting);

        MeetingUser meetingUser = new MeetingUser(user,meeting);

        meetingUserRepository.save(meetingUser);
        Long meetingId = meeting.getId();
        return ResponseEntity.ok(meetingId);
    }

    // 미팅 바로 시작하기
    public ResponseEntity createMeetingNow(MeetingRequestDto meetingRequestDto,
                                           UserDetailsImpl userDetails,
                                           Long teamId){
        String meetingTitle = meetingRequestDto.getMeetingTitle();
        User user = userRepository.findUserById(userDetails.getUser().getId());
        if (user == null){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        Team team = teamRepository.findTeamById(teamId);
        if (team == null){
            throw new CustomException(ErrorCode.TEAM_NOT_FOUND);
        }

        Meeting meeting = Meeting.builder()
                .meetingStatus(Meeting.Status.NOW)
                .meetingTitle(meetingTitle)
                .meetingDate(meetingRequestDto.getMeetingDate())
                .meetingTime(meetingRequestDto.getMeetingTime())
                .meetingSum(meetingRequestDto.getMeetingSum())
                .meetingTheme(meetingRequestDto.getMeetingTheme())
                .meetingCreator(user)
                .team(team)
                .build();
        meetingRepository.save(meeting);

        MeetingUser meetingUser = new MeetingUser(user,meeting);

        meetingUserRepository.save(meetingUser);

        Long meetingId = meeting.getId();
        return ResponseEntity.ok(meetingId);
    }

    // 미팅 목록
    public List<MeetingResponseDto> getMeetings(Long teamId){
        List<Meeting> meetingList = meetingRepository.findAllByTeamId(teamId);

        List<MeetingResponseDto> meetingResponseDtos = new ArrayList<>();
        for (Meeting meeting : meetingList){
            MeetingResponseDto meetingResponseDto = new MeetingResponseDto(meeting);
            meetingResponseDtos.add(meetingResponseDto);
        }
        return meetingResponseDtos;
    }

    // 특정 미팅 조회
    public MeetingResponseDto getMeeting(Long meetingId){
        Meeting meeting = meetingRepository.findMeetingById(meetingId);
        if (meeting == null){
            throw new CustomException(ErrorCode.MEETING_NOT_FOUND);
        }

        MeetingResponseDto meetingResponseDto = new MeetingResponseDto(meeting);
        return meetingResponseDto;
    }

    // 미팅 프로필 수정
    @Transactional
    public ResponseEntity updateMeeting(MeetingRequestDto requestDto,
                                        Long meetingId,
                                        UserDetailsImpl userDetails){
        Meeting meeting = meetingRepository.findMeetingById(meetingId);
        if (!(meeting.getMeetingCreator().getNickname().equals(userDetails.getUser().getNickname()))){
            throw new CustomException(ErrorCode.INVALID_AUTHORITY);
        }

        meeting.updateMeeting(requestDto);
        meetingRepository.save(meeting);
        return ResponseEntity.ok("미팅 정보 수정 완료");
    }

    // 미팅 삭제
    @Transactional
    public ResponseEntity deleteMeeting(Long meetingId,
                                        UserDetailsImpl userDetails){
        Meeting meeting = meetingRepository.findMeetingById(meetingId);
        if (!(meeting.getMeetingCreator().getNickname().equals(userDetails.getUser().getNickname()))){
            throw new CustomException(ErrorCode.INVALID_AUTHORITY);
        }

        meetingRepository.delete(meeting);
        return ResponseEntity.ok("미팅 삭제 완료");
    }
}
