package com.team.unanimous.service;


import com.team.unanimous.dto.requestDto.NicknameRequestDto;
import com.team.unanimous.dto.requestDto.TeamInviteRequestDto;
import com.team.unanimous.dto.requestDto.TeamRequestDto;
import com.team.unanimous.dto.responseDto.*;
import com.team.unanimous.exceptionHandler.CustomException;
import com.team.unanimous.exceptionHandler.ErrorCode;
import com.team.unanimous.model.meeting.Meeting;
import com.team.unanimous.model.meeting.MeetingUser;
import com.team.unanimous.model.team.Team;
import com.team.unanimous.model.team.TeamUser;
import com.team.unanimous.model.user.User;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    private final TeamUserRepository teamUserRepository;

    private final MeetingRepository meetingRepository;

    private final MeetingUserRepository meetingUserRepository;

    //팀 생성
    @Transactional
    public ResponseEntity createTeam(TeamRequestDto requestDto, UserDetailsImpl userDetails){
        User user = userRepository.findUserById(userDetails.getUser().getId());
        if (user == null){
            throw new IllegalArgumentException("읔");
        }
        Team team = Team.builder()
                .teamname(requestDto.getTeamname())
                .uuid(UUID.randomUUID().toString())
                .build();
        teamRepository.save(team);
        TeamUser teamUser = new TeamUser(user,team);
        teamUserRepository.save(teamUser);
        return ResponseEntity.ok("팀이 생성되었습니다!");
    }

    //팀 선택 페이지
    @Transactional
    public List<TeamUserResponseDto> getTeams(UserDetailsImpl userDetails){
        User user = userRepository.findUserById(userDetails.getUser().getId());
        List<TeamUser> teamUserList = teamUserRepository.findAllByUser(user);
        List<TeamUserResponseDto> responseDtoList = new ArrayList<>();
        for (TeamUser teamUser : teamUserList) {
            TeamUserResponseDto responseDto = new TeamUserResponseDto(teamUser.getTeam(), teamUser.getUser());
//            TeamUserResponseDto responseDto = new TeamUserResponseDto(teamUser.getId(), teamUser.getTeam(), teamUser.getUser());

            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    //초대받은 팀 찾기
    @Transactional
    public TeamResponseDto findTeamUuid(TeamInviteRequestDto requestDto, UserDetailsImpl userdetails){
        String uuid = requestDto.getUuid();
        Team team = teamRepository.findByUuid(uuid);
        if (team == null){
            throw new IllegalArgumentException("읔");
        }
//        User user = User.builder()
//                .username(userdetails.getUsername())
//                .nickname(userdetails.getUser.getNickname())
//                .team(team)
//                .build();
//        userRepository.save(user);

        return new TeamResponseDto(team.getId(), team.getTeamname(), team.getUuid());
    }

    //팀 가입하기
    @Transactional
    public ResponseEntity JoinTeam(TeamInviteRequestDto requestDto, UserDetailsImpl userDetails){
        String uuid = requestDto.getUuid();
        Team team = teamRepository.findByUuid(uuid);

        if (team == null){
            throw new IllegalArgumentException("읔");
        }
        User user = userRepository.findUserById(userDetails.getUser().getId());
        TeamUser teamUser1 = teamUserRepository.findByUser(user);
        if (teamUser1.getUser().getId().equals(userDetails.getUser().getId())){
            throw new CustomException(ErrorCode.DUPLICATE_TEAM_USER);
        }
        TeamUser teamuser = new TeamUser(user,team);
        teamUserRepository.save(teamuser);

        // team.userList
        // userList.add(user)

//        User user1 = User.builder()
//                .username(userDetails.getUsername())
//                .nickname(userDetails.getUser().getNickname())
//                .team(team)
//                .password(userDetails.getPassword())
//                .meeting(null)
//                .build();

        return ResponseEntity.ok("팀에 가입되었습니다!");
    }

    // 팀 메인 게시판
    @Transactional
    public TeamUserMainResponseDto getMain(Long teamId){
        Team team = teamRepository.findTeamById(teamId);
        Meeting meeting = meetingRepository.findAllByTeam(team);
        List<TeamUser> teamUserList = teamUserRepository.findAllByTeam(team);
        List<NicknameResponseDto> nicknameResponseDtos = new ArrayList<>();
        for (TeamUser teamUser : teamUserList){
            NicknameResponseDto nicknameResponseDto = new NicknameResponseDto(teamUser.getUser());
            nicknameResponseDtos.add(nicknameResponseDto);
        }
        List<MeetingUser> meetingUsers = meetingUserRepository.findAllByMeeting(meeting);
        List<TeamUserMainMeetingResponseDto> teamUserMainMeetingResponseDtos = new ArrayList<>();
        for (MeetingUser meetingUser : meetingUsers){
            TeamUserMainMeetingResponseDto teamUserMainMeetingResponseDto = new TeamUserMainMeetingResponseDto(
                    meetingUser.getMeeting().getTeam(),
                    meetingUsers);
            teamUserMainMeetingResponseDtos.add(teamUserMainMeetingResponseDto);
        }
        TeamUserMainResponseDto  teamUserMainResponseDto = new TeamUserMainResponseDto(team,nicknameResponseDtos,teamUserMainMeetingResponseDtos);
        return teamUserMainResponseDto;

//        List<User> user = userRepository.findAllById(team);
//        List<Meeting> meetingList = meetingRepository.findAllById(team);
//        List<TeamMainMeetingResponseDto> teamMainMeetingResponseDtos = new ArrayList<>();
//        for (Meeting meeting : meetingList) {
//            List<User> user1 = userRepository.findAllById(meeting);
//            TeamMainMeetingResponseDto teamMainMeetingResponseDto = new TeamMainMeetingResponseDto(
//                    meeting.getMeetingTitle(),
//                    meeting.getMeetingDate(),
//                    user1
//            );
//            teamMainMeetingResponseDtos.add(teamMainMeetingResponseDto);
//
//        }
//
//        TeamMainResponseDto teamMainResponseDto = new TeamMainResponseDto(
//                team.getId(),
//                team.getTeamname(),
//                user,
//                teamMainMeetingResponseDtos);
//        return teamMainResponseDto;
    }

    // 팀 프로필 수정 Fetch 오늘까지 해보는걸로
    // 팀 프로필 사진, 팀 네임


    // 팀원 강퇴




}
