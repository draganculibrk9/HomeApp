import {Routes} from '@angular/router';
import {LoginComponent} from '../auth/login/login.component';
import {RegistrationComponent} from '../auth/registration/registration.component';
import {DashboardComponent} from '../dashboard/dashboard.component';
import {HouseholdComponent} from '../dashboard/household/household.component';
import {ServiceComponent} from '../dashboard/service/service.component';
import {UserComponent} from "../dashboard/user/user.component";


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
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: '/dashboard/household'
      },
      {
        path: 'household',
        pathMatch: 'full',
        component: HouseholdComponent
      },
      {
        path: 'service',
        pathMatch: 'full',
        component: ServiceComponent
      },
      {
        path: 'user',
        pathMatch: 'full',
        component: UserComponent
      }
    ]
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/login'
  }
];
