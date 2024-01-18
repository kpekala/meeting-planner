package com.kpekala.meetingplanner.domain.meeting.dto;

import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@ToString
public class AddMeetingRequest {
    private String name;
    private ZonedDateTime startDate;
    private int durationMinutes;
    private List<UserDto> users;
}
