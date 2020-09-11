package home.app.grpc.api.security;

import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static com.google.common.base.Strings.nullToEmpty;

@GrpcGlobalServerInterceptor
@Order(10)
public class AuthenticationInterceptor implements ServerInterceptor {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String authHeader = nullToEmpty(metadata.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER)));

        if (!authHeader.startsWith("Bearer ")) {
            return serverCallHandler.startCall(serverCall, metadata);
        }

        try {
            String token = authHeader.substring(authHeader.indexOf(' ') + 1);
            String email = tokenService.getEmailFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (tokenService.validateToken(token, userDetails)) {
                Authentication authentication = new TokenBasedAuthentication(userDetails, token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return serverCallHandler.startCall(serverCall, metadata);
            } else {
                throw Status.UNAUTHENTICATED.asRuntimeException();
            }
        } catch (Exception e) {
            throw Status.UNAUTHENTICATED.asRuntimeException();
        }
    }
}
