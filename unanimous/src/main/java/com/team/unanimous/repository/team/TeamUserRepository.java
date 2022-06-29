package com.team.unanimous.repository.team;

import com.team.unanimous.model.team.Team;
import com.team.unanimous.model.team.TeamUser;
import com.team.unanimous.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamUserRepository extends JpaRepository<TeamUser,Long> {

    List<TeamUser> findByUserIdAndTeamId(Long userId,Long teamId);

    List<TeamUser> findAllByUser(User user);

    List<TeamUser> findByUser(User user);

    List<TeamUser> findByTeam(Team team);
}
