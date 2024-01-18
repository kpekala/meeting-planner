package com.kpekala.meetingplanner.domain.meeting.exception;

public class MeetingOverlapsException extends RuntimeException {
    public MeetingOverlapsException() {
        super("Meetings are overlapping!");
    }
}
