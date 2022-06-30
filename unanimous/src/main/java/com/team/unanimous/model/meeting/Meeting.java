package com.team.unanimous.model.meeting;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.unanimous.model.team.Team;
import com.team.unanimous.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String meetingTitle;

    @Column(nullable = false)
    private String meetingDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User meetingCreator;

    @JsonManagedReference
    @OneToMany(mappedBy = "meeting")
    private List<Issue> issue;

    @JsonManagedReference
    @OneToMany(mappedBy = "meeting")
    private List<MeetingUser> user;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
