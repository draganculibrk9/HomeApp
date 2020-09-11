// package: home.app.grpc
// file: household_service.proto

import * as household_service_pb from "./household_service_pb";
import {grpc} from "@improbable-eng/grpc-web";

type HouseholdServiceGetHousehold = {
  readonly methodName: string;
  readonly service: typeof HouseholdService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof household_service_pb.HouseholdRequest;
  readonly responseType: typeof household_service_pb.HouseholdResponse;
};

type HouseholdServiceGetHouseholdById = {
  readonly methodName: string;
  readonly service: typeof HouseholdService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof household_service_pb.HouseholdByIdRequest;
  readonly responseType: typeof household_service_pb.HouseholdResponse;
};

type HouseholdServiceCreateHousehold = {
  readonly methodName: string;
  readonly service: typeof HouseholdService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof household_service_pb.HouseholdRequest;
  readonly responseType: typeof household_service_pb.SuccessResponse;
};

type HouseholdServiceGetTransactions = {
  readonly methodName: string;
  readonly service: typeof HouseholdService;
  readonly requestStream: false;
  readonly responseStream: true;
  readonly requestType: typeof household_service_pb.TransactionsRequest;
  readonly responseType: typeof household_service_pb.TransactionResponse;
};

type HouseholdServiceGetTransaction = {
  readonly methodName: string;
  readonly service: typeof HouseholdService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof household_service_pb.GetOrDeleteTransactionRequest;
  readonly responseType: typeof household_service_pb.TransactionResponse;
};

type HouseholdServiceEditTransaction = {
  readonly methodName: string;
  readonly service: typeof HouseholdService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof household_service_pb.CreateOrEditTransactionRequest;
  readonly responseType: typeof household_service_pb.SuccessResponse;
};

type HouseholdServiceCreateTransaction = {
  readonly methodName: string;
  readonly service: typeof HouseholdService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof household_service_pb.CreateOrEditTransactionRequest;
  readonly responseType: typeof household_service_pb.SuccessResponse;
};

type HouseholdServiceDeleteTransaction = {
  readonly methodName: string;
  readonly service: typeof HouseholdService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof household_service_pb.GetOrDeleteTransactionRequest;
  readonly responseType: typeof household_service_pb.SuccessResponse;
};

export class HouseholdService {
  static readonly serviceName: string;
  static readonly GetHousehold: HouseholdServiceGetHousehold;
  static readonly GetHouseholdById: HouseholdServiceGetHouseholdById;
  static readonly CreateHousehold: HouseholdServiceCreateHousehold;
  static readonly GetTransactions: HouseholdServiceGetTransactions;
  static readonly GetTransaction: HouseholdServiceGetTransaction;
  static readonly EditTransaction: HouseholdServiceEditTransaction;
  static readonly CreateTransaction: HouseholdServiceCreateTransaction;
  static readonly DeleteTransaction: HouseholdServiceDeleteTransaction;
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

export class HouseholdServiceClient {
  readonly serviceHost: string;

  constructor(serviceHost: string, options?: grpc.RpcOptions);
  getHousehold(
    requestMessage: household_service_pb.HouseholdRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.HouseholdResponse|null) => void
  ): UnaryResponse;
  getHousehold(
    requestMessage: household_service_pb.HouseholdRequest,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.HouseholdResponse|null) => void
  ): UnaryResponse;
  getHouseholdById(
    requestMessage: household_service_pb.HouseholdByIdRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.HouseholdResponse|null) => void
  ): UnaryResponse;
  getHouseholdById(
    requestMessage: household_service_pb.HouseholdByIdRequest,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.HouseholdResponse|null) => void
  ): UnaryResponse;
  createHousehold(
    requestMessage: household_service_pb.HouseholdRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  createHousehold(
    requestMessage: household_service_pb.HouseholdRequest,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  getTransactions(requestMessage: household_service_pb.TransactionsRequest, metadata?: grpc.Metadata): ResponseStream<household_service_pb.TransactionResponse>;
  getTransaction(
    requestMessage: household_service_pb.GetOrDeleteTransactionRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.TransactionResponse|null) => void
  ): UnaryResponse;
  getTransaction(
    requestMessage: household_service_pb.GetOrDeleteTransactionRequest,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.TransactionResponse|null) => void
  ): UnaryResponse;
  editTransaction(
    requestMessage: household_service_pb.CreateOrEditTransactionRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  editTransaction(
    requestMessage: household_service_pb.CreateOrEditTransactionRequest,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  createTransaction(
    requestMessage: household_service_pb.CreateOrEditTransactionRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  createTransaction(
    requestMessage: household_service_pb.CreateOrEditTransactionRequest,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  deleteTransaction(
    requestMessage: household_service_pb.GetOrDeleteTransactionRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
  deleteTransaction(
    requestMessage: household_service_pb.GetOrDeleteTransactionRequest,
    callback: (error: ServiceError|null, responseMessage: household_service_pb.SuccessResponse|null) => void
  ): UnaryResponse;
}

