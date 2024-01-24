import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { MainService } from '../main.service';

@Component({
  selector: 'app-find-time',
  templateUrl: './find-time.component.html',
  styleUrls: ['./find-time.component.scss']
})
export class FindTimeComponent implements OnInit{
  form: FormGroup;

  constructor(private mainService: MainService) {}

  ngOnInit(): void {
    let guests = new FormArray([]);

    this.form = new FormGroup({
      'guests': guests
    });
  }
  
  onAddGuest() {
    (<FormArray> this.form.get('guests')).push(
      new FormGroup({
        'email': new FormControl('', Validators.required),
      })
    );
  }

  get guests() {
    return (<FormArray>this.form.get('guests')).controls;
  }

  onSubmit() {
    const users = this.form.value['guests'].map(guest => guest.email);
    console.log(`users: ${users}`);
    this.mainService.findTime(users).subscribe({
      next: (response: any) => {
        alert(`Found time at ${response.startDate}, duration: ${response.duration}`)
        console.log(response);
      },error: (errorMsg) => {
        console.log(errorMsg);
      }
    });
  }
}
