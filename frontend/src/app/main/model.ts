export interface MeetingDto {
    name: string;
    startDate: string;
    durationMinutes: number;
    userDtos: UserDto[];
    id: number;
}

export interface MoveMeetingRequest {
    id: number;
    newDate: string;
}

interface UserDto {
    email: string;
}