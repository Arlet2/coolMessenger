import exceptions.SerializerException;
import org.junit.Assert;
import org.junit.Test;
import utils.ByteString;
import utils.Serializer;

public class ByteStringTest {
    @Test
    public void doubleConversionTest() throws SerializerException {
        String input = "Hello, world!";

        ByteString byteString = new ByteString(Serializer.convertObjectToBytes(input));

        String result = (String) Serializer.convertBytesToObject(byteString.getBytes());
        Assert.assertEquals(input, result);
    }
}
