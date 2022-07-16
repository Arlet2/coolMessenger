import encryptors.EncryptorsFactory;
import encryptors.EncryptorService;
import exceptions.SerializerException;
import org.junit.Assert;
import org.junit.Test;
import utils.Serializer;

public class EncryptorsTest {
    @Test
    public void nullEncryptorEncryptionTest() throws SerializerException {
        EncryptorService encryptor = EncryptorsFactory.getEncryptor(0);
        String input = "Hello, world!";

        String result = (String)
                Serializer.convertBytesToObject(encryptor.encrypt(Serializer.convertObjectToBytes(input)));

        Assert.assertEquals(input, result);
    }

    @Test
    public void nullEncryptorDecryptionTest() throws SerializerException {
        EncryptorService encryptor = EncryptorsFactory.getEncryptor(0);
        String input = "Hello, world!";

        String result = (String)
                Serializer.convertBytesToObject(encryptor.decrypt(encryptor.encrypt(Serializer.convertObjectToBytes(input))));

        Assert.assertEquals(input, result);
    }

    @Test
    public void simpleEncryptorEncryptionTest() throws SerializerException {
        EncryptorService encryptor = EncryptorsFactory.getEncryptor(1);
        String input = "Hello, world!";

        byte[] result = encryptor.encrypt(Serializer.convertObjectToBytes(input));
        Assert.assertNotEquals(Serializer.convertObjectToBytes(input), result);
    }

    @Test
    public void simpleEncryptorDecryptionTest() throws SerializerException {
        EncryptorService encryptor = EncryptorsFactory.getEncryptor(1);
        String input = "Hello, encryptor!";

        String result = (String) Serializer.convertBytesToObject(
                encryptor.decrypt(encryptor.encrypt(Serializer.convertObjectToBytes(input))));

        Assert.assertEquals(input, result);
    }
}
