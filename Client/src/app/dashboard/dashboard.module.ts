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
import {PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {MatPaginatorModule} from '@angular/material/paginator';
import { EditTransactionComponent } from './household/edit-transaction/edit-transaction.component';
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import { UserComponent } from './user/user.component';
import { AccommodationRequestComponent } from './accommodation-request/accommodation-request.component';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};


@NgModule({
  declarations: [DashboardComponent, ToolbarComponent, HouseholdComponent, ServiceComponent, TransactionComponent, CreateTransactionComponent, EditTransactionComponent, UserComponent, AccommodationRequestComponent],
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
    PerfectScrollbarModule,
    MatPaginatorModule,
    MatTableModule,
    MatSortModule
  ],
  providers: [
    DatePipe
  ]
})
export class DashboardModule {
}
