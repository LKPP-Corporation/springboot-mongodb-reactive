package my.cwm.mdb.mdbdemo.features.security;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
public class UserRefreshToken {

    @Id
    private long id;

    @Indexed(unique = true)
    private String token;

    private User user;

    public UserRefreshToken(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
