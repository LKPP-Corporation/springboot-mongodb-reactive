package my.cwm.mdb.mdbdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongPasswordException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -6195630833824825931L;

    public WrongPasswordException() {
        super("Wrong password");
    }
}