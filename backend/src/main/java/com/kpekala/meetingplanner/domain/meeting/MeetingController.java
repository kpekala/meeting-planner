package com.kpekala.meetingplanner.domain.meeting;

import com.kpekala.meetingplanner.domain.meeting.dto.AddMeetingRequest;
import com.kpekala.meetingplanner.domain.meeting.dto.AddMeetingResponse;
import com.kpekala.meetingplanner.domain.meeting.dto.MeetingDto;
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

    @ExceptionHandler({MeetingOverlapsException.class})
    public ErrorResponse handleMeetingOverlapsException(MeetingOverlapsException exception) {
        return ErrorResponse.create(exception, HttpStatusCode.valueOf(403), exception.getMessage());
    }
}
