import org.junit.Assert;
import org.junit.Test;
import server_utils.PasswordHashGenerator;

public class PasswordHashGeneratorTest {

    @Test
    public void simpleHashTest() {
        String input = "hello";

        String result = PasswordHashGenerator.generateHash(input);

        if (input.equals(result))
            Assert.fail();
    }

    @Test
    public void algorithmNotExistAndUsingDefaultTest() {
        String input = "hello";

        String result = PasswordHashGenerator.generateHash(input);

        PasswordHashGenerator.setNewAlgorithm("Asdagagdahadha");

        String result1 = PasswordHashGenerator.generateHash(input);

        Assert.assertEquals(result, result1);
    }
}
