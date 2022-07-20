import exceptions.SerializerException;
import org.junit.Assert;
import org.junit.Test;
import utils.ByteString;
import utils.Serializer;

import java.util.ArrayList;

public class ByteStringTest {
    @Test
    public void doubleConversionTest() throws SerializerException {
        String input = "Hello, world!";

        ByteString byteString = new ByteString(Serializer.convertObjectToBytes(input));

        String result = (String) Serializer.convertBytesToObject(byteString.getBytes());
        Assert.assertEquals(input, result);
    }

    @Test
    public void bigStructureTest() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("hello");
        expected.add("1231");

        ByteString byteString = new ByteString(Serializer.convertObjectToBytes(expected));

        ArrayList<String> result = (ArrayList<String>) Serializer.convertBytesToObject(byteString.getBytes());

        Assert.assertEquals(expected, result);
    }
}
