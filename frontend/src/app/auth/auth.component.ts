import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent {
  authForm: FormGroup;
  isLoginView = true;
  isLoading = false;

  constructor(private authService: AuthService, private router: Router) {

  }
  
  ngOnInit(): void {
    this.authForm = new FormGroup({
      'email': new FormControl('', Validators.required),
      'password': new FormControl('', Validators.required)
    });  
  }

  onLoginButtonClicked() {
    const email = this.authForm.get('email').value;
    const password = this.authForm.get('password').value;
    console.log(email + password);
    this.authService.signin(email, password).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.router.navigate(['./main']);
      },
      error: (error) => {
        this.isLoading = false;
      }
    })
  }
}
