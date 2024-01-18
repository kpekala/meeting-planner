package com.kpekala.meetingplanner.domain.meeting.entity;

import com.kpekala.meetingplanner.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private ZonedDateTime startDate;

    private Integer durationMinutes;

    @ManyToMany
    private List<User> users;
}
