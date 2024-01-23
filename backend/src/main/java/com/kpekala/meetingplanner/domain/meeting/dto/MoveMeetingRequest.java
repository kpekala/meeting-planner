package com.kpekala.meetingplanner.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class MoveMeetingRequest {
    private int id;
    private ZonedDateTime newDate;
}
