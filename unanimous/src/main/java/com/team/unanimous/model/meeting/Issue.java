package com.team.unanimous.model.meeting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String issue;

    @Column
    private String result;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}
