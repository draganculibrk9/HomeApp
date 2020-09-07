import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PeriodPipe } from './period.pipe';
import { AccommodationTypePipe } from './accommodation-type.pipe';
import { AccommodationRequestStatusPipe } from './accommodation-request-status.pipe';



@NgModule({
  declarations: [PeriodPipe, AccommodationTypePipe, AccommodationRequestStatusPipe],
  exports: [
    PeriodPipe,
    AccommodationTypePipe,
    AccommodationRequestStatusPipe
  ],
  imports: [
    CommonModule
  ],
  providers: [
    AccommodationTypePipe,
    AccommodationRequestStatusPipe
  ]
})
export class PipeModule { }
