// package: home.app.grpc
// file: accommodation_message.proto

import * as jspb from "google-protobuf";

export class AccommodationMessage extends jspb.Message {
  getId(): number;
  setId(value: number): void;

  getName(): string;
  setName(value: string): void;

  getType(): AccommodationMessage.AccommodationTypeMap[keyof AccommodationMessage.AccommodationTypeMap];
  setType(value: AccommodationMessage.AccommodationTypeMap[keyof AccommodationMessage.AccommodationTypeMap]): void;

  getPrice(): number;
  setPrice(value: number): void;

  getAvailable(): boolean;
  setAvailable(value: boolean): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AccommodationMessage.AsObject;
  static toObject(includeInstance: boolean, msg: AccommodationMessage): AccommodationMessage.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: AccommodationMessage, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AccommodationMessage;
  static deserializeBinaryFromReader(message: AccommodationMessage, reader: jspb.BinaryReader): AccommodationMessage;
}

export namespace AccommodationMessage {
  export type AsObject = {
    id: number,
    name: string,
    type: AccommodationMessage.AccommodationTypeMap[keyof AccommodationMessage.AccommodationTypeMap],
    price: number,
    available: boolean,
  }

  export interface AccommodationTypeMap {
    UNKNOWN: 0;
    REPAIRS: 1;
    HYGIENE: 2;
    CATERING: 3;
  }

  export const AccommodationType: AccommodationTypeMap;
}

