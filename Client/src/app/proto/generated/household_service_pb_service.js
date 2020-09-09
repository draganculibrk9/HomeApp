// package: home.app.grpc
// file: household_service.proto

var household_service_pb = require("./household_service_pb");
var grpc = require("@improbable-eng/grpc-web").grpc;

var HouseholdService = (function () {
  function HouseholdService() {}
  HouseholdService.serviceName = "home.app.grpc.HouseholdService";
  return HouseholdService;
}());

HouseholdService.GetHousehold = {
  methodName: "GetHousehold",
  service: HouseholdService,
  requestStream: false,
  responseStream: false,
  requestType: household_service_pb.HouseholdRequest,
  responseType: household_service_pb.HouseholdResponse
};

HouseholdService.GetHouseholdById = {
  methodName: "GetHouseholdById",
  service: HouseholdService,
  requestStream: false,
  responseStream: false,
  requestType: household_service_pb.HouseholdByIdRequest,
  responseType: household_service_pb.HouseholdResponse
};

HouseholdService.CreateHousehold = {
  methodName: "CreateHousehold",
  service: HouseholdService,
  requestStream: false,
  responseStream: false,
  requestType: household_service_pb.HouseholdRequest,
  responseType: household_service_pb.SuccessResponse
};

HouseholdService.GetTransactions = {
  methodName: "GetTransactions",
  service: HouseholdService,
  requestStream: false,
  responseStream: true,
  requestType: household_service_pb.TransactionsRequest,
  responseType: household_service_pb.TransactionResponse
};

HouseholdService.GetTransaction = {
  methodName: "GetTransaction",
  service: HouseholdService,
  requestStream: false,
  responseStream: false,
  requestType: household_service_pb.GetOrDeleteTransactionRequest,
  responseType: household_service_pb.TransactionResponse
};

HouseholdService.EditTransaction = {
  methodName: "EditTransaction",
  service: HouseholdService,
  requestStream: false,
  responseStream: false,
  requestType: household_service_pb.CreateOrEditTransactionRequest,
  responseType: household_service_pb.SuccessResponse
};

HouseholdService.CreateTransaction = {
  methodName: "CreateTransaction",
  service: HouseholdService,
  requestStream: false,
  responseStream: false,
  requestType: household_service_pb.CreateOrEditTransactionRequest,
  responseType: household_service_pb.SuccessResponse
};

HouseholdService.DeleteTransaction = {
  methodName: "DeleteTransaction",
  service: HouseholdService,
  requestStream: false,
  responseStream: false,
  requestType: household_service_pb.GetOrDeleteTransactionRequest,
  responseType: household_service_pb.SuccessResponse
};

exports.HouseholdService = HouseholdService;

function HouseholdServiceClient(serviceHost, options) {
  this.serviceHost = serviceHost;
  this.options = options || {};
}

HouseholdServiceClient.prototype.getHousehold = function getHousehold(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(HouseholdService.GetHousehold, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

HouseholdServiceClient.prototype.getHouseholdById = function getHouseholdById(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(HouseholdService.GetHouseholdById, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

HouseholdServiceClient.prototype.createHousehold = function createHousehold(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(HouseholdService.CreateHousehold, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

HouseholdServiceClient.prototype.getTransactions = function getTransactions(requestMessage, metadata) {
  var listeners = {
    data: [],
    end: [],
    status: []
  };
  var client = grpc.invoke(HouseholdService.GetTransactions, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onMessage: function (responseMessage) {
      listeners.data.forEach(function (handler) {
        handler(responseMessage);
      });
    },
    onEnd: function (status, statusMessage, trailers) {
      listeners.status.forEach(function (handler) {
        handler({ code: status, details: statusMessage, metadata: trailers });
      });
      listeners.end.forEach(function (handler) {
        handler({ code: status, details: statusMessage, metadata: trailers });
      });
      listeners = null;
    }
  });
  return {
    on: function (type, handler) {
      listeners[type].push(handler);
      return this;
    },
    cancel: function () {
      listeners = null;
      client.close();
    }
  };
};

HouseholdServiceClient.prototype.getTransaction = function getTransaction(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(HouseholdService.GetTransaction, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

HouseholdServiceClient.prototype.editTransaction = function editTransaction(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(HouseholdService.EditTransaction, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

HouseholdServiceClient.prototype.createTransaction = function createTransaction(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(HouseholdService.CreateTransaction, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

HouseholdServiceClient.prototype.deleteTransaction = function deleteTransaction(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(HouseholdService.DeleteTransaction, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

exports.HouseholdServiceClient = HouseholdServiceClient;

