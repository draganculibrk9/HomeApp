import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import {DashboardComponent} from './dashboard.component';
import {ToolbarComponent} from './toolbar/toolbar.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {RouterModule} from '@angular/router';
import {FlexLayoutModule} from '@angular/flex-layout';
import {HouseholdComponent} from './household/household.component';
import {ServiceComponent} from './service/service.component';
import {MatListModule} from '@angular/material/list';
import {TransactionComponent} from './household/transaction/transaction.component';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {PipeModule} from '../pipe/pipe.module';
import {CreateTransactionComponent} from './household/create-transaction/create-transaction.component';
import {MatDialogModule} from '@angular/material/dialog';
import {ReactiveFormsModule} from '@angular/forms';
import {MatSelectModule} from '@angular/material/select';
import {DirectiveModule} from '../directives/directive.module';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatPaginatorModule} from '@angular/material/paginator';
import {EditTransactionComponent} from './household/edit-transaction/edit-transaction.component';
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import {UserComponent} from './user/user.component';
import {AccommodationRequestComponent} from './accommodation-request/accommodation-request.component';
import {ServiceDetailsComponent} from './service/service-details/service-details.component';
import {CreateServiceComponent} from './service/create-service/create-service.component';
import {MatStepperModule} from "@angular/material/stepper";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatSliderModule} from "@angular/material/slider";
import {Ng5SliderModule} from "ng5-slider";

@NgModule({
  declarations: [DashboardComponent, ToolbarComponent, HouseholdComponent, ServiceComponent, TransactionComponent, CreateTransactionComponent, EditTransactionComponent, UserComponent, AccommodationRequestComponent, ServiceDetailsComponent, CreateServiceComponent],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    RouterModule,
    FlexLayoutModule,
    MatListModule,
    MatCardModule,
    MatIconModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    PipeModule,
    MatDialogModule,
    ReactiveFormsModule,
    MatSelectModule,
    DirectiveModule,
    MatGridListModule,
    MatPaginatorModule,
    MatTableModule,
    MatSortModule,
    MatStepperModule,
    MatCheckboxModule,
    MatSliderModule,
    Ng5SliderModule,
  ],
  providers: [
    DatePipe
  ]
})
export class DashboardModule {
}
