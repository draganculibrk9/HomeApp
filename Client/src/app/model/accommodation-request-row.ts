import {AccommodationRequestStatus} from "./accommodation-request-status.enum";

export interface AccommodationRequestRow {
  accommodation: string,
  filedOn: number,
  id: number,
  owner?: string,
  requestedFor: number,
  status: AccommodationRequestStatus
}
