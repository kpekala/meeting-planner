package com.kpekala.meetingplanner.domain.meeting;

import com.kpekala.meetingplanner.domain.meeting.dto.FindTimeResponse;
import com.kpekala.meetingplanner.domain.meeting.entity.Meeting;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Component
public class TimeFinder {

    public FindTimeResponse findTime(List<Meeting> meetings) {
        meetings = meetings.stream().distinct().toList();

        final long startTimeBound = Instant.now().toEpochMilli();
        final long endTimeBound = Instant.now().plusSeconds(60 * 60 * 24 * 2).toEpochMilli();

        LinkedList<Interval> intervals = new LinkedList<>(mapMeetingsToIntervals(meetings).stream()
                .filter(interval -> interval.start > startTimeBound && interval.start < endTimeBound)
                .sorted(Comparator.comparingLong(i -> i.start))
                .toList());

        LinkedList<Interval> merged = new LinkedList<>();

        while (intervals.size() > 0) {
            Interval interval = intervals.removeLast();
            Interval lastInterval = merged.get(merged.size() - 1);
            if (merged.size() > 0 && interval.start <= lastInterval.end){
                lastInterval = new Interval(lastInterval.start, Math.max(lastInterval.end, interval.end));
            }else {
                merged.add(interval);
            }
        }

        Instant startTime;
        long duration;

        if (merged.size() >= 2) {
            startTime = Instant.ofEpochMilli(merged.get(0).end);
            duration = merged.get(1).start - merged.get(0).end;
        }

        return new FindTimeResponse(ZonedDateTime.now(),20);
    }

    private List<Interval> mapMeetingsToIntervals(List<Meeting> meetings) {
        return meetings.stream().map(meeting ->
                new Interval(meeting.getStartDate().toInstant().toEpochMilli(),
                        meeting.getStartDate().plusMinutes(meeting.getDurationMinutes()).toInstant().toEpochMilli()))
                .toList();
    }

    record Interval(long start, long end) {

    }
}
