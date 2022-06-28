package com.team.unanimous.serviece;

import com.team.unanimous.dto.requestDto.TeamInviteRequestDto;
import com.team.unanimous.dto.requestDto.TeamRequestDto;
import com.team.unanimous.dto.responseDto.TeamMainMeetingResponseDto;
import com.team.unanimous.dto.responseDto.TeamMainResponseDto;
import com.team.unanimous.dto.responseDto.TeamResponseDto;
import com.team.unanimous.model.meeting.Meeting;
import com.team.unanimous.model.team.Team;
import com.team.unanimous.model.user.User;
import com.team.unanimous.repository.meeting.MeetingRepository;
import com.team.unanimous.repository.team.TeamRepository;
import com.team.unanimous.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    private final MeetingRepository meetingRepository;

    //팀 생성
    public ResponseEntity createTeam(TeamRequestDto requestDto,UserDetailsImpl userDetails){
        User user = userRepository.findById(userDetails.getId).orElseThrow(
                () -> new IllegalArgumentException("읔")
        );

        Team team = Team.builder()
                .teamname(requestDto.getTeamname())
                .uuid(UUID.randomUUID().toString())
                .user(user)
                .meetingList(null)
                .build();
        teamRepository.save(team);
        return ResponseEntity.ok("팀이 생성되었습니다!");
    }

    //팀 선택 페이지
    public List<TeamResponseDto> getTeams(UserDetailsImpl userDetails){
        User user = userRepository.findById(userDetails.getId);
        List<Team> teamList = teamRepository.findAllById(user);
        List<TeamResponseDto> responseDtoList = new ArrayList<>();
        for (Team team : teamList) {
            TeamResponseDto responseDto = new TeamResponseDto(team.getId(), team.getTeamname(), team.getUuid());

            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    //초대받은 팀 찾기
    public TeamResponseDto findTeamUuid(TeamInviteRequestDto requestDto,UserdetailsImpl userdetails){
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
    public ResponseEntity JoinTeam(TeamInviteRequestDto requestDto, UserDetailsImpl userDetails){
        String uuid = requestDto.getUuid();
        Team team = teamRepository.findByUuid(uuid);
        if (team == null){
            throw new IllegalArgumentException("읔");
        }
        User user = User.builder()
                .username(userdetails.getUsername())
                .nickname(userdetails.getUser.getNickname())
                .team(team)
                .meeting(null)
                .build();
        userRepository.save(user);

        return ResponseEntity.ok("팀에 가입되었습니다!");
    }

    // 팀 메인 게시판
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
