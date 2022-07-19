package exceptions;

public class StreamReadingException extends RuntimeException {
    public StreamReadingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
