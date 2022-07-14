package encryptors;

public interface EncryptorService {
    int getEncryptionProtocol();
    byte[] encrypt(byte[] data);
    byte[] decrypt(byte[] data);
}
