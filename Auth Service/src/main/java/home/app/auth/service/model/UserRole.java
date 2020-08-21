package home.app.auth.service.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER,
    SERVICE_ADMINISTRATOR,
    SYSTEM_ADMINISTRATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
