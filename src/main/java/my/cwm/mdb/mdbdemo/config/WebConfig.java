package my.cwm.mdb.mdbdemo.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebFluxSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    public WebConfig(JWTAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

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
}
