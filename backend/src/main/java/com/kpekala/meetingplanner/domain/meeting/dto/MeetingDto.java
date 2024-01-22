package com.kpekala.meetingplanner.domain.meeting.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class MeetingDto {
    private Integer id;
    private String name;
    private ZonedDateTime startDate;
    private Integer durationMinutes;
    private List<UserDto> userDtos;
}
