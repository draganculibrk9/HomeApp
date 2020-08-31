// package: home.app.grpc
// file: accommodation_request_message.proto

import * as jspb from "google-protobuf";

export class AccommodationRequestMessage extends jspb.Message {
  getId(): number;
  setId(value: number): void;

  getHousehold(): number;
  setHousehold(value: number): void;

  getFiledOn(): number;
  setFiledOn(value: number): void;

  getRequestedFor(): number;
  setRequestedFor(value: number): void;

  getStatus(): AccommodationRequestMessage.StatusMap[keyof AccommodationRequestMessage.StatusMap];
  setStatus(value: AccommodationRequestMessage.StatusMap[keyof AccommodationRequestMessage.StatusMap]): void;

  getRating(): number;
  setRating(value: number): void;

  getAccommodation(): number;
  setAccommodation(value: number): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AccommodationRequestMessage.AsObject;
  static toObject(includeInstance: boolean, msg: AccommodationRequestMessage): AccommodationRequestMessage.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: AccommodationRequestMessage, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AccommodationRequestMessage;
  static deserializeBinaryFromReader(message: AccommodationRequestMessage, reader: jspb.BinaryReader): AccommodationRequestMessage;
}

export namespace AccommodationRequestMessage {
  export type AsObject = {
    id: number,
    household: number,
    filedOn: number,
    requestedFor: number,
    status: AccommodationRequestMessage.StatusMap[keyof AccommodationRequestMessage.StatusMap],
    rating: number,
    accommodation: number,
  }

  export interface StatusMap {
    UNKNOWN: 0;
    ACCEPTED: 1;
    REJECTED: 2;
    PENDING: 3;
  }

  export const Status: StatusMap;
}

