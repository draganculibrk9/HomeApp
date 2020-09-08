// package: home.app.grpc
// file: services_service.proto

import * as services_service_pb from "./services_service_pb";
import * as household_service_pb from "./household_service_pb";
import {grpc} from "@improbable-eng/grpc-web";

type ServicesServiceSearchServices = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: true;
  readonly requestType: typeof services_service_pb.SearchServiceRequest;
  readonly responseType: typeof services_service_pb.ServiceResponse;
};

type ServicesServiceGetService = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof services_service_pb.ServiceRequest;
  readonly responseType: typeof services_service_pb.ServiceResponse;
};

type ServicesServiceGetServicesByAdministrator = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: true;
  readonly requestType: typeof services_service_pb.ServiceByAdministratorRequest;
  readonly responseType: typeof services_service_pb.ServiceResponse;
};

type ServicesServiceCreateService = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof services_service_pb.CreateOrEditServiceRequest;
  readonly responseType: typeof services_service_pb.ServiceResponse;
};

type ServicesServiceEditService = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof services_service_pb.CreateOrEditServiceRequest;
  readonly responseType: typeof services_service_pb.ServiceResponse;
};

type ServicesServiceDeleteService = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof services_service_pb.ServiceRequest;
  readonly responseType: typeof household_service_pb.SuccessResponse;
};

type ServicesServiceGetAccommodations = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: true;
  readonly requestType: typeof services_service_pb.ServiceRequest;
  readonly responseType: typeof services_service_pb.AccommodationResponse;
};

type ServicesServiceCreateAccommodation = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof services_service_pb.CreateOrEditAccommodationRequest;
  readonly responseType: typeof services_service_pb.AccommodationResponse;
};

type ServicesServiceEditAccommodation = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof services_service_pb.CreateOrEditAccommodationRequest;
  readonly responseType: typeof services_service_pb.AccommodationResponse;
};

type ServicesServiceDeleteAccommodation = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof services_service_pb.DeleteAccommodationRequest;
  readonly responseType: typeof household_service_pb.SuccessResponse;
};

type ServicesServiceGetAccommodationRequestsForAdministrator = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: true;
  readonly requestType: typeof services_service_pb.ServiceByAdministratorRequest;
  readonly responseType: typeof services_service_pb.AccommodationRequestResponse;
};

type ServicesServiceGetAccommodationRequests = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: true;
  readonly requestType: typeof services_service_pb.AccommodationRequestRequest;
  readonly responseType: typeof services_service_pb.AccommodationRequestResponse;
};

type ServicesServiceDecideAccommodationRequest = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof services_service_pb.DecideAccommodationRequestRequest;
  readonly responseType: typeof household_service_pb.SuccessResponse;
};

type ServicesServiceRequestAccommodation = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof services_service_pb.RequestAccommodationRequest;
  readonly responseType: typeof household_service_pb.SuccessResponse;
};

export class ServicesService {
  static readonly serviceName: string;
  static readonly SearchServices: ServicesServiceSearchServices;
  static readonly GetService: ServicesServiceGetService;
  static readonly GetServicesByAdministrator: ServicesServiceGetServicesByAdministrator;
  static readonly CreateService: ServicesServiceCreateService;
  static readonly EditService: ServicesServiceEditService;
  static readonly DeleteService: ServicesServiceDeleteService;
  static readonly GetAccommodations: ServicesServiceGetAccommodations;
  static readonly CreateAccommodation: ServicesServiceCreateAccommodation;
  static readonly EditAccommodation: ServicesServiceEditAccommodation;
  static readonly DeleteAccommodation: ServicesServiceDeleteAccommodation;
  static readonly GetAccommodationRequestsForAdministrator: ServicesServiceGetAccommodationRequestsForAdministrator;
  static readonly GetAccommodationRequests: ServicesServiceGetAccommodationRequests;
  static readonly DecideAccommodationRequest: ServicesServiceDecideAccommodationRequest;
  static readonly RequestAccommodation: ServicesServiceRequestAccommodation;
}

export type ServiceError = { message: string, code: number; metadata: grpc.Metadata }
export type Status = { details: string, code: number; metadata: grpc.Metadata }

interface UnaryResponse {
  cancel(): void;
}
interface ResponseStream<T> {
  cancel(): void;
  on(type: 'data', handler: (message: T) => void): ResponseStream<T>;
  on(type: 'end', handler: (status?: Status) => void): ResponseStream<T>;
  on(type: 'status', handler: (status: Status) => void): ResponseStream<T>;
}
interface RequestStream<T> {
  write(message: T): RequestStream<T>;
  end(): void;
  cancel(): void;
  on(type: 'end', handler: (status?: Status) => void): RequestStream<T>;
  on(type: 'status', handler: (status: Status) => void): RequestStream<T>;
}
interface BidirectionalStream<ReqT, ResT> {
  write(message: ReqT): BidirectionalStream<ReqT, ResT>;
  end(): void;
  cancel(): void;
  on(type: 'data', handler: (message: ResT) => void): BidirectionalStream<ReqT, ResT>;
  on(type: 'end', handler: (status?: Status) => void): BidirectionalStream<ReqT, ResT>;
  on(type: 'status', handler: (status: Status) => void): BidirectionalStream<ReqT, ResT>;
}

export class ServicesServiceClient {
  readonly serviceHost: string;

  constructor(serviceHost: string, options?: grpc.RpcOptions);
  searchServices(requestMessage: services_service_pb.SearchServiceRequest, metadata?: grpc.Metadata): ResponseStream<services_service_pb.ServiceResponse>;
  getService(
    requestMessage: services_service_pb.ServiceRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: services_service_pb.ServiceResponse|null) => void
  ): UnaryResponse;
  getService(
    requestMessage: services_service_pb.ServiceRequest,
    callback: (error: ServiceError|null, responseMessage: services_service_pb.ServiceResponse|null) => void
  ): UnaryResponse;
  getServicesByAdministrator(requestMessage: services_service_pb.ServiceByAdministratorRequest, metadata?: grpc.Metadata): ResponseStream<services_service_pb.ServiceResponse>;
  createService(
    requestMessage: services_service_pb.CreateOrEditServiceRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: services_service_pb.ServiceResponse|null) => void
  ): UnaryResponse;
  createService(
    requestMessage: services_service_pb.CreateOrEditServiceRequest,
    callback: (error: ServiceError|null, responseMessage: services_service_pb.ServiceResponse|null) => void
  ): UnaryResponse;
  editService(
    requestMessage: services_service_pb.CreateOrEditServiceRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: services_service_pb.ServiceResponse|null) => void
  ): UnaryResponse;
  editService(
    requestMessage: services_service_pb.CreateOrEditServiceRequest,
    callback: (error: ServiceError|null, responseMessage: services_service_pb.ServiceResponse|null) => void
  ): UnaryResponse;
  deleteService(
    requestMessage: services_service_pb.ServiceRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  deleteService(
    requestMessage: services_service_pb.ServiceRequest,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  getAccommodations(requestMessage: services_service_pb.ServiceRequest, metadata?: grpc.Metadata): ResponseStream<services_service_pb.AccommodationResponse>;
  createAccommodation(
    requestMessage: services_service_pb.CreateOrEditAccommodationRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: services_service_pb.AccommodationResponse|null) => void
  ): UnaryResponse;
  createAccommodation(
    requestMessage: services_service_pb.CreateOrEditAccommodationRequest,
    callback: (error: ServiceError|null, responseMessage: services_service_pb.AccommodationResponse|null) => void
  ): UnaryResponse;
  editAccommodation(
    requestMessage: services_service_pb.CreateOrEditAccommodationRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: services_service_pb.AccommodationResponse|null) => void
  ): UnaryResponse;
  editAccommodation(
    requestMessage: services_service_pb.CreateOrEditAccommodationRequest,
    callback: (error: ServiceError|null, responseMessage: services_service_pb.AccommodationResponse|null) => void
  ): UnaryResponse;
  deleteAccommodation(
    requestMessage: services_service_pb.DeleteAccommodationRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  deleteAccommodation(
    requestMessage: services_service_pb.DeleteAccommodationRequest,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  getAccommodationRequestsForAdministrator(requestMessage: services_service_pb.ServiceByAdministratorRequest, metadata?: grpc.Metadata): ResponseStream<services_service_pb.AccommodationRequestResponse>;
  getAccommodationRequests(requestMessage: services_service_pb.AccommodationRequestRequest, metadata?: grpc.Metadata): ResponseStream<services_service_pb.AccommodationRequestResponse>;
  decideAccommodationRequest(
    requestMessage: services_service_pb.DecideAccommodationRequestRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  decideAccommodationRequest(
    requestMessage: services_service_pb.DecideAccommodationRequestRequest,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  requestAccommodation(
    requestMessage: services_service_pb.RequestAccommodationRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  requestAccommodation(
    requestMessage: services_service_pb.RequestAccommodationRequest,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
}

