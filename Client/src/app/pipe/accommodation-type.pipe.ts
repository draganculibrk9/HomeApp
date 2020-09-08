import {Pipe, PipeTransform} from '@angular/core';
import {AccommodationType} from "../model/accommodation-type.enum";

@Pipe({
  name: 'accommodationType'
})
export class AccommodationTypePipe implements PipeTransform {

  transform(value: number): AccommodationType {
    switch (value) {
      case 1:
        return AccommodationType.REPAIRS;
      case 2:
        return AccommodationType.HYGIENE;
      default:
        return AccommodationType.CATERING;
    }
  }

}
