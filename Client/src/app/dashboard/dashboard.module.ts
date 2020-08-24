import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ToolbarComponent } from './toolbar/toolbar.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {RouterModule} from "@angular/router";
import {FlexLayoutModule} from "@angular/flex-layout";
import { HouseholdComponent } from './household/household.component';
import { ServiceComponent } from './service/service.component';
import {MatListModule} from "@angular/material/list";
import { TransactionComponent } from './household/transaction/transaction.component';
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {PipeModule} from "../pipe/pipe.module";



@NgModule({
  declarations: [DashboardComponent, ToolbarComponent, HouseholdComponent, ServiceComponent, TransactionComponent],
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
    PipeModule
  ]
})
export class DashboardModule { }
