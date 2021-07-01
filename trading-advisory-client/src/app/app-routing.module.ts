import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from './register/register.component';
import {RegisterResultComponent} from './register/register-result/register-result.component';

const routes: Routes = [
  {path: 'app-registration', component: RegisterComponent},
  {path: '', component: RegisterComponent},
  {path: 'app-register-result', component: RegisterResultComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
