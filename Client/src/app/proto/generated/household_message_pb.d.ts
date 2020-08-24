// package: home.app.grpc
// file: household_message.proto

import * as jspb from "google-protobuf";

export class HouseholdMessage extends jspb.Message {
  getId(): number;
  setId(value: number): void;

  getBalance(): number;
  setBalance(value: number): void;

  getOwner(): number;
  setOwner(value: number): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): HouseholdMessage.AsObject;
  static toObject(includeInstance: boolean, msg: HouseholdMessage): HouseholdMessage.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: HouseholdMessage, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): HouseholdMessage;
  static deserializeBinaryFromReader(message: HouseholdMessage, reader: jspb.BinaryReader): HouseholdMessage;
}

export namespace HouseholdMessage {
  export type AsObject = {
    id: number,
    balance: number,
    owner: number,
  }
}

