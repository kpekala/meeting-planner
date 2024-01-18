package com.kpekala.meetingplanner.domain.meeting;

import com.kpekala.meetingplanner.domain.meeting.dto.AddMeetingRequest;
import com.kpekala.meetingplanner.domain.meeting.dto.AddMeetingResponse;

public interface MeetingService {

    AddMeetingResponse addMeeting(AddMeetingRequest request);
}
