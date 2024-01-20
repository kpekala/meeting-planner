import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AddMeetingRequest} from "./add-meeting/dto/model"

@Injectable({
    providedIn: 'root'
})
export class MainService {

    private meetingPath = "http://localhost:8080/api/meeting/";

    constructor(private http: HttpClient) {}

    addMeeting(request: AddMeetingRequest): Observable<any> {
        return this.http.post(this.meetingPath + 'add', request);
    }
}