package exceptions;

public class DataSavingException extends RuntimeException {
    public DataSavingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
