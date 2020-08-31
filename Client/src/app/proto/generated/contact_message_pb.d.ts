// package: home.app.grpc
// file: contact_message.proto

import * as jspb from "google-protobuf";
import * as registration_message_pb from "./registration_message_pb";

export class ContactMessage extends jspb.Message {
  getId(): number;
  setId(value: number): void;

  getEmail(): string;
  setEmail(value: string): void;

  getPhone(): string;
  setPhone(value: string): void;

  getWebsite(): string;
  setWebsite(value: string): void;

  hasAddress(): boolean;
  clearAddress(): void;
  getAddress(): registration_message_pb.Address | undefined;
  setAddress(value?: registration_message_pb.Address): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ContactMessage.AsObject;
  static toObject(includeInstance: boolean, msg: ContactMessage): ContactMessage.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: ContactMessage, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): ContactMessage;
  static deserializeBinaryFromReader(message: ContactMessage, reader: jspb.BinaryReader): ContactMessage;
}

export namespace ContactMessage {
  export type AsObject = {
    id: number,
    email: string,
    phone: string,
    website: string,
    address?: registration_message_pb.Address.AsObject,
  }
}

