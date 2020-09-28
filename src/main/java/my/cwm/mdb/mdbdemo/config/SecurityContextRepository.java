package my.cwm.mdb.mdbdemo.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import my.cwm.mdb.mdbdemo.features.security.User;
import my.cwm.mdb.mdbdemo.features.security.UserService;

import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import reactor.core.publisher.Mono;

@Slf4j
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Value("${jwt.encryption.secret}")
    private String SECRET;

    @Value("${jwt.access.token.expiration.seconds}")
    private long EXPIRATION_TIME_IN_SECONDS;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        // TODO Auto-generated method stub
        // return null;
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        ServerHttpRequest request = swe.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            //Authentication auth = getAuthentication(authToken);
            //getAuthentication(authToken);
            return getAuthentication(authToken).flatMap(auth->{
                return this.authenticationManager.authenticate(auth).map((authentication) -> {
                    return new SecurityContextImpl(authentication);
                });
            });
            
        } else {
            log.warn("couldn't find bearer string, will ignore the header.");
            return Mono.empty();
        }
    }

    private Mono<Authentication> getAuthentication(String token) {
        // parse the token.
        final String username;
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET.getBytes())).build().verify(token);
            username = jwt.getSubject();
        } catch (JWTVerificationException e) {
            log.debug("Invalid JWT", e);
            return Mono.empty();
        }
        return this.userService.findFirstByUsername(username).map(u -> new JWTAuthentication(u));
/*
        User user = this.userService.findFirstByUsername(username).map(u -> new JWTAuthentication(u));

        if (user != null) {
            return new JWTAuthentication(user);
        } else {
            log.debug("User Not Found: {}", username);
            return null;
        }
        */
    }
}
