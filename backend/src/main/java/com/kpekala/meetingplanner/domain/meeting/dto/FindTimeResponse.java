package com.kpekala.meetingplanner.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
public class FindTimeResponse {
    private ZonedDateTime startDate;
    private int duration;
}
