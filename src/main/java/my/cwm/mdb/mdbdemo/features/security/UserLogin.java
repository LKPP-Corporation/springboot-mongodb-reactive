package my.cwm.mdb.mdbdemo.features.security;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLogin {
    @NotBlank
    private final String username;

    @NotBlank
    private final String password;
}
