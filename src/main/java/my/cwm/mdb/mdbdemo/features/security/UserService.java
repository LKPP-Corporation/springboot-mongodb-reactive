package my.cwm.mdb.mdbdemo.features.security;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> findFirstByUsername(String username) {
        return this.userRepository.findFirstByUsername(username);
    }
}
