package com.team.unanimous.repository.meeting;

import com.team.unanimous.model.meeting.Meeting;
import com.team.unanimous.model.meeting.MeetingUser;
import com.team.unanimous.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long> {

    List<MeetingUser> findAllByMeeting(Meeting meeting);

    Optional<MeetingUser> findByUser(User user);

}
