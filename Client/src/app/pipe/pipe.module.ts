import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PeriodPipe } from './period.pipe';
import { AccommodationTypePipe } from './accommodation-type.pipe';



@NgModule({
  declarations: [PeriodPipe, AccommodationTypePipe],
  exports: [
    PeriodPipe,
    AccommodationTypePipe
  ],
  imports: [
    CommonModule
  ],
  providers: [
    AccommodationTypePipe
  ]
})
export class PipeModule { }
