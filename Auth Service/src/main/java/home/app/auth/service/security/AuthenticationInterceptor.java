package home.app.auth.service.security;

import home.app.auth.service.services.AuthService;
import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

@GrpcGlobalServerInterceptor
@Order()
public class AuthenticationInterceptor implements ServerInterceptor {
    @Autowired
    private AuthService authService;

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

        if (authService.validateToken(token)) {
            return serverCallHandler.startCall(serverCall, metadata);
        } else {
            throw Status.UNAUTHENTICATED.asRuntimeException();
        }
    }
}
