package com.kpekala.meetingplanner.domain.meeting;

import com.kpekala.meetingplanner.domain.meeting.entity.Meeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TimeFinderTest {

    private TimeFinder timeFinder;

    @BeforeEach
    public void beforeEach() {
        timeFinder = new TimeFinder();
    }

    @Test
    public void testFindTime_findTimeAfterOneMeeting() {
        List<Meeting> meetings = new ArrayList<>(
                List.of(new Meeting(1, "Test", ZonedDateTime.now(), 30, null))
        );

        var response = timeFinder.findTime(meetings);
        long actualEpochMilli = response.getStartDate().toInstant().toEpochMilli();
        long expectedEpochMilli = meetings.get(0).getStartDate().plusMinutes(30).toInstant().toEpochMilli();
        long delta = Math.abs(expectedEpochMilli - actualEpochMilli);

        assertTrue(delta < 10);    }

    @Test
    public void testFindTime_findTimeBetweenTwoMeetings() {
        List<Meeting> meetings = new ArrayList<>(List.of(
                new Meeting(1, "Test 1", ZonedDateTime.now(), 30, null),
                new Meeting(2, "Test 2", ZonedDateTime.now().plusMinutes(60), 30, null)
        ));

        var response = timeFinder.findTime(meetings);
        long actualEpochMilli = response.getStartDate().toInstant().toEpochMilli();
        long expectedEpochMilli = meetings.get(0).getStartDate().plusMinutes(30).toInstant().toEpochMilli();
        long delta = Math.abs(expectedEpochMilli - actualEpochMilli);

        System.out.println(delta);
        assertTrue(delta < 10);
    }

}
