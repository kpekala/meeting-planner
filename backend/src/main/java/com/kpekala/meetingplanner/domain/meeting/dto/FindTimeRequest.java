package com.kpekala.meetingplanner.domain.meeting.dto;

import lombok.Data;

import java.util.List;

@Data
public class FindTimeRequest {
    private List<UserDto> users;
}
