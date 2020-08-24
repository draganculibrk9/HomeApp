// package: home.app.grpc
// file: transaction_message.proto

import * as jspb from "google-protobuf";

export class TransactionMessage extends jspb.Message {
  getId(): number;
  setId(value: number): void;

  getName(): string;
  setName(value: string): void;

  getTransactionType(): TransactionMessage.TransactionTypeMap[keyof TransactionMessage.TransactionTypeMap];
  setTransactionType(value: TransactionMessage.TransactionTypeMap[keyof TransactionMessage.TransactionTypeMap]): void;

  getAmount(): number;
  setAmount(value: number): void;

  getDate(): number;
  setDate(value: number): void;

  getNumberOfTimes(): number;
  setNumberOfTimes(value: number): void;

  getPeriod(): PeriodMap[keyof PeriodMap];
  setPeriod(value: PeriodMap[keyof PeriodMap]): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): TransactionMessage.AsObject;
  static toObject(includeInstance: boolean, msg: TransactionMessage): TransactionMessage.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: TransactionMessage, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): TransactionMessage;
  static deserializeBinaryFromReader(message: TransactionMessage, reader: jspb.BinaryReader): TransactionMessage;
}

export namespace TransactionMessage {
  export type AsObject = {
    id: number,
    name: string,
    transactionType: TransactionMessage.TransactionTypeMap[keyof TransactionMessage.TransactionTypeMap],
    amount: number,
    date: number,
    numberOfTimes: number,
    period: PeriodMap[keyof PeriodMap],
  }

  export interface TransactionTypeMap {
    UNKNOWN: 0;
    INCOME: 1;
    EXPENDITURE: 2;
  }

  export const TransactionType: TransactionTypeMap;
}

export interface PeriodMap {
  UNKNOWN: 0;
  WEEK: 1;
  MONTH: 2;
  YEAR: 3;
}

export const Period: PeriodMap;

