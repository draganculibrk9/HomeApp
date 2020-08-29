import {Directive, Input, OnInit} from '@angular/core';
import {NgControl} from "@angular/forms";

@Directive({
  selector: '[disableControl]'
})
export class DisableFormControlDirective implements OnInit {
  private disabled: boolean;

  @Input() set disableControl(condition: boolean) {
    if (this.disabled !== undefined) {
      this.toggleForm(condition);
    }
    this.disabled = condition;
  }

  constructor(private control: NgControl) {
  }

  private toggleForm(condition: boolean) {
    const action = condition ? 'disable' : 'enable';
    this.control.control[action]();
  }

  ngOnInit(): void {
    this.toggleForm(this.disabled);
  }
}
