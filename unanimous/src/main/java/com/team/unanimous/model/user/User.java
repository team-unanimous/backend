package com.team.unanimous.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team.unanimous.model.meeting.Meeting;
import com.team.unanimous.model.team.Team;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}
