// package: home.app.grpc
// file: services_service.proto

import * as jspb from "google-protobuf";
import * as accommodation_message_pb from "./accommodation_message_pb";
import * as accommodation_request_message_pb from "./accommodation_request_message_pb";
import * as service_message_pb from "./service_message_pb";
import * as household_service_pb from "./household_service_pb";

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

export class CreateOrEditServiceRequest extends jspb.Message {
  hasService(): boolean;
  clearService(): void;
  getService(): service_message_pb.ServiceMessage | undefined;
  setService(value?: service_message_pb.ServiceMessage): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): CreateOrEditServiceRequest.AsObject;
  static toObject(includeInstance: boolean, msg: CreateOrEditServiceRequest): CreateOrEditServiceRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: CreateOrEditServiceRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): CreateOrEditServiceRequest;
  static deserializeBinaryFromReader(message: CreateOrEditServiceRequest, reader: jspb.BinaryReader): CreateOrEditServiceRequest;
}

export namespace CreateOrEditServiceRequest {
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

export class CreateOrEditAccommodationRequest extends jspb.Message {
  getServiceId(): number;
  setServiceId(value: number): void;

  hasAccommodation(): boolean;
  clearAccommodation(): void;
  getAccommodation(): accommodation_message_pb.AccommodationMessage | undefined;
  setAccommodation(value?: accommodation_message_pb.AccommodationMessage): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): CreateOrEditAccommodationRequest.AsObject;
  static toObject(includeInstance: boolean, msg: CreateOrEditAccommodationRequest): CreateOrEditAccommodationRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: CreateOrEditAccommodationRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): CreateOrEditAccommodationRequest;
  static deserializeBinaryFromReader(message: CreateOrEditAccommodationRequest, reader: jspb.BinaryReader): CreateOrEditAccommodationRequest;
}

export namespace CreateOrEditAccommodationRequest {
  export type AsObject = {
    serviceId: number,
    accommodation?: accommodation_message_pb.AccommodationMessage.AsObject,
  }
}

export class DeleteAccommodationRequest extends jspb.Message {
  getServiceId(): number;
  setServiceId(value: number): void;

  getAccommodationId(): number;
  setAccommodationId(value: number): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): DeleteAccommodationRequest.AsObject;
  static toObject(includeInstance: boolean, msg: DeleteAccommodationRequest): DeleteAccommodationRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: DeleteAccommodationRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): DeleteAccommodationRequest;
  static deserializeBinaryFromReader(message: DeleteAccommodationRequest, reader: jspb.BinaryReader): DeleteAccommodationRequest;
}

export namespace DeleteAccommodationRequest {
  export type AsObject = {
    serviceId: number,
    accommodationId: number,
  }
}

export class AccommodationRequestResponse extends jspb.Message {
  hasAccommodationRequest(): boolean;
  clearAccommodationRequest(): void;
  getAccommodationRequest(): accommodation_request_message_pb.AccommodationRequestMessage | undefined;
  setAccommodationRequest(value?: accommodation_request_message_pb.AccommodationRequestMessage): void;

  getAccommodationName(): string;
  setAccommodationName(value: string): void;

  getHouseholdOwner(): string;
  setHouseholdOwner(value: string): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AccommodationRequestResponse.AsObject;
  static toObject(includeInstance: boolean, msg: AccommodationRequestResponse): AccommodationRequestResponse.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: AccommodationRequestResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AccommodationRequestResponse;
  static deserializeBinaryFromReader(message: AccommodationRequestResponse, reader: jspb.BinaryReader): AccommodationRequestResponse;
}

export namespace AccommodationRequestResponse {
  export type AsObject = {
    accommodationRequest?: accommodation_request_message_pb.AccommodationRequestMessage.AsObject,
    accommodationName: string,
    householdOwner: string,
  }
}

export class DecideAccommodationRequestRequest extends jspb.Message {
  getAccommodationRequestId(): number;
  setAccommodationRequestId(value: number): void;

  getStatus(): accommodation_request_message_pb.AccommodationRequestMessage.StatusMap[keyof accommodation_request_message_pb.AccommodationRequestMessage.StatusMap];
  setStatus(value: accommodation_request_message_pb.AccommodationRequestMessage.StatusMap[keyof accommodation_request_message_pb.AccommodationRequestMessage.StatusMap]): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): DecideAccommodationRequestRequest.AsObject;
  static toObject(includeInstance: boolean, msg: DecideAccommodationRequestRequest): DecideAccommodationRequestRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: DecideAccommodationRequestRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): DecideAccommodationRequestRequest;
  static deserializeBinaryFromReader(message: DecideAccommodationRequestRequest, reader: jspb.BinaryReader): DecideAccommodationRequestRequest;
}

export namespace DecideAccommodationRequestRequest {
  export type AsObject = {
    accommodationRequestId: number,
    status: accommodation_request_message_pb.AccommodationRequestMessage.StatusMap[keyof accommodation_request_message_pb.AccommodationRequestMessage.StatusMap],
  }
}

export class AccommodationRequestRequest extends jspb.Message {
  getHouseholdId(): number;
  setHouseholdId(value: number): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AccommodationRequestRequest.AsObject;
  static toObject(includeInstance: boolean, msg: AccommodationRequestRequest): AccommodationRequestRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: AccommodationRequestRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AccommodationRequestRequest;
  static deserializeBinaryFromReader(message: AccommodationRequestRequest, reader: jspb.BinaryReader): AccommodationRequestRequest;
}

export namespace AccommodationRequestRequest {
  export type AsObject = {
    householdId: number,
  }
}

