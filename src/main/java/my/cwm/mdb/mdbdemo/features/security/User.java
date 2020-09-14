package my.cwm.mdb.mdbdemo.features.security;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private long id;

    @Indexed(unique = true)
    private String personel;

    @Indexed(unique = true)
    private String username;
    private String password;
    private boolean locked = false;
    private boolean enabled = true;
    private List<String> roles;
}
