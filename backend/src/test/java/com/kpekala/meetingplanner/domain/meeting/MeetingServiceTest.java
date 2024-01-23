package com.kpekala.meetingplanner.domain.meeting;

import com.kpekala.meetingplanner.domain.meeting.dto.AddMeetingRequest;
import com.kpekala.meetingplanner.domain.meeting.dto.MoveMeetingRequest;
import com.kpekala.meetingplanner.domain.meeting.dto.UserDto;
import com.kpekala.meetingplanner.domain.meeting.entity.Meeting;
import com.kpekala.meetingplanner.domain.meeting.exception.MeetingOverlapsException;
import com.kpekala.meetingplanner.domain.user.UserRepository;
import com.kpekala.meetingplanner.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeetingServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MeetingRepository meetingRepository;

    @InjectMocks
    private MeetingServiceImpl meetingService;

    @Captor
    ArgumentCaptor<Meeting> meetingCaptor;

    private final String asiaEmail = "asia@t.pl";
    private final String basiaEmail = "basia@t.pl";

    @Test
    public void testAddMeeting_simpleCase() {
        // Assume
        var meetingStartDate = ZonedDateTime.now().plusMinutes(60);
        var meetingUsers = List.of(new UserDto(asiaEmail), new UserDto(basiaEmail));
        var request = new AddMeetingRequest("Meeting 1", meetingStartDate, 30, meetingUsers);

        var asiaMeetings = new ArrayList<Meeting>();
        asiaMeetings.add(new Meeting(1, "Asia meeting", ZonedDateTime.now().plusMinutes(120),
                30, new ArrayList<>()));

        var asia = new User(1, "asia@t.pl", "123456", null, asiaMeetings);
        asiaMeetings.get(0).setUsers(List.of(asia));
        var basia = new User(2, "basia@t.pl", "123456", null, new ArrayList<>());

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

        when(userRepository.findByEmail(asiaEmail)).thenReturn(Optional.of(asia));

        // Act & Assert
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

        when(userRepository.findByEmail(asiaEmail)).thenReturn(Optional.of(asia));

        // Act & Assert
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
        when(userRepository.findByEmail(asiaEmail)).thenReturn(Optional.of(asia));

        // Act & Assert
        assertThrows(MeetingOverlapsException.class, () -> meetingService.addMeeting(request));
    }

    @Test
    public void testGetMeetings() {
        // Assume
        var asiaMeetings = List.of(new Meeting(1, "Asia meeting 1", ZonedDateTime.now(), 30, new ArrayList<>()));
        var asia = new User(1, "asia@t.pl", "123456", null, asiaMeetings);
        asia.setMeetings(asiaMeetings);

        when(userRepository.findByEmail(asiaEmail)).thenReturn(Optional.of(asia));

        // Act
        var receivedMeetings = meetingService.getMeetings(asiaEmail);

        // Assert
        assertThat(receivedMeetings).hasSize(asiaMeetings.size());
        var meeting1 = receivedMeetings.get(0);
        assertEquals("Asia meeting 1", meeting1.getName());
        assertEquals(30, meeting1.getDurationMinutes());
        assertEquals(asiaMeetings.get(0).getStartDate(), meeting1.getStartDate());
    }

    @Test
    public void testMoveMeeting() {
        // Assume
        var asiaMeetings = List.of(new Meeting(1, "Asia meeting 1", ZonedDateTime.now(), 30, new ArrayList<>()));
        var asia = new User(1, "asia@t.pl", "123456", null, asiaMeetings);
        asia.setMeetings(asiaMeetings);
        asiaMeetings.get(0).setUsers(List.of(asia));

        var request = new MoveMeetingRequest(1, ZonedDateTime.now().plusMinutes(15));

        when(meetingRepository.findById(1)).thenReturn(Optional.of(asiaMeetings.get(0)));

        // Act
        meetingService.moveMeeting(request);

        // Assert
        verify(meetingRepository).save(meetingCaptor.capture());
        verify(meetingRepository, times(1)).deleteById(1);

        var capturedMeeting = meetingCaptor.getValue();
        assertEquals(request.getNewDate(), capturedMeeting.getStartDate());
    }

    @Test
    public void testMoveMeeting_whenNewMeetingDateOverlaps_throwException() {
        // Assume
        var asiaMeetings = List.of(
                new Meeting(1, "Asia meeting 1", ZonedDateTime.now(), 30, new ArrayList<>()),
                new Meeting(2, "Asia meeting 2", ZonedDateTime.now().plusMinutes(60), 30, new ArrayList<>())
        );
        var asia = new User(1, "asia@t.pl", "123456", null, asiaMeetings);
        asia.setMeetings(asiaMeetings);
        asiaMeetings.forEach(meeting -> meeting.setUsers(List.of(asia)));

        var request = new MoveMeetingRequest(1, ZonedDateTime.now().plusMinutes(60));

        when(meetingRepository.findById(1)).thenReturn(Optional.of(asiaMeetings.get(0)));
        when(userRepository.findByEmail(asiaEmail)).thenReturn(Optional.of(asia));

        // Act & Assert
        assertThrows(MeetingOverlapsException.class, () -> meetingService.moveMeeting(request));
    }

}
