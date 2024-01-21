import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { MainComponent } from './main/main.component';
import { AddMeetingComponent } from './main/add-meeting/add-meeting.component';
import { canActivateAuthContent, goToMainIfAuthenticated } from './auth/auth-guard';
import { FindMeetingsComponent } from './main/find-meetings/find-meetings.component';

const routes: Routes = [
  {path: 'auth', component: AuthComponent, canActivate: [goToMainIfAuthenticated]},
  {path: 'main', component: MainComponent, canActivate: [canActivateAuthContent],
  children: [
    {path: 'add', component: AddMeetingComponent},
    {path: 'find', component: FindMeetingsComponent},
    {path: '**', redirectTo: 'add'}
  ]},
  {path: '**', redirectTo: 'auth'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
