import {Routes} from "@angular/router";
import {LoginComponent} from "../auth/login/login.component";
import {RegistrationComponent} from "../auth/registration/registration.component";


export const routes: Routes = [
  {
    path: 'login',
    pathMatch: 'full',
    component: LoginComponent
  },
  {
    path: 'register',
    pathMatch: 'full',
    component: RegistrationComponent
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/login'
  }
];
