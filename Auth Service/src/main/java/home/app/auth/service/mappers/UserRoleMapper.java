package home.app.auth.service.mappers;

import home.app.auth.service.model.UserRole;
import home.app.grpc.UserMessage;
import home.app.grpc.api.model.IMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRoleMapper implements IMapper<UserRole, UserMessage.Role> {
    @Override
    public UserRole toEntity(UserMessage.Role dto) {
        switch (dto) {
            case USER:
                return UserRole.USER;
            case SYSTEM_ADMINISTRATOR:
                return UserRole.SYSTEM_ADMINISTRATOR;
            default:
                return UserRole.SERVICE_ADMINISTRATOR;
        }
    }

    @Override
    public UserMessage.Role toDTO(UserRole userRole) {
        switch (userRole) {
            case USER:
                return UserMessage.Role.USER;
            case SYSTEM_ADMINISTRATOR:
                return UserMessage.Role.SYSTEM_ADMINISTRATOR;
            default:
                return UserMessage.Role.SERVICE_ADMINISTRATOR;
        }
    }
}
