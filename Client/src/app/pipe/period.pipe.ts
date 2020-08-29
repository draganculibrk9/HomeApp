import {Pipe, PipeTransform} from '@angular/core';
import {Period} from '../proto/generated/transaction_message_pb';

@Pipe({
  name: 'period'
})
export class PeriodPipe implements PipeTransform {

  transform(value: number): string {
    switch (value) {
      case 1:
        return 'week';
      case 2:
        return 'month';
      case 3:
        return 'year';
      default:
        return 'unknown';
    }

  }

}
