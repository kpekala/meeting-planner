import { Component } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';

@Component({
  selector: 'app-add-meeting',
  templateUrl: './add-meeting.component.html',
  styleUrls: ['./add-meeting.component.scss']
})
export class AddMeetingComponent {

  id: number;
  editMode = false;
  meetingForm: FormGroup;

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.route.params.subscribe(
      (params: Params) => {
        this.id = +params['id'];
        this.editMode = params['id'] != null;
        this.initForm();
      }
    );
  }

  initForm() {
    let name = '';
    let startDate = new Date(Date.now());
    let minutes = 10;
    let guests = new FormArray([]);

    this.meetingForm = new FormGroup({
      'name': new FormControl(name, Validators.required),
      'startDate': new FormControl(startDate, Validators.required),
      'duration': new FormControl(minutes, Validators.required),
      'guests': guests
    });
  }

  onAddGuest() {
    (<FormArray>this.meetingForm.get('guests')).push(
      new FormGroup({
        'email': new FormControl('', Validators.required),
      })
    );
  }

  get guests() {
    return (<FormArray>this.meetingForm.get('guests')).controls;
  }

  onSubmit() {
    console.log(this.meetingForm.value);
  }
}
