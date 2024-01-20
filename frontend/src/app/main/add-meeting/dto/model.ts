export interface AddMeetingRequest {
    name: string;
    startDate: string;
    durationMinutes: number;
    users: User[]
}

interface User {
    email: string;
}