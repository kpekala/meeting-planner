package com.kpekala.meetingplanner.domain.meeting;

import com.kpekala.meetingplanner.domain.meeting.dto.AddMeetingRequest;
import com.kpekala.meetingplanner.domain.meeting.dto.AddMeetingResponse;
import com.kpekala.meetingplanner.domain.meeting.dto.MeetingDto;
import com.kpekala.meetingplanner.domain.meeting.dto.UserDto;
import com.kpekala.meetingplanner.domain.meeting.entity.Meeting;
import com.kpekala.meetingplanner.domain.meeting.exception.MeetingOverlapsException;
import com.kpekala.meetingplanner.domain.user.UserRepository;
import com.kpekala.meetingplanner.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MeetingServiceImpl implements MeetingService{

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;

    @Override
    @Transactional
    public AddMeetingResponse addMeeting(AddMeetingRequest request) {
        var users = request.getUsers();

        validateUsersCanJoinMeeting(users, request.getStartDate(), request.getDurationMinutes());

        var meeting = prepareMeeting(request);
        meetingRepository.save(meeting);
        meeting.getUsers().forEach(user -> {
            user.addMeeting(meeting);
            userRepository.save(user);
        });

        return new AddMeetingResponse(true);
    }

    @Override
    @Transactional
    public List<MeetingDto> getMeetings(String userEmail) {
        var user = userRepository.findByEmail(userEmail).orElseThrow();
        return user.getMeetings().stream().map(this::mapToMeetingDto).toList();
    }

    @Override
    @Transactional
    public void removeMeeting(Integer id) {
        meetingRepository.deleteById(id);
    }

    private MeetingDto mapToMeetingDto(Meeting meeting) {
        var meetingDto = new MeetingDto();
        meetingDto.setName(meeting.getName());
        meetingDto.setStartDate(meeting.getStartDate());
        meetingDto.setDurationMinutes(meeting.getDurationMinutes());
        meetingDto.setUserDtos(meeting.getUsers().stream().map(user -> new UserDto(user.getEmail())).toList());
        meetingDto.setId(meeting.getId());

        return meetingDto;
    }

    private Meeting prepareMeeting(AddMeetingRequest request) {
        var meeting = new Meeting();
        meeting.setName(request.getName());
        meeting.setStartDate(request.getStartDate());
        meeting.setDurationMinutes(request.getDurationMinutes());

        var users = new ArrayList<User>();
        request.getUsers().stream().distinct().forEach(guest -> {
            var guestOptional = userRepository.findByEmail(guest.getEmail());
            guestOptional.ifPresent(users::add);
        });
        meeting.setUsers(users);

        return meeting;
    }

    private void validateUsersCanJoinMeeting(List<UserDto> users, ZonedDateTime startDate, int durationMinutes) {
        users.forEach(userDto -> {
            validateUserCanJoinMeeting(userDto, startDate, durationMinutes);
        });
    }

    private void validateUserCanJoinMeeting(UserDto userDto, ZonedDateTime startDate, int durationMinutes) {
        var userOptional = userRepository.findByEmail(userDto.getEmail());

        // If user is not present, we can skip validation
        if(userOptional.isEmpty())
            return;

        var meetingOptional = userOptional.get().getMeetings().stream()
                .filter(meeting -> meetingOverlaps(meeting, startDate, durationMinutes)).findFirst();

        if (meetingOptional.isPresent())
            throw new MeetingOverlapsException();
    }

    private boolean meetingOverlaps(Meeting meeting, ZonedDateTime startDate, int durationMinutes) {
        boolean overlaps;
        var endDate = startDate.plusMinutes(durationMinutes);
        var meetingEndDate = meeting.getStartDate().plusMinutes(meeting.getDurationMinutes());

        // first case
        overlaps = (startDate.isAfter(meeting.getStartDate()) || startDate.isEqual(meeting.getStartDate()))
                && (startDate.isBefore(meetingEndDate) || startDate.isEqual(meeting.getStartDate()));

        if(overlaps)
            return true;

        // second case
        overlaps = (endDate.isAfter(meeting.getStartDate()) || endDate.isEqual(meeting.getStartDate()))
                && (endDate.isBefore(meetingEndDate)  || endDate.isEqual(meetingEndDate));

        if(overlaps)
            return true;

        // third case
        overlaps = (startDate.isBefore(meeting.getStartDate()) || startDate.isEqual(meeting.getStartDate()))
                && (endDate.isAfter(meetingEndDate) || endDate.isEqual(meetingEndDate));

        return overlaps;
    }
}
