import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AddMeetingRequest} from "./add-meeting/dto/model"
import { AuthService } from "../auth/auth.service";
import { MeetingDto, MoveMeetingRequest } from "./model";

@Injectable({
    providedIn: 'root'
})
export class MainService {

    private meetingPath = "http://localhost:8080/api/meeting";

    constructor(private http: HttpClient, private authService: AuthService) {}

    addMeeting(request: AddMeetingRequest): Observable<any> {
        request.users.push({email: this.authService.getUserEmail()});
        return this.http.post(this.meetingPath + '/add', request);
    }

    getMeetings(email: string) {
        return this.http.get<MeetingDto[]>(this.meetingPath, {params: {'email': email}});
    }

    deleteMeeting(id: number) {
        return this.http.delete(this.meetingPath, {params: {'id': id}});
    }

    moveMeeting(id: number, newDate: string) {
        const body: MoveMeetingRequest = {
            id: id,
            newDate: newDate
        };

        return this.http.post(this.meetingPath + '/move', body);
    }

    findTime(users: string[]) {
        users.push(this.authService.getUserEmail());
        return this.http.get(this.meetingPath + '/find-time', {params: {'emails': users}});
    }

}