import {Routes} from '@angular/router';
import {LoginComponent} from '../auth/login/login.component';
import {RegistrationComponent} from '../auth/registration/registration.component';
import {DashboardComponent} from '../dashboard/dashboard.component';
import {HouseholdComponent} from '../dashboard/household/household.component';
import {ServiceComponent} from '../dashboard/service/service.component';
import {UserComponent} from "../dashboard/user/user.component";
import {AccommodationRequestComponent} from "../dashboard/accommodation-request/accommodation-request.component";
import {AuthGuard} from "../guards/auth.guard";


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
    canActivateChild: [AuthGuard],
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: '/dashboard/household'
      },
      {
        path: 'household',
        pathMatch: 'full',
        component: HouseholdComponent,
        data: {
          roles: [
            'USER'
          ]
        }
      },
      {
        path: 'service',
        pathMatch: 'full',
        component: ServiceComponent,
        data: {
          roles: [
            'USER',
            'SERVICE_ADMINISTRATOR'
          ]
        }
      },
      {
        path: 'user',
        pathMatch: 'full',
        component: UserComponent,
        data: {
          roles: [
            'SYSTEM_ADMINISTRATOR'
          ]
        }
      },
      {
        path: 'accommodation-request',
        pathMatch: 'full',
        component: AccommodationRequestComponent,
        data: {
          roles: [
            'USER',
            'SERVICE_ADMINISTRATOR'
          ]
        }
      }
    ]
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/login'
  }
];
