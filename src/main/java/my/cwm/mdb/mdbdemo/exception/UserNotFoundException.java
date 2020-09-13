package my.cwm.mdb.mdbdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -7871543282119723007L;

    public UserNotFoundException() {
        super("User not found");
    }
}