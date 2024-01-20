import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AddMeetingRequest} from "./add-meeting/dto/model"
import { AuthService } from "../auth/auth.service";

@Injectable({
    providedIn: 'root'
})
export class MainService {

    private meetingPath = "http://localhost:8080/api/meeting/";

    constructor(private http: HttpClient, private authService: AuthService) {}

    addMeeting(request: AddMeetingRequest): Observable<any> {
        request.users.push({email: this.authService.getUserEmail()});
        return this.http.post(this.meetingPath + 'add', request);
    }
}