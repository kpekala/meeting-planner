export interface MeetingDto {
    // private String name;
    // private ZonedDateTime startDate;
    // private Integer durationMinutes;
    // private List<UserDto> userDtos;

    name: string;
    startDate: string;
    durationMinutes: number;
    userDtos: UserDto[]
}

interface UserDto {
    email: string;
}