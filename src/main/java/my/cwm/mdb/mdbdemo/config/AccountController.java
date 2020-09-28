package my.cwm.mdb.mdbdemo.config;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import my.cwm.mdb.mdbdemo.features.security.AccountService;
import my.cwm.mdb.mdbdemo.features.security.UserLogin;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.BodyInserters;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
public class AccountController {

    public static final String PATH_POST_REFRESH = "/api/v1/account/token/refresh";
    public static final String PATH_POST_LOGIN = "/api/v1/account/login";
    public static final String PATH_POST_SIGN_UP = "/api/v1/account/register";
    public static final String PATH_DELETE_LOGOUT = "/api/v1/account/logout";
    private static final String PATH_GET_ME = "/api/v1/account/me";
    private static final String PATH_CHANGE_PWD = "/api/v1/account/change_password";

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(PATH_POST_LOGIN)
    public Mono<ServerResponse> login(ServerRequest request) {
        Mono<UserLogin> loginRequest = request.bodyToMono(UserLogin.class);
        return loginRequest.flatMap(lr -> {
            return this.accountService.login(lr).flatMap(tp -> {
                return ServerResponse.ok().contentType(APPLICATION_JSON).body(BodyInserters.fromValue(tp));
            }).onErrorContinue(ServerResponse.badRequest()
                    .body(BodyInserters.fromObject(new ApiResponse(400, "Invalid credentials", null))));
        });
    }
}
