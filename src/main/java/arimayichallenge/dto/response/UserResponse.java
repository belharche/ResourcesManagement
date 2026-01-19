package arimayichallenge.dto.response;

import arimayichallenge.domain.user.User;
import arimayichallenge.domain.user.UserStatus;

public record UserResponse(
    Long id,
    String name,
    String email,
    UserStatus status
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus()
        );
    }
}
