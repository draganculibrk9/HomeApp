package home.app.auth.service.mappers;

import home.app.auth.service.model.User;
import home.app.auth.service.model.UserRole;
import home.app.grpc.RegistrationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IMapper<User, RegistrationMessage> {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public User toEntity(RegistrationMessage dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setId(null);
        user.setLastName(dto.getLastName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());

        UserRole role;

        if (dto.getRole().equals(RegistrationMessage.Role.USER)) {
            role = UserRole.USER;
        } else {
            role = UserRole.SERVICE_ADMINISTRATOR;
        }

        user.setRole(role);
        user.setAddress(addressMapper.toEntity(dto.getAddress()));

        return user;
    }

    @Override
    public RegistrationMessage toDTO(User user) {
        throw new UnsupportedOperationException();
    }
}
