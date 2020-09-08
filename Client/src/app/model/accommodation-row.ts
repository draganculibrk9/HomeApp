import {AccommodationType} from "./accommodation-type.enum";

export interface AccommodationRow {
  id: number,
  name: string,
  type: AccommodationType,
  price: number,
  available: boolean
}
