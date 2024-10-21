package com.calendar.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToMany
    @JoinTable(
      name = "meeting_participants",
      joinColumns = @JoinColumn(name = "meeting_id"),
      inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> participants;

    // Getters and setters
}
