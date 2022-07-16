import exceptions.SerializerException;
import org.junit.Assert;
import org.junit.Test;
import utils.Serializer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

public class SerializerTest {

    @Test
    public void doubleConversionTest() throws SerializerException {
        String arg = "Test123$#2";

        byte[] bytes = Serializer.convertObjectToBytes(arg);
        String result = (String) Serializer.convertBytesToObject(bytes);

        Assert.assertEquals(result, arg);
    }
}
