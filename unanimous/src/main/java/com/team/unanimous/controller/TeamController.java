package com.team.unanimous.controller;


import com.team.unanimous.dto.requestDto.TeamInviteRequestDto;
import com.team.unanimous.dto.requestDto.TeamRequestDto;
import com.team.unanimous.dto.responseDto.TeamMainResponseDto;
import com.team.unanimous.dto.responseDto.TeamResponseDto;
import com.team.unanimous.dto.responseDto.TeamUserMainResponseDto;
import com.team.unanimous.dto.responseDto.TeamUserResponseDto;
import com.team.unanimous.security.UserDetailsImpl;
import com.team.unanimous.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    // 팀 선택 페이지
    @GetMapping("/api/teams")
    public List<TeamUserResponseDto> getTeams(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null){
            throw new IllegalArgumentException("읔");
        }
        return teamService.getTeams(userDetails);
    }

    // 팀 생성
    @PostMapping("/api/teams")
    public ResponseEntity createTeam(@RequestBody TeamRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails == null){
            throw new IllegalArgumentException("읔");
        }
        return teamService.createTeam(requestDto, userDetails);
    }

    //초대받은 팀 찾기
    @PostMapping("/api/teams/auth-code")
    public TeamResponseDto findTeamUuid(@RequestBody TeamInviteRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails == null){
            throw new IllegalArgumentException("읔");
        }
        return teamService.findTeamUuid(requestDto, userDetails);
    }


    // 팀 가입하기
    @PostMapping("/api/teams/join")
    public ResponseEntity JoinTeam(@RequestBody TeamInviteRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails == null){
            throw new IllegalArgumentException("읔");
        }
        return teamService.JoinTeam(requestDto, userDetails);
    }

    // 팀 메인 게시판
    @GetMapping("/api/teams/{teamId}")
    public TeamUserMainResponseDto getMain(@PathVariable Long teamId){
        return teamService.getMain(teamId);
    }

}
