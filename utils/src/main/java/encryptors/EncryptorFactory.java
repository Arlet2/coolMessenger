package encryptors;

public class EncryptorFactory {
    public static EncryptorService getEncryptor(int encryptionProtocol) {
        switch (encryptionProtocol) {
            case SimpleEncryptor.ENCRYPTION_PROTOCOL: // 1
                return new SimpleEncryptor();
            default: // 0 and other
                return new NullEncryptor();
        }
    }
}
