package my.cwm.mdb.mdbdemo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends RuntimeException {
    /**
    	 *
    	 */
    private static final long serialVersionUID = -5770268790513067751L;

    public InvalidTokenException() {
        super("Token is not valid");
    }
}