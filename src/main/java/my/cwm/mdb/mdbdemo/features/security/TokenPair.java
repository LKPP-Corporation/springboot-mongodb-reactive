package my.cwm.mdb.mdbdemo.features.security;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenPair {
    private final String jwt;

    @JsonProperty("refresh_token")
    private final String refreshToken;
}
