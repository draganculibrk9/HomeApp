// package: home.app.grpc
// file: address_message.proto

import * as jspb from "google-protobuf";

export class AddressMessage extends jspb.Message {
  getId(): number;
  setId(value: number): void;

  getCountry(): string;
  setCountry(value: string): void;

  getCity(): string;
  setCity(value: string): void;

  getStreet(): string;
  setStreet(value: string): void;

  getNumber(): number;
  setNumber(value: number): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AddressMessage.AsObject;
  static toObject(includeInstance: boolean, msg: AddressMessage): AddressMessage.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: AddressMessage, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AddressMessage;
  static deserializeBinaryFromReader(message: AddressMessage, reader: jspb.BinaryReader): AddressMessage;
}

export namespace AddressMessage {
  export type AsObject = {
    id: number,
    country: string,
    city: string,
    street: string,
    number: number,
  }
}

