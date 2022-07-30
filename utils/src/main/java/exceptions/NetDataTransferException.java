package exceptions;

public class NetDataTransferException extends Exception {
    public NetDataTransferException(Throwable cause) {
        super("Something got wrong with connection", cause);
    }
    public NetDataTransferException(String msg) {
        super(msg);
    }
}
