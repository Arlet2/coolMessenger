import exceptions.SerializerException;
import org.junit.Assert;
import org.junit.Test;
import utils.Serializer;

import java.util.ArrayList;

public class SerializerTest {

    @Test
    public void doubleConversionTest() throws SerializerException {
        String arg = "Test123$#2";

        byte[] bytes = Serializer.convertObjectToBytes(arg);
        String result = (String) Serializer.convertBytesToObject(bytes);

        Assert.assertEquals(result, arg);
    }

    @Test
    public void bigStructureSerializeTest() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("hello");
        expected.add("1231");

        ArrayList<String> result = (ArrayList<String>)
                Serializer.convertBytesToObject(Serializer.convertObjectToBytes(expected));

        Assert.assertEquals(expected, result);
    }
}
