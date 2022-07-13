package exceptions;

public class ConfigDataNotFoundException extends RuntimeException {
    public ConfigDataNotFoundException(String msg) {
        super(msg);
    }
}
