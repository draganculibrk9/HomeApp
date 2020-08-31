// package: home.app.grpc
// file: comment_message.proto

import * as jspb from "google-protobuf";
import * as accommodation_request_message_pb from "./accommodation_request_message_pb";

export class CommentMessage extends jspb.Message {
  getId(): number;
  setId(value: number): void;

  getCaption(): string;
  setCaption(value: string): void;

  getContent(): string;
  setContent(value: string): void;

  getDate(): number;
  setDate(value: number): void;

  getStatus(): accommodation_request_message_pb.AccommodationRequestMessage.StatusMap[keyof accommodation_request_message_pb.AccommodationRequestMessage.StatusMap];
  setStatus(value: accommodation_request_message_pb.AccommodationRequestMessage.StatusMap[keyof accommodation_request_message_pb.AccommodationRequestMessage.StatusMap]): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): CommentMessage.AsObject;
  static toObject(includeInstance: boolean, msg: CommentMessage): CommentMessage.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: CommentMessage, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): CommentMessage;
  static deserializeBinaryFromReader(message: CommentMessage, reader: jspb.BinaryReader): CommentMessage;
}

export namespace CommentMessage {
  export type AsObject = {
    id: number,
    caption: string,
    content: string,
    date: number,
    status: accommodation_request_message_pb.AccommodationRequestMessage.StatusMap[keyof accommodation_request_message_pb.AccommodationRequestMessage.StatusMap],
  }
}

