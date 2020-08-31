// package: home.app.grpc
// file: services_service.proto

import * as jspb from "google-protobuf";
import * as accommodation_message_pb from "./accommodation_message_pb";
import * as accommodation_request_message_pb from "./accommodation_request_message_pb";
import * as comment_message_pb from "./comment_message_pb";
import * as contact_message_pb from "./contact_message_pb";
import * as service_message_pb from "./service_message_pb";
import * as registration_message_pb from "./registration_message_pb";

export class ServiceRequest extends jspb.Message {
  getId(): number;
  setId(value: number): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ServiceRequest.AsObject;
  static toObject(includeInstance: boolean, msg: ServiceRequest): ServiceRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: ServiceRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): ServiceRequest;
  static deserializeBinaryFromReader(message: ServiceRequest, reader: jspb.BinaryReader): ServiceRequest;
}

export namespace ServiceRequest {
  export type AsObject = {
    id: number,
  }
}

export class ServiceByAdministratorRequest extends jspb.Message {
  getAdministrator(): string;
  setAdministrator(value: string): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ServiceByAdministratorRequest.AsObject;
  static toObject(includeInstance: boolean, msg: ServiceByAdministratorRequest): ServiceByAdministratorRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: ServiceByAdministratorRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): ServiceByAdministratorRequest;
  static deserializeBinaryFromReader(message: ServiceByAdministratorRequest, reader: jspb.BinaryReader): ServiceByAdministratorRequest;
}

export namespace ServiceByAdministratorRequest {
  export type AsObject = {
    administrator: string,
  }
}

export class ServiceResponse extends jspb.Message {
  hasService(): boolean;
  clearService(): void;
  getService(): service_message_pb.ServiceMessage | undefined;
  setService(value?: service_message_pb.ServiceMessage): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ServiceResponse.AsObject;
  static toObject(includeInstance: boolean, msg: ServiceResponse): ServiceResponse.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: ServiceResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): ServiceResponse;
  static deserializeBinaryFromReader(message: ServiceResponse, reader: jspb.BinaryReader): ServiceResponse;
}

export namespace ServiceResponse {
  export type AsObject = {
    service?: service_message_pb.ServiceMessage.AsObject,
  }
}

export class SearchServiceRequest extends jspb.Message {
  getName(): string;
  setName(value: string): void;

  getType(): accommodation_message_pb.AccommodationMessage.AccommodationTypeMap[keyof accommodation_message_pb.AccommodationMessage.AccommodationTypeMap];
  setType(value: accommodation_message_pb.AccommodationMessage.AccommodationTypeMap[keyof accommodation_message_pb.AccommodationMessage.AccommodationTypeMap]): void;

  getMinimumPrice(): number;
  setMinimumPrice(value: number): void;

  getMaximumPrice(): number;
  setMaximumPrice(value: number): void;

  getCity(): string;
  setCity(value: string): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): SearchServiceRequest.AsObject;
  static toObject(includeInstance: boolean, msg: SearchServiceRequest): SearchServiceRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: SearchServiceRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): SearchServiceRequest;
  static deserializeBinaryFromReader(message: SearchServiceRequest, reader: jspb.BinaryReader): SearchServiceRequest;
}

export namespace SearchServiceRequest {
  export type AsObject = {
    name: string,
    type: accommodation_message_pb.AccommodationMessage.AccommodationTypeMap[keyof accommodation_message_pb.AccommodationMessage.AccommodationTypeMap],
    minimumPrice: number,
    maximumPrice: number,
    city: string,
  }
}

export class AccommodationResponse extends jspb.Message {
  hasAccommodation(): boolean;
  clearAccommodation(): void;
  getAccommodation(): accommodation_message_pb.AccommodationMessage | undefined;
  setAccommodation(value?: accommodation_message_pb.AccommodationMessage): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AccommodationResponse.AsObject;
  static toObject(includeInstance: boolean, msg: AccommodationResponse): AccommodationResponse.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: AccommodationResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AccommodationResponse;
  static deserializeBinaryFromReader(message: AccommodationResponse, reader: jspb.BinaryReader): AccommodationResponse;
}

export namespace AccommodationResponse {
  export type AsObject = {
    accommodation?: accommodation_message_pb.AccommodationMessage.AsObject,
  }
}

