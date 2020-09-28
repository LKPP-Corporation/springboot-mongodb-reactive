package my.cwm.mdb.mdbdemo.features.security;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    @Value("${jwt.encryption.secret}")
    private String SECRET;

    @Value("${jwt.access.token.expiration.seconds}")
    private long EXPIRATION_TIME_IN_SECONDS;

    public String generateAccessToken(String email) {
        return JWT.create().withSubject(String.valueOf(email)).withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_SECONDS * 1000))
                .sign(Algorithm.HMAC256(SECRET.getBytes()));
    }
}
