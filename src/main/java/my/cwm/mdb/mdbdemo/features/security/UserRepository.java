package my.cwm.mdb.mdbdemo.features.security;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findFirstByUsername(String username);
}
