import {Pipe, PipeTransform} from '@angular/core';
import {AccommodationRequestStatus} from "../model/accommodation-request-status.enum";

@Pipe({
  name: 'accommodationRequestStatus'
})
export class AccommodationRequestStatusPipe implements PipeTransform {

  transform(value: number): AccommodationRequestStatus {
    switch (value) {
      case 1:
        return AccommodationRequestStatus.ACCEPTED;
      case 2:
        return AccommodationRequestStatus.REJECTED;
      default:
        return AccommodationRequestStatus.PENDING;
    }
  }

}
