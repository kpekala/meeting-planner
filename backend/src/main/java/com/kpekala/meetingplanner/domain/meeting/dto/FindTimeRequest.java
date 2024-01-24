package com.kpekala.meetingplanner.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FindTimeRequest {
    private List<UserDto> users;
}
