package com.team.unanimous.repository.user;

import com.team.unanimous.model.meeting.Meeting;
import com.team.unanimous.model.team.Team;
import com.team.unanimous.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAllById(Team team);

    User findUserById(Long id);

    List<User> findAllById(Meeting meeting);
}
