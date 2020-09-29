package my.cwm.mdb.mdbdemo.features.security;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
