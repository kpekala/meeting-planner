package com.kpekala.meetingplanner.domain.meeting;

import com.kpekala.meetingplanner.domain.meeting.dto.*;
import com.kpekala.meetingplanner.domain.meeting.exception.MeetingOverlapsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meeting")
@RequiredArgsConstructor
@Slf4j
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping("add")
    public AddMeetingResponse addMeeting(@RequestBody AddMeetingRequest request) {
        log.info("Add meeting request: {}", request.toString());
        return meetingService.addMeeting(request);
    }

    @GetMapping
    public List<MeetingDto> getMeetings(@RequestParam String email) {
        return meetingService.getMeetings(email);
    }

    @DeleteMapping
    public void deleteMeeting(@RequestParam int id) {
        meetingService.removeMeeting(id);
    }

    @PostMapping("move")
    public void moveMeetings(@RequestBody MoveMeetingRequest request) {
        meetingService.moveMeeting(request);
    }

    @GetMapping("find-time")
    public FindTimeResponse findTime(@RequestParam List<String> emails) {
        var request = new FindTimeRequest(emails.stream().map(UserDto::new).toList());
        return meetingService.findTime(request);
    }

    @ExceptionHandler({MeetingOverlapsException.class})
    public ErrorResponse handleMeetingOverlapsException(MeetingOverlapsException exception) {
        return ErrorResponse.create(exception, HttpStatusCode.valueOf(403), exception.getMessage());
    }
}
