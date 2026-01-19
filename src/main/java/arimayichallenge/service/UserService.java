package arimayichallenge.service;

import arimayichallenge.domain.user.User;
import arimayichallenge.exception.ConflictException;
import arimayichallenge.exception.NotFoundException;
import arimayichallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(String name, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Email already in use");
        }
        return userRepository.save(new User(name, email));
    }

    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void disable(Long id) {
        User user = get(id);
        user.disable();
        userRepository.save(user);
    }
}
