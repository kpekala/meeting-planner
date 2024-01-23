package com.kpekala.meetingplanner.domain.meeting;

import com.kpekala.meetingplanner.domain.meeting.dto.AddMeetingRequest;
import com.kpekala.meetingplanner.domain.meeting.dto.AddMeetingResponse;
import com.kpekala.meetingplanner.domain.meeting.dto.MeetingDto;
import com.kpekala.meetingplanner.domain.meeting.dto.MoveMeetingRequest;

import java.util.List;

public interface MeetingService {

    AddMeetingResponse addMeeting(AddMeetingRequest request);

    List<MeetingDto> getMeetings(String userEmail);

    void removeMeeting(Integer id);

    void moveMeeting(MoveMeetingRequest request);
}
