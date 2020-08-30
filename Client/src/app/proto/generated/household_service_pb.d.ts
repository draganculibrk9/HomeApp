// package: home.app.grpc
// file: household_service.proto

import * as jspb from "google-protobuf";
import * as household_message_pb from "./household_message_pb";
import * as transaction_message_pb from "./transaction_message_pb";

export class HouseholdRequest extends jspb.Message {
  getOwner(): string;
  setOwner(value: string): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): HouseholdRequest.AsObject;
  static toObject(includeInstance: boolean, msg: HouseholdRequest): HouseholdRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: HouseholdRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): HouseholdRequest;
  static deserializeBinaryFromReader(message: HouseholdRequest, reader: jspb.BinaryReader): HouseholdRequest;
}

export namespace HouseholdRequest {
  export type AsObject = {
    owner: string,
  }
}

export class EditHouseholdRequest extends jspb.Message {
  getId(): number;
  setId(value: number): void;

  getBalance(): number;
  setBalance(value: number): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): EditHouseholdRequest.AsObject;
  static toObject(includeInstance: boolean, msg: EditHouseholdRequest): EditHouseholdRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: EditHouseholdRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): EditHouseholdRequest;
  static deserializeBinaryFromReader(message: EditHouseholdRequest, reader: jspb.BinaryReader): EditHouseholdRequest;
}

export namespace EditHouseholdRequest {
  export type AsObject = {
    id: number,
    balance: number,
  }
}

export class HouseholdResponse extends jspb.Message {
  hasHousehold(): boolean;
  clearHousehold(): void;
  getHousehold(): household_message_pb.HouseholdMessage | undefined;
  setHousehold(value?: household_message_pb.HouseholdMessage): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): HouseholdResponse.AsObject;
  static toObject(includeInstance: boolean, msg: HouseholdResponse): HouseholdResponse.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: HouseholdResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): HouseholdResponse;
  static deserializeBinaryFromReader(message: HouseholdResponse, reader: jspb.BinaryReader): HouseholdResponse;
}

export namespace HouseholdResponse {
  export type AsObject = {
    household?: household_message_pb.HouseholdMessage.AsObject,
  }
}

export class TransactionsRequest extends jspb.Message {
  getHouseholdId(): number;
  setHouseholdId(value: number): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): TransactionsRequest.AsObject;
  static toObject(includeInstance: boolean, msg: TransactionsRequest): TransactionsRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: TransactionsRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): TransactionsRequest;
  static deserializeBinaryFromReader(message: TransactionsRequest, reader: jspb.BinaryReader): TransactionsRequest;
}

export namespace TransactionsRequest {
  export type AsObject = {
    householdId: number,
  }
}

export class TransactionResponse extends jspb.Message {
  hasTransaction(): boolean;
  clearTransaction(): void;
  getTransaction(): transaction_message_pb.TransactionMessage | undefined;
  setTransaction(value?: transaction_message_pb.TransactionMessage): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): TransactionResponse.AsObject;
  static toObject(includeInstance: boolean, msg: TransactionResponse): TransactionResponse.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: TransactionResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): TransactionResponse;
  static deserializeBinaryFromReader(message: TransactionResponse, reader: jspb.BinaryReader): TransactionResponse;
}

export namespace TransactionResponse {
  export type AsObject = {
    transaction?: transaction_message_pb.TransactionMessage.AsObject,
  }
}

export class CreateOrEditTransactionRequest extends jspb.Message {
  getHouseholdId(): number;
  setHouseholdId(value: number): void;

  hasTransaction(): boolean;
  clearTransaction(): void;
  getTransaction(): transaction_message_pb.TransactionMessage | undefined;
  setTransaction(value?: transaction_message_pb.TransactionMessage): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): CreateOrEditTransactionRequest.AsObject;
  static toObject(includeInstance: boolean, msg: CreateOrEditTransactionRequest): CreateOrEditTransactionRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: CreateOrEditTransactionRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): CreateOrEditTransactionRequest;
  static deserializeBinaryFromReader(message: CreateOrEditTransactionRequest, reader: jspb.BinaryReader): CreateOrEditTransactionRequest;
}

export namespace CreateOrEditTransactionRequest {
  export type AsObject = {
    householdId: number,
    transaction?: transaction_message_pb.TransactionMessage.AsObject,
  }
}

export class GetOrDeleteTransactionRequest extends jspb.Message {
  getTransactionId(): number;
  setTransactionId(value: number): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): GetOrDeleteTransactionRequest.AsObject;
  static toObject(includeInstance: boolean, msg: GetOrDeleteTransactionRequest): GetOrDeleteTransactionRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: GetOrDeleteTransactionRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): GetOrDeleteTransactionRequest;
  static deserializeBinaryFromReader(message: GetOrDeleteTransactionRequest, reader: jspb.BinaryReader): GetOrDeleteTransactionRequest;
}

export namespace GetOrDeleteTransactionRequest {
  export type AsObject = {
    transactionId: number,
  }
}

export class SuccessResponse extends jspb.Message {
  getSuccess(): boolean;
  setSuccess(value: boolean): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): SuccessResponse.AsObject;
  static toObject(includeInstance: boolean, msg: SuccessResponse): SuccessResponse.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: SuccessResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): SuccessResponse;
  static deserializeBinaryFromReader(message: SuccessResponse, reader: jspb.BinaryReader): SuccessResponse;
}

export namespace SuccessResponse {
  export type AsObject = {
    success: boolean,
  }
}

