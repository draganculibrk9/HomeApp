// package: home.app.grpc
// file: services_service.proto

var services_service_pb = require("./services_service_pb");
var household_service_pb = require("./household_service_pb");
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

ServicesService.GetServicesByAdministrator = {
  methodName: "GetServicesByAdministrator",
  service: ServicesService,
  requestStream: false,
  responseStream: true,
  requestType: services_service_pb.ServiceByAdministratorRequest,
  responseType: services_service_pb.ServiceResponse
};

ServicesService.CreateService = {
  methodName: "CreateService",
  service: ServicesService,
  requestStream: false,
  responseStream: false,
  requestType: services_service_pb.CreateOrEditServiceRequest,
  responseType: services_service_pb.ServiceResponse
};

ServicesService.EditService = {
  methodName: "EditService",
  service: ServicesService,
  requestStream: false,
  responseStream: false,
  requestType: services_service_pb.CreateOrEditServiceRequest,
  responseType: services_service_pb.ServiceResponse
};

ServicesService.DeleteService = {
  methodName: "DeleteService",
  service: ServicesService,
  requestStream: false,
  responseStream: false,
  requestType: services_service_pb.ServiceRequest,
  responseType: household_service_pb.SuccessResponse
};

ServicesService.GetAccommodations = {
  methodName: "GetAccommodations",
  service: ServicesService,
  requestStream: false,
  responseStream: true,
  requestType: services_service_pb.ServiceRequest,
  responseType: services_service_pb.AccommodationResponse
};

ServicesService.CreateAccommodation = {
  methodName: "CreateAccommodation",
  service: ServicesService,
  requestStream: false,
  responseStream: false,
  requestType: services_service_pb.CreateOrEditAccommodationRequest,
  responseType: services_service_pb.AccommodationResponse
};

ServicesService.EditAccommodation = {
  methodName: "EditAccommodation",
  service: ServicesService,
  requestStream: false,
  responseStream: false,
  requestType: services_service_pb.CreateOrEditAccommodationRequest,
  responseType: services_service_pb.AccommodationResponse
};

ServicesService.DeleteAccommodation = {
  methodName: "DeleteAccommodation",
  service: ServicesService,
  requestStream: false,
  responseStream: false,
  requestType: services_service_pb.DeleteAccommodationRequest,
  responseType: household_service_pb.SuccessResponse
};

ServicesService.GetAccommodationRequestsForAdministrator = {
  methodName: "GetAccommodationRequestsForAdministrator",
  service: ServicesService,
  requestStream: false,
  responseStream: true,
  requestType: services_service_pb.ServiceByAdministratorRequest,
  responseType: services_service_pb.AccommodationRequestResponse
};

ServicesService.GetAccommodationRequests = {
  methodName: "GetAccommodationRequests",
  service: ServicesService,
  requestStream: false,
  responseStream: true,
  requestType: services_service_pb.AccommodationRequestRequest,
  responseType: services_service_pb.AccommodationRequestResponse
};

ServicesService.DecideAccommodationRequest = {
  methodName: "DecideAccommodationRequest",
  service: ServicesService,
  requestStream: false,
  responseStream: false,
  requestType: services_service_pb.DecideAccommodationRequestRequest,
  responseType: household_service_pb.SuccessResponse
};

ServicesService.RequestAccommodation = {
  methodName: "RequestAccommodation",
  service: ServicesService,
  requestStream: false,
  responseStream: false,
  requestType: services_service_pb.RequestAccommodationRequest,
  responseType: household_service_pb.SuccessResponse
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

ServicesServiceClient.prototype.getServicesByAdministrator = function getServicesByAdministrator(requestMessage, metadata) {
  var listeners = {
    data: [],
    end: [],
    status: []
  };
  var client = grpc.invoke(ServicesService.GetServicesByAdministrator, {
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

ServicesServiceClient.prototype.createService = function createService(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(ServicesService.CreateService, {
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

ServicesServiceClient.prototype.editService = function editService(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(ServicesService.EditService, {
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

ServicesServiceClient.prototype.deleteService = function deleteService(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(ServicesService.DeleteService, {
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

ServicesServiceClient.prototype.createAccommodation = function createAccommodation(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(ServicesService.CreateAccommodation, {
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

ServicesServiceClient.prototype.editAccommodation = function editAccommodation(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(ServicesService.EditAccommodation, {
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

ServicesServiceClient.prototype.deleteAccommodation = function deleteAccommodation(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(ServicesService.DeleteAccommodation, {
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

ServicesServiceClient.prototype.getAccommodationRequestsForAdministrator = function getAccommodationRequestsForAdministrator(requestMessage, metadata) {
  var listeners = {
    data: [],
    end: [],
    status: []
  };
  var client = grpc.invoke(ServicesService.GetAccommodationRequestsForAdministrator, {
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

ServicesServiceClient.prototype.getAccommodationRequests = function getAccommodationRequests(requestMessage, metadata) {
  var listeners = {
    data: [],
    end: [],
    status: []
  };
  var client = grpc.invoke(ServicesService.GetAccommodationRequests, {
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

ServicesServiceClient.prototype.decideAccommodationRequest = function decideAccommodationRequest(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(ServicesService.DecideAccommodationRequest, {
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

ServicesServiceClient.prototype.requestAccommodation = function requestAccommodation(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(ServicesService.RequestAccommodation, {
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

exports.ServicesServiceClient = ServicesServiceClient;

