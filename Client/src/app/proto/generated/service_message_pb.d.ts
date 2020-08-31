// package: home.app.grpc
// file: service_message.proto

import * as jspb from "google-protobuf";
import * as contact_message_pb from "./contact_message_pb";

export class ServiceMessage extends jspb.Message {
  getId(): number;
  setId(value: number): void;

  getAdministrator(): string;
  setAdministrator(value: string): void;

  getName(): string;
  setName(value: string): void;

  hasContact(): boolean;
  clearContact(): void;
  getContact(): contact_message_pb.ContactMessage | undefined;
  setContact(value?: contact_message_pb.ContactMessage): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ServiceMessage.AsObject;
  static toObject(includeInstance: boolean, msg: ServiceMessage): ServiceMessage.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: ServiceMessage, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): ServiceMessage;
  static deserializeBinaryFromReader(message: ServiceMessage, reader: jspb.BinaryReader): ServiceMessage;
}

export namespace ServiceMessage {
  export type AsObject = {
    id: number,
    administrator: string,
    name: string,
    contact?: contact_message_pb.ContactMessage.AsObject,
  }
}

