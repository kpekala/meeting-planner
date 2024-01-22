export interface MeetingDto {
    name: string;
    startDate: string;
    durationMinutes: number;
    userDtos: UserDto[];
    id: number;
}

interface UserDto {
    email: string;
}