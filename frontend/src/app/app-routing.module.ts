import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { MainComponent } from './main/main.component';
import { AddMeetingComponent } from './main/add-meeting/add-meeting.component';

const routes: Routes = [
  {path: 'auth', component: AuthComponent},
  {path: 'main', component: MainComponent, children: [
    {path: 'add', component: AddMeetingComponent},
    {path: '**', redirectTo: 'add'}
  ]},
  {path: '**', redirectTo: 'auth'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
