package com.team.unanimous.repository.team;

import com.team.unanimous.model.team.Team;
import com.team.unanimous.model.team.TeamUser;
import com.team.unanimous.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamUserRepository extends JpaRepository<TeamUser,Long> {

    List<TeamUser> findByUserIdAndTeamId(Long userId,Long teamId);

    List<TeamUser> findAllByUser(User user);

    TeamUser findByUser(User user);


    List<TeamUser> findAllByTeam(Team team);

    TeamUser findByTeam(Team team);

    List<TeamUser> findAllByTeamId(Long teamId);

    TeamUser findAllByTeamIdAndUserId(Long teamId, Long userId);

}
