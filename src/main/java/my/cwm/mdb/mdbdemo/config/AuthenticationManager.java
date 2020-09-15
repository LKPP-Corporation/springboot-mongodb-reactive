package my.cwm.mdb.mdbdemo.config;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        try {
            return Mono.just(authentication);
        } catch (Exception e) {
            return Mono.empty();
        }
    }

}
