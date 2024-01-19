import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './auth/auth.component';
import { ReactiveFormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { MainComponent } from './main/main.component';
import { HeaderComponent } from './main/header/header.component';
import { AddMeetingComponent } from './main/add-meeting/add-meeting.component';


@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    MainComponent,
    HeaderComponent,
    AddMeetingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
