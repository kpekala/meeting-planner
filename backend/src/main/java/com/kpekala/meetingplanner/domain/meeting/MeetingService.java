package com.kpekala.meetingplanner.domain.meeting;

import com.kpekala.meetingplanner.domain.meeting.dto.*;

import java.util.List;

public interface MeetingService {

    AddMeetingResponse addMeeting(AddMeetingRequest request);

    List<MeetingDto> getMeetings(String userEmail);

    void removeMeeting(Integer id);

    void moveMeeting(MoveMeetingRequest request);

    FindTimeResponse findTime(FindTimeRequest request);
}
