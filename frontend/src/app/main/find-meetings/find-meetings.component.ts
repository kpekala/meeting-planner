import { Component } from '@angular/core';
import { MainService } from '../main.service';
import { MeetingDto } from '../model';
import { concatMap } from 'rxjs';

@Component({
  selector: 'app-find-meetings',
  templateUrl: './find-meetings.component.html',
  styleUrls: ['./find-meetings.component.scss']
})
export class FindMeetingsComponent {

  meetings: MeetingDto[];
  email: any;
  movingMeetingIndex = -1;

  newDate: string = null;

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

  onDeleteClick(index: number) {
    this.mainService.deleteMeeting(this.meetings[index].id).subscribe({
      next: () => {
        console.log("Deleted meeting");
        this.meetings.splice(index, 1);
      },error: (errorMsg) => {
        console.log("Error when deleting meeting");
      }
    });
  }

  onSwitchToMovingMeeting(index: number) {
    this.movingMeetingIndex = index;
  }

  onMoveMeeting(index: number) {
    const date = new Date(this.newDate).toISOString();
    this.mainService.moveMeeting(this.meetings[index].id, date)
    .pipe(concatMap(response => this.mainService.getMeetings(this.email)))
      .subscribe({
          next: (meetings) => {
            this.meetings = meetings;
            console.log("Moved meeting");
            alert("Moved meeting");
          },
          error: (errorMsg) => {
            console.log(errorMsg);
            alert("Failed to move meeting, detail: " + errorMsg.error.detail);
          }
      });
  }
}
