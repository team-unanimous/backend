package com.team.unanimous.model.team;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.unanimous.model.meeting.Meeting;
import com.team.unanimous.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String teamname;

    @Column
    private String uuid;

    @JsonManagedReference
    @OneToMany(mappedBy = "team")
    private List<TeamUser> userList;

    @OneToMany(mappedBy = "team")
    private List<Meeting> meetingList;

}
