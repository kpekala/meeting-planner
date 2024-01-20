import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { tap } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private authPath = "http://localhost:8080/api/auth/";

    constructor(private http: HttpClient, private router: Router) {

    }

    public signin(email: string, password: string) {
        const body = {
            emailAddress: email,
            password: password
        };
        return this.http.post(this.authPath + 'signin', body)
            .pipe(tap((response) => {
                this.saveAuthDataLocally(response, email);
            }));
    }

    saveAuthDataLocally(response: any, email: string) {
        localStorage.setItem('token', response.token);
        localStorage.setItem('expiration', response.tokenExpirationDate);
        localStorage.setItem('email', email);
    }

    isUserLoggedIn(): boolean {
        const token = localStorage.getItem('token');
        const tokenExpirationDateString = localStorage.getItem('expiration');
        if (token == null || tokenExpirationDateString == null)
            return false;
        const tokenExpirationDate = Date.parse(tokenExpirationDateString);
        return tokenExpirationDate > Date.now();
    }

    getTokenHeader(): string { 
        return `Bearer ${localStorage.getItem('token')}`;
    }

    getUserEmail() {
        return localStorage.getItem('email');
    }
}