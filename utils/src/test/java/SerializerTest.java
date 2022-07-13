import org.junit.Assert;
import org.junit.Test;

public class SerializerTest {
    @Test
    public void convertToObjectTest() {
        Object result;
        String expected = "Hello, world";

        result = Serializer.convertBytesToObject(expected.getBytes());

        Assert.assertEquals(expected, result);
    }

    @Test
    public void doubleConversionTest() {
        String arg = "Test123$#2";

        byte[] bytes = Serializer.convertObjectToBytes(arg);
        String result = (String) Serializer.convertBytesToObject(bytes);

        Assert.assertEquals(result, arg);
    }
}
