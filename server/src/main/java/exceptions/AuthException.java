package exceptions;

public class AuthException extends Exception {
    public AuthException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
