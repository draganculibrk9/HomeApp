// package: home.app.grpc
// file: user_message.proto

import * as jspb from "google-protobuf";
import * as address_message_pb from "./address_message_pb";

export class UserMessage extends jspb.Message {
  getId(): number;
  setId(value: number): void;

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

  getRole(): UserMessage.RoleMap[keyof UserMessage.RoleMap];
  setRole(value: UserMessage.RoleMap[keyof UserMessage.RoleMap]): void;

  getBlocked(): boolean;
  setBlocked(value: boolean): void;

  hasAddress(): boolean;
  clearAddress(): void;
  getAddress(): address_message_pb.AddressMessage | undefined;
  setAddress(value?: address_message_pb.AddressMessage): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UserMessage.AsObject;
  static toObject(includeInstance: boolean, msg: UserMessage): UserMessage.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: UserMessage, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): UserMessage;
  static deserializeBinaryFromReader(message: UserMessage, reader: jspb.BinaryReader): UserMessage;
}

export namespace UserMessage {
  export type AsObject = {
    id: number,
    firstName: string,
    lastName: string,
    email: string,
    password: string,
    phone: string,
    role: UserMessage.RoleMap[keyof UserMessage.RoleMap],
    blocked: boolean,
    address?: address_message_pb.AddressMessage.AsObject,
  }

  export interface RoleMap {
    UNKNOWN: 0;
    USER: 1;
    SERVICE_ADMINISTRATOR: 2;
    SYSTEM_ADMINISTRATOR: 3;
  }

  export const Role: RoleMap;
}

