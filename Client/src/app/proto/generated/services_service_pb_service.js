// package: home.app.grpc
// file: services_service.proto

var services_service_pb = require("./services_service_pb");
var grpc = require("@improbable-eng/grpc-web").grpc;

var ServicesService = (function () {
  function ServicesService() {}
  ServicesService.serviceName = "home.app.grpc.ServicesService";
  return ServicesService;
}());

ServicesService.SearchServices = {
  methodName: "SearchServices",
  service: ServicesService,
  requestStream: false,
  responseStream: true,
  requestType: services_service_pb.SearchServiceRequest,
  responseType: services_service_pb.ServiceResponse
};

ServicesService.GetService = {
  methodName: "GetService",
  service: ServicesService,
  requestStream: false,
  responseStream: false,
  requestType: services_service_pb.ServiceRequest,
  responseType: services_service_pb.ServiceResponse
};

ServicesService.GetServiceByAdministrator = {
  methodName: "GetServiceByAdministrator",
  service: ServicesService,
  requestStream: false,
  responseStream: false,
  requestType: services_service_pb.ServiceByAdministratorRequest,
  responseType: services_service_pb.ServiceResponse
};

ServicesService.GetAccommodations = {
  methodName: "GetAccommodations",
  service: ServicesService,
  requestStream: false,
  responseStream: true,
  requestType: services_service_pb.ServiceRequest,
  responseType: services_service_pb.AccommodationResponse
};

exports.ServicesService = ServicesService;

function ServicesServiceClient(serviceHost, options) {
  this.serviceHost = serviceHost;
  this.options = options || {};
}

ServicesServiceClient.prototype.searchServices = function searchServices(requestMessage, metadata) {
  var listeners = {
    data: [],
    end: [],
    status: []
  };
  var client = grpc.invoke(ServicesService.SearchServices, {
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

ServicesServiceClient.prototype.getService = function getService(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(ServicesService.GetService, {
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

ServicesServiceClient.prototype.getServiceByAdministrator = function getServiceByAdministrator(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(ServicesService.GetServiceByAdministrator, {
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

ServicesServiceClient.prototype.getAccommodations = function getAccommodations(requestMessage, metadata) {
  var listeners = {
    data: [],
    end: [],
    status: []
  };
  var client = grpc.invoke(ServicesService.GetAccommodations, {
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

exports.ServicesServiceClient = ServicesServiceClient;

