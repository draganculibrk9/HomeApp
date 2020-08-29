import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DisableFormControlDirective} from "./disable-form-control.directive";



@NgModule({
  declarations: [DisableFormControlDirective],
  imports: [
    CommonModule
  ],
  exports: [
    DisableFormControlDirective
  ]
})
export class DirectiveModule { }
