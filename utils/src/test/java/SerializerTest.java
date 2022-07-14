import exceptions.SerializerException;
import org.junit.Assert;
import org.junit.Test;
import utils.Serializer;

public class SerializerTest {
    @Test
    public void convertToObjectTest() throws SerializerException {
        Object result;
        String expected = "Hello, world";

        result = Serializer.convertBytesToObject(expected.getBytes());

        Assert.assertEquals(expected, result);
    }

    @Test
    public void doubleConversionTest() throws SerializerException {
        String arg = "Test123$#2";

        byte[] bytes = Serializer.convertObjectToBytes(arg);
        String result = (String) Serializer.convertBytesToObject(bytes);

        Assert.assertEquals(result, arg);
    }
}
