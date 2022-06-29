package com.team.unanimous.service;


import com.team.unanimous.dto.requestDto.TeamInviteRequestDto;
import com.team.unanimous.dto.requestDto.TeamRequestDto;
import com.team.unanimous.dto.responseDto.TeamMainMeetingResponseDto;
import com.team.unanimous.dto.responseDto.TeamMainResponseDto;
import com.team.unanimous.dto.responseDto.TeamResponseDto;
import com.team.unanimous.dto.responseDto.TeamUserResponseDto;
import com.team.unanimous.model.meeting.Meeting;
import com.team.unanimous.model.team.Team;
import com.team.unanimous.model.team.TeamUser;
import com.team.unanimous.model.user.User;
import com.team.unanimous.repository.meeting.MeetingRepository;
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
        System.out.println(user);
        System.out.println(teamUserList);
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
    public TeamMainResponseDto getMain(Long teamId){
        Team team = teamRepository.findTeamById(teamId);
        List<User> user = userRepository.findAllById(team);
        List<Meeting> meetingList = meetingRepository.findAllById(team);
        List<TeamMainMeetingResponseDto> teamMainMeetingResponseDtos = new ArrayList<>();
        for (Meeting meeting : meetingList) {
            List<User> user1 = userRepository.findAllById(meeting);
            TeamMainMeetingResponseDto teamMainMeetingResponseDto = new TeamMainMeetingResponseDto(
                    meeting.getMeetingTitle(),
                    meeting.getMeetingDate(),
                    user1
            );
            teamMainMeetingResponseDtos.add(teamMainMeetingResponseDto);

        }

        TeamMainResponseDto teamMainResponseDto = new TeamMainResponseDto(
                team.getId(),
                team.getTeamname(),
                user,
                teamMainMeetingResponseDtos);
        return teamMainResponseDto;
    }




}
