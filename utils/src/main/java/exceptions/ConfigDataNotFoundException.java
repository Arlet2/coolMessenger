package exceptions;

public class ConfigDataNotFoundException extends RuntimeException {
    public ConfigDataNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
