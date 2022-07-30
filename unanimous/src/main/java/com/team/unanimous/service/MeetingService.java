package com.team.unanimous.service;

import com.team.unanimous.dto.requestDto.MeetingRequestDto;
import com.team.unanimous.dto.responseDto.MeetingResponseDto;
import com.team.unanimous.dto.responseDto.NicknameResponseDto;
import com.team.unanimous.exceptionHandler.CustomException;
import com.team.unanimous.exceptionHandler.ErrorCode;
import com.team.unanimous.model.Image;
import com.team.unanimous.model.chat.ChatRoom;
import com.team.unanimous.model.chat.ChatRoomUser;
import com.team.unanimous.model.meeting.Meeting;
import com.team.unanimous.model.meeting.MeetingUser;
import com.team.unanimous.model.team.Team;
import com.team.unanimous.model.user.User;
import com.team.unanimous.repository.chat.ChatRoomRepository;
import com.team.unanimous.repository.chat.ChatRoomUserRepository;
import com.team.unanimous.repository.meeting.MeetingRepository;
import com.team.unanimous.repository.meeting.MeetingUserRepository;
import com.team.unanimous.repository.team.TeamRepository;
import com.team.unanimous.repository.user.UserRepository;
import com.team.unanimous.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final UserRepository userRepository;

    private final TeamRepository teamRepository;

    private final MeetingRepository meetingRepository;

    private final MeetingUserRepository meetingUserRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomUserRepository chatRoomUserRepository;

    // 미팅 예약하기 생성
    @Transactional
    public ResponseEntity createMeeting(MeetingRequestDto meetingRequestDto,
                                        UserDetailsImpl userDetails,
                                        Long teamId){
        String meetingTitle = meetingRequestDto.getMeetingTitle().trim();
        if (meetingTitle.length() > 20){
            throw new CustomException(ErrorCode.MEETING_NAME_LENGTH);
        } else if (meetingTitle.isEmpty()){
            throw new CustomException(ErrorCode.MEETING_NAME_LENGTH);
        } else if (meetingTitle.equals("")){
            throw new CustomException(ErrorCode.MEETING_NAME_LENGTH);
        } else if (meetingTitle == null){
            throw new CustomException(ErrorCode.MEETING_NAME_LENGTH);
        }
        String meetingDate = meetingRequestDto.getMeetingDate();
        String meetingTime = meetingRequestDto.getMeetingTime();
        String meetingDuration = meetingRequestDto.getMeetingDuration();

        // 미팅 생성 시간 삽입
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);

        if (meetingDate.equals("")){
            meetingDate = dateResult.split(" ")[0];
            meetingDuration = "1시간";
        } else if (meetingDate == null){
            meetingDate = dateResult.split(" ")[0];
            meetingDuration = "1시간";
        } else if (meetingDate.isEmpty()){
            meetingDate = dateResult.split(" ")[0];
            meetingDuration = "1시간";
        } else if (meetingDate.equals("string")){
            meetingDate = dateResult.split(" ")[0];
            meetingDuration = "1시간";
        }
        if (meetingTime.equals("")){
            meetingTime = dateResult.split(" ")[1];
            meetingDuration = "1시간";
        } else if (meetingTime == null){
            meetingTime = dateResult.split(" ")[1];
            meetingDuration = "1시간";
        } else if (meetingTime.isEmpty()){
            meetingTime = dateResult.split(" ")[1];
            meetingDuration = "1시간";
        } else if (meetingTime.equals("string")){
            meetingTime = dateResult.split(" ")[1];
            meetingDuration = "1시간";
        }

        String[] meetingTime1 = meetingTime.split(":");
        String[] meetingDuration1 = meetingDuration.split("시");
        String meetingTime2 = meetingTime1[0];
        String meetingDuration2 = meetingDuration1[0];
        String meetingOverTime1 = "";


        int meetingTimeInt = Integer.parseInt(meetingTime2);
        int meetingDurationInt = Integer.parseInt(meetingDuration2);
        int meetingOverTime = meetingTimeInt + meetingDurationInt;

        if (meetingOverTime >= 24){
            meetingOverTime -= 24;
        }

        if (meetingOverTime < 10){
            meetingOverTime1 = "0"+meetingOverTime+":"+meetingTime1[1];
        } else {
            meetingOverTime1 = meetingOverTime+":"+meetingTime1[1];
        }


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
                .meetingTime(meetingTime)
                .meetingSum(meetingRequestDto.getMeetingSum())
                .meetingTheme(meetingRequestDto.getMeetingTheme())
                .meetingDuration(meetingDuration)
                .meetingOverTime(meetingOverTime1)
                .meetingCreator(user)
                .team(team)
                .build();
        meetingRepository.save(meeting);

        MeetingUser meetingUser = new MeetingUser(user,meeting);

        meetingUserRepository.save(meetingUser);
        Long meetingId = meeting.getId();

        ChatRoom chatRoom = ChatRoom.builder()
                .id(meetingId)
                .chatRoomName(meeting.getMeetingTitle())
                .meetingId(meetingId)
                .build();
        chatRoomRepository.save(chatRoom);
        return ResponseEntity.ok(meetingId);
    }

    // 미팅 바로 시작하기
    public ResponseEntity createMeetingNow(MeetingRequestDto meetingRequestDto,
                                           UserDetailsImpl userDetails,
                                           Long teamId){
        String meetingTitle = meetingRequestDto.getMeetingTitle().trim();
        if (meetingTitle.length() > 20){
            throw new CustomException(ErrorCode.MEETING_NAME_LENGTH);
        } else if (meetingTitle.isEmpty()){
            throw new CustomException(ErrorCode.MEETING_NAME_LENGTH);
        } else if (meetingTitle.equals("")){
            throw new CustomException(ErrorCode.MEETING_NAME_LENGTH);
        } else if (meetingTitle == null){
            throw new CustomException(ErrorCode.MEETING_NAME_LENGTH);
        }
        User user = userRepository.findUserById(userDetails.getUser().getId());
        if (user == null){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        Team team = teamRepository.findTeamById(teamId);
        if (team == null){
            throw new CustomException(ErrorCode.TEAM_NOT_FOUND);
        }

        // 미팅 생성 시간 삽입
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);

        String meetingDate = dateResult.split(" ")[0];
        String meetingTime = dateResult.split(" ")[1];

        Meeting meeting = Meeting.builder()
                .meetingStatus(Meeting.Status.NOW)
                .meetingTitle(meetingTitle)
                .meetingDate(meetingDate)
                .meetingTime(meetingTime)
                .meetingSum(meetingRequestDto.getMeetingSum())
                .meetingTheme(meetingRequestDto.getMeetingTheme())
                .meetingCreator(user)
                .team(team)
                .build();
        meetingRepository.save(meeting);

        MeetingUser meetingUser = new MeetingUser(user,meeting);

        meetingUserRepository.save(meetingUser);

        Long meetingId = meeting.getId();

        ChatRoom chatRoom = ChatRoom.builder()
                .id(meetingId)
                .chatRoomName(meeting.getMeetingTitle())
                .meetingId(meetingId)
                .build();
        chatRoomRepository.save(chatRoom);
        return ResponseEntity.ok(meetingId);
    }

    // 미팅 목록
    public List<MeetingResponseDto> getMeetings(Long teamId){
        List<Meeting> meetingList = meetingRepository.findAllByTeamId(teamId);

        List<MeetingResponseDto> meetingResponseDtos = new ArrayList<>();
        for (Meeting meeting : meetingList){
            User user = meeting.getMeetingCreator();
            MeetingResponseDto meetingResponseDto = new MeetingResponseDto(meeting,user);
            meetingResponseDtos.add(meetingResponseDto);
        }
        return meetingResponseDtos;
    }

    // 예약 중인 미팅 목록
    public List<MeetingResponseDto> getYetMeetings(Long teamId){
        List<Meeting> meetingList = meetingRepository.findAllByOrderByMeetingDateAsc();
        List<Meeting> meetingList1 = new ArrayList<>();
        for (Meeting meeting : meetingList){
            if (meeting.getTeam().getId().equals(teamId)){
                meetingList1.add(meeting);
            }
        }

        List<MeetingResponseDto> meetingResponseDtos = new ArrayList<>();
        for (Meeting meeting : meetingList1){
            if (meeting.getMeetingStatus().equals(Meeting.Status.YET)) {
                User user = meeting.getMeetingCreator();
                MeetingResponseDto meetingResponseDto = new MeetingResponseDto(meeting,user);
                meetingResponseDtos.add(meetingResponseDto);
            }
        }
        return meetingResponseDtos;
    }

    // 진행중인 미팅 목록
    public List<MeetingResponseDto> getNowMeetings(Long teamId){
        List<Meeting> meetingList = meetingRepository.findAllByOrderByModifiedAtDesc();
        List<Meeting> meetingList1 = new ArrayList<>();
        for (Meeting meeting : meetingList){
            if (meeting.getTeam().getId().equals(teamId)){
                meetingList1.add(meeting);
            }
        }
        List<MeetingResponseDto> meetingResponseDtos = new ArrayList<>();
        for (Meeting meeting : meetingList1){
            if (meeting.getMeetingStatus().equals(Meeting.Status.NOW)) {
                User user = meeting.getMeetingCreator();
                List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findAllByChatRoomId(meeting.getId());
                int userCnt = chatRoomUsers.size();
                List<NicknameResponseDto> nicknameResponseDtos = new ArrayList<>();
                for (int i = 0; i < chatRoomUsers.size(); i++){
                    User user1 = chatRoomUsers.get(i).getUser();
                    Image image = user1.getImage();
                    NicknameResponseDto nicknameResponseDto = new NicknameResponseDto(user1,image);
                    nicknameResponseDtos.add(nicknameResponseDto);
                }
                MeetingResponseDto meetingResponseDto = new MeetingResponseDto(meeting,user,nicknameResponseDtos,userCnt);
                meetingResponseDtos.add(meetingResponseDto);
            }
        }
        return meetingResponseDtos;
    }

    // 이전 미팅 목록
    public List<MeetingResponseDto> getDoneMeetings(Long teamId){
        List<Meeting> meetingList = meetingRepository.findAllByOrderByModifiedAtDesc();
        List<Meeting> meetingList1 = new ArrayList<>();
        for (Meeting meeting : meetingList){
            if (meeting.getTeam().getId().equals(teamId)){
                meetingList1.add(meeting);
            }
        }

        List<MeetingResponseDto> meetingResponseDtos = new ArrayList<>();
        for (Meeting meeting : meetingList1){
            if (meeting.getMeetingStatus().equals(Meeting.Status.DONE)) {
                User user = meeting.getMeetingCreator();
                MeetingResponseDto meetingResponseDto = new MeetingResponseDto(meeting,user);
                meetingResponseDtos.add(meetingResponseDto);
            }
        }
        return meetingResponseDtos;
    }

    // 특정 미팅 조회
    public MeetingResponseDto getMeeting(Long meetingId){
        Meeting meeting = meetingRepository.findMeetingById(meetingId);
        if (meeting == null){
            throw new CustomException(ErrorCode.MEETING_NOT_FOUND);
        }
        User user = meeting.getMeetingCreator();

        MeetingResponseDto meetingResponseDto = new MeetingResponseDto(meeting,user);
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

        String meetingTitle = requestDto.getMeetingTitle();
        if (meetingTitle.length() > 20){
            throw new CustomException(ErrorCode.MEETING_NAME_LENGTH);
        } else if (meetingTitle.isEmpty()){
            throw new CustomException(ErrorCode.MEETING_NAME_LENGTH);
        } else if (meetingTitle.equals("")){
            throw new CustomException(ErrorCode.MEETING_NAME_LENGTH);
        } else if (meetingTitle == null){
            throw new CustomException(ErrorCode.MEETING_NAME_LENGTH);
        }

        String meetingTime = requestDto.getMeetingTime().split(":")[0];
        String meetingDuration = requestDto.getMeetingDuration().split("시")[0];
        int meetingTime2 = Integer.parseInt(meetingTime);
        int meetingDuration2 = Integer.parseInt(meetingDuration);
        int meetingOverTime2 = meetingTime2+meetingDuration2;
        String meetingOverTime = "";

        if (meetingOverTime2 >= 24){
            meetingOverTime2 -= 24;
        }

        if (meetingOverTime2 < 10){
            meetingOverTime = "0"+meetingOverTime2+":00";
        } else {
            meetingOverTime = meetingOverTime2+":00";
        }

        meeting.updateMeeting(requestDto,meetingOverTime);
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

    // 미팅 NOW로 전환
    @Transactional
    public ResponseEntity changeMeetingNow(Long meetingId) {
        Meeting meeting = meetingRepository.findMeetingById(meetingId);
        meeting.setMeetingStatus(Meeting.Status.NOW);
        meetingRepository.save(meeting);
        return ResponseEntity.ok("회의 시작");
    }

    // 미팅 DONE으로 전환
    @Transactional
    public ResponseEntity changeMeetingDone(Long meetingId){
        Meeting meeting = meetingRepository.findMeetingById(meetingId);
        meeting.setMeetingStatus(Meeting.Status.DONE);

        // 미팅 생성 시간 삽입
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);
        String meetingOverTime = dateResult.split(" ")[1];
        meeting.setMeetingOverTime(meetingOverTime);
        meetingRepository.save(meeting);
        return ResponseEntity.ok("회의 종료");
    }
}
