package com.kpekala.meetingplanner.domain.meeting;

import com.kpekala.meetingplanner.domain.meeting.dto.AddMeetingRequest;
import com.kpekala.meetingplanner.domain.meeting.dto.UserDto;
import com.kpekala.meetingplanner.domain.meeting.entity.Meeting;
import com.kpekala.meetingplanner.domain.meeting.exception.MeetingOverlapsException;
import com.kpekala.meetingplanner.domain.user.UserRepository;
import com.kpekala.meetingplanner.domain.user.entity.Role;
import com.kpekala.meetingplanner.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeetingServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MeetingServiceImpl meetingService;

    private final String asiaEmail = "asia@t.pl";
    private final String basiaEmail = "basia@t.pl";

    @Test
    public void testAddMeeting_simpleCase() {
        // Assume
        var meetingStartDate = ZonedDateTime.now().plusMinutes(60);
        var meetingUsers = List.of(new UserDto(asiaEmail), new UserDto(basiaEmail));
        var request = new AddMeetingRequest("Meeting 1", meetingStartDate, 30, meetingUsers);

        var asiaMeetings = List.of(new Meeting(1, "Asia meeting",
                ZonedDateTime.now().plusMinutes(120), 30, new ArrayList<>()));


        var asia = new User(1, "asia@t.pl", "123456", null, asiaMeetings);
        asiaMeetings.get(0).setUsers(List.of(asia));
        var basia = new User(2, "basia@t.pl", "123456", null, List.of());

        when(userRepository.findByEmail(asiaEmail)).thenReturn(Optional.of(asia));
        when(userRepository.findByEmail(basiaEmail)).thenReturn(Optional.of(basia));

        // Act
        var response = meetingService.addMeeting(request);

        // Assert
        assertTrue(response.isSuccessful());
    }


    @Test
    public void testAddMeeting_whenMeetingsOverlap_throwException_case1() {
        // Assume
        var meetingStartDate = ZonedDateTime.now().plusMinutes(60);
        var meetingUsers = List.of(new UserDto(asiaEmail), new UserDto(basiaEmail));
        var request = new AddMeetingRequest("Meeting 1", meetingStartDate, 30, meetingUsers);

        var asiaMeetings = List.of(new Meeting(1, "Asia meeting",
                ZonedDateTime.now().plusMinutes(30), 60, new ArrayList<>()));

        var asia = new User(1, "asia@t.pl", "123456", null, asiaMeetings);
        asiaMeetings.get(0).setUsers(List.of(asia));

        // Act
        when(userRepository.findByEmail(asiaEmail)).thenReturn(Optional.of(asia));

        // Assert
        assertThrows(MeetingOverlapsException.class, () -> meetingService.addMeeting(request));
    }

    @Test
    public void testAddMeeting_whenMeetingsOverlap_throwException_case2() {
        // Assume
        var meetingStartDate = ZonedDateTime.now().plusMinutes(60);
        var meetingUsers = List.of(new UserDto(asiaEmail), new UserDto(basiaEmail));
        var request = new AddMeetingRequest("Meeting 1", meetingStartDate, 30, meetingUsers);

        var asiaMeetings = List.of(new Meeting(1, "Asia meeting",
                ZonedDateTime.now().plusMinutes(70), 60, new ArrayList<>()));

        var asia = new User(1, "asia@t.pl", "123456", null, asiaMeetings);
        asiaMeetings.get(0).setUsers(List.of(asia));

        // Act
        when(userRepository.findByEmail(asiaEmail)).thenReturn(Optional.of(asia));

        // Assert
        assertThrows(MeetingOverlapsException.class, () -> meetingService.addMeeting(request));
    }

    @Test
    public void testAddMeeting_whenMeetingsOverlap_throwException_case3() {
        // Assume
        var meetingStartDate = ZonedDateTime.now().plusMinutes(60);
        var meetingUsers = List.of(new UserDto(asiaEmail), new UserDto(basiaEmail));
        var request = new AddMeetingRequest("Meeting 1", meetingStartDate, 30, meetingUsers);

        var asiaMeetings = List.of(new Meeting(1, "Asia meeting",
                ZonedDateTime.now(), 120, new ArrayList<>()));

        var asia = new User(1, "asia@t.pl", "123456", null, asiaMeetings);
        asiaMeetings.get(0).setUsers(List.of(asia));

        // Act
        when(userRepository.findByEmail(asiaEmail)).thenReturn(Optional.of(asia));

        // Assert
        assertThrows(MeetingOverlapsException.class, () -> meetingService.addMeeting(request));
    }
}
