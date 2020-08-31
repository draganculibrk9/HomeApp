// package: home.app.grpc
// file: services_service.proto

import * as services_service_pb from "./services_service_pb";
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

type ServicesServiceGetServiceByAdministrator = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof services_service_pb.ServiceByAdministratorRequest;
  readonly responseType: typeof services_service_pb.ServiceResponse;
};

type ServicesServiceGetAccommodations = {
  readonly methodName: string;
  readonly service: typeof ServicesService;
  readonly requestStream: false;
  readonly responseStream: true;
  readonly requestType: typeof services_service_pb.ServiceRequest;
  readonly responseType: typeof services_service_pb.AccommodationResponse;
};

export class ServicesService {
  static readonly serviceName: string;
  static readonly SearchServices: ServicesServiceSearchServices;
  static readonly GetService: ServicesServiceGetService;
  static readonly GetServiceByAdministrator: ServicesServiceGetServiceByAdministrator;
  static readonly GetAccommodations: ServicesServiceGetAccommodations;
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
  getServiceByAdministrator(
    requestMessage: services_service_pb.ServiceByAdministratorRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: services_service_pb.ServiceResponse|null) => void
  ): UnaryResponse;
  getServiceByAdministrator(
    requestMessage: services_service_pb.ServiceByAdministratorRequest,
    callback: (error: ServiceError|null, responseMessage: services_service_pb.ServiceResponse|null) => void
  ): UnaryResponse;
  getAccommodations(requestMessage: services_service_pb.ServiceRequest, metadata?: grpc.Metadata): ResponseStream<services_service_pb.AccommodationResponse>;
}

