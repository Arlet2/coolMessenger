package connection.data_objects;

public class NetDTO {
    private final byte[] bytes;
    private final DataCode code;
    private final int encryptionProtocol;

    public enum DataCode {
        AUTH_INFO,
        MESSAGE,
        EMOJI,
        PICTURE,
        FILE,

        ERROR
    }

    public NetDTO(byte[] data, DataCode code, int encryptionProtocol) {
        this.encryptionProtocol = encryptionProtocol;
        bytes = data;
        this.code = code;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public DataCode getCode() {
        return code;
    }

    public int getEncryptionProtocol() {
        return encryptionProtocol;
    }
}
