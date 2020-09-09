package home.app.services.service.security;

import home.app.grpc.AuthServiceGrpc;
import home.app.grpc.ValidateTokenRequest;
import io.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.core.annotation.Order;

@GrpcGlobalServerInterceptor
@Order()
public class AuthenticationInterceptor implements ServerInterceptor {
    @GrpcClient("authService")
    private AuthServiceGrpc.AuthServiceBlockingStub authServiceStub;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String authHeader = metadata.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));

        if (authHeader == null) {
            authHeader = "";
        }

        if (!authHeader.startsWith("Bearer ")) {
            return serverCallHandler.startCall(serverCall, metadata);
        }

        String token = authHeader.substring(7);

        if (authServiceStub.validateToken(ValidateTokenRequest.newBuilder().setToken(token).build()).getSuccess()) {
            return serverCallHandler.startCall(serverCall, metadata);
        } else {
            throw Status.UNAUTHENTICATED.asRuntimeException();
        }
    }
}
