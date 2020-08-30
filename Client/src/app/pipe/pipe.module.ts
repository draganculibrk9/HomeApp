import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PeriodPipe } from './period.pipe';



@NgModule({
  declarations: [PeriodPipe],
  exports: [
    PeriodPipe
  ],
  imports: [
    CommonModule
  ]
})
export class PipeModule { }
