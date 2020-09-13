package my.cwm.mdb.mdbdemo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)

public class InvalidRefreshTokenException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 9125673011040816348L;

    public InvalidRefreshTokenException() {
        super("Invalid refresh token");
    }
}