package home.app.grpc.api.mappers;

import home.app.grpc.UserMessage;
import home.app.grpc.api.model.IMapper;
import home.app.grpc.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IMapper<User, UserMessage> {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public User toEntity(UserMessage dto) {
        User user = new User();

        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setId(dto.getId());
        user.setLastName(dto.getLastName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setBlocked(dto.getBlocked());
        user.setRole(userRoleMapper.toEntity(dto.getRole()));
        user.setAddress(addressMapper.toEntity(dto.getAddress()));

        return user;
    }

    @Override
    public UserMessage toDTO(User user) {
        return UserMessage.newBuilder()
                .setAddress(addressMapper.toDTO(user.getAddress()))
                .setBlocked(user.getBlocked())
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setId(user.getId())
                .setLastName(user.getLastName())
                .setPassword(user.getPassword())
                .setPhone(user.getPhone())
                .setRole(userRoleMapper.toDTO(user.getRole()))
                .build();
    }
}
