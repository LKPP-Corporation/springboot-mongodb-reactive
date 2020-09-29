package my.cwm.mdb.mdbdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class WebConfig {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;



    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        String[] patterns = new String[] { "/v3/api-docs/**", "/swagger-ui/**", "/webjars/swagger-ui/**",
                "/swagger-ui.html", "/h2-console/**",
                AccountController.PATH_POST_REFRESH, AccountController.PATH_POST_LOGIN,
                AccountController.PATH_DELETE_LOGOUT };
        return http.exceptionHandling().authenticationEntryPoint((swe, e) -> {
            return Mono.fromRunnable(() -> {
                swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            });
        }).accessDeniedHandler((swe, e) -> {
            return Mono.fromRunnable(() -> {
                swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            });
        }).and().csrf().disable()
                .authenticationManager(authenticationManager).securityContextRepository(securityContextRepository)
                .authorizeExchange().pathMatchers(HttpMethod.OPTIONS).permitAll().pathMatchers(patterns).permitAll()
                .anyExchange().authenticated().and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.cors().disable();
        http.csrf().disable(); // TODO: keep only in dev
        http.addFilterAfter(jwtAuthorizationFilter, BasicAuthenticationFilter.class);
        http.authorizeRequests().antMatchers(AccountController.PATH_POST_SIGN_UP).permitAll()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/h2-console/**").permitAll()
                .antMatchers(AccountController.PATH_POST_REFRESH).permitAll()
                .antMatchers(AccountController.PATH_POST_LOGIN).permitAll()
                .antMatchers(AccountController.PATH_DELETE_LOGOUT).permitAll()
                // .antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            response.setHeader("WWW-Authenticate", "Bearer"); // we can point explicitly to register/login/refresh URL
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        });
    }
    */
}
