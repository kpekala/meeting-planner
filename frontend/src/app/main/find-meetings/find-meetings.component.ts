import { Component } from '@angular/core';
import { MainService } from '../main.service';
import { MeetingDto } from '../model';

@Component({
  selector: 'app-find-meetings',
  templateUrl: './find-meetings.component.html',
  styleUrls: ['./find-meetings.component.scss']
})
export class FindMeetingsComponent {

  meetings: MeetingDto[];
  email: any;

  constructor(private mainService: MainService) {

  }

  onFindMeetingsClick() {
    this.mainService.getMeetings(this.email).subscribe({
      next: (meetings) => {
        this.meetings = meetings;
      }
    })
  }

  localDateTime(dateString: string) {
    const date = new Date(dateString);
    var time = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    return `${date.toDateString()} ${time}`;
  }

  onDeleteClick(meeting: MeetingDto) {
    this.mainService.deleteMeeting(meeting.id).subscribe({
      next: () => {
        console.log("Deleted meeting");
      },error: (errorMsg) => {
        console.log("Error when deleting meeting");
      }
    });
  }
}
