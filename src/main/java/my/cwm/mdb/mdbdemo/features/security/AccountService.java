package my.cwm.mdb.mdbdemo.features.security;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import my.cwm.mdb.mdbdemo.exception.UserNotFoundException;
import my.cwm.mdb.mdbdemo.exception.WrongPasswordException;
import reactor.core.publisher.Mono;

@Service
public class AccountService {
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AccountService(UserService userService, PasswordEncoder passwordEncoder,
            UserRefreshTokenRepository userRefreshTokenRepository, TokenProvider tokenProvider) {
        this.userRefreshTokenRepository = userRefreshTokenRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    private TokenPair doLoginUser(final User user) {
        final String jwt = tokenProvider.generateAccessToken(user.getUsername());
        final String refreshToken = createRefreshToken(user);
        return new TokenPair(jwt, refreshToken);
    }

    public Mono<TokenPair> login(UserLogin login) {
        return this.userService.findFirstByUsername(login.getUsername())
                .switchIfEmpty(Mono.error(UserNotFoundException::new)).flatMap(user -> {
                    if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                        return Mono.just(this.doLoginUser(user));
                    } else
                        return Mono.error(WrongPasswordException::new);
                });
    }

    private String createRefreshToken(final User user) {
        final String token = RandomStringUtils.randomAlphanumeric(128);
        userRefreshTokenRepository.save(new UserRefreshToken(token, user));
        return token;

    }
}
