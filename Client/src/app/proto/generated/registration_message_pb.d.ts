// package: home.app.grpc
// file: registration_message.proto

import * as jspb from "google-protobuf";

export class RegistrationMessage extends jspb.Message {
  getFirstName(): string;
  setFirstName(value: string): void;

  getLastName(): string;
  setLastName(value: string): void;

  getEmail(): string;
  setEmail(value: string): void;

  getPassword(): string;
  setPassword(value: string): void;

  getPhone(): string;
  setPhone(value: string): void;

  hasAddress(): boolean;
  clearAddress(): void;
  getAddress(): Address | undefined;
  setAddress(value?: Address): void;

  getRole(): RegistrationMessage.RoleMap[keyof RegistrationMessage.RoleMap];
  setRole(value: RegistrationMessage.RoleMap[keyof RegistrationMessage.RoleMap]): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): RegistrationMessage.AsObject;
  static toObject(includeInstance: boolean, msg: RegistrationMessage): RegistrationMessage.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: RegistrationMessage, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): RegistrationMessage;
  static deserializeBinaryFromReader(message: RegistrationMessage, reader: jspb.BinaryReader): RegistrationMessage;
}

export namespace RegistrationMessage {
  export type AsObject = {
    firstName: string,
    lastName: string,
    email: string,
    password: string,
    phone: string,
    address?: Address.AsObject,
    role: RegistrationMessage.RoleMap[keyof RegistrationMessage.RoleMap],
  }

  export interface RoleMap {
    UNKNOWN: 0;
    USER: 1;
    SERVICE_ADMINISTRATOR: 2;
  }

  export const Role: RoleMap;
}

export class Address extends jspb.Message {
  getCountry(): string;
  setCountry(value: string): void;

  getCity(): string;
  setCity(value: string): void;

  getStreet(): string;
  setStreet(value: string): void;

  getNumber(): number;
  setNumber(value: number): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): Address.AsObject;
  static toObject(includeInstance: boolean, msg: Address): Address.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: Address, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): Address;
  static deserializeBinaryFromReader(message: Address, reader: jspb.BinaryReader): Address;
}

export namespace Address {
  export type AsObject = {
    country: string,
    city: string,
    street: string,
    number: number,
  }
}

