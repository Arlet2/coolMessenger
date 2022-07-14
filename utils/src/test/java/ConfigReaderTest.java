import exceptions.ConfigDataNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.ConfigReader;

import java.io.StringReader;
import java.net.InetSocketAddress;

public class ConfigReaderTest {
    private StringReader reader;

    @Before
    public void createDefaultReader() {
        reader = new StringReader("hostname: localhost\n"+
                "port: 13456");
    }

    @Test
    public void readConfigTest() {
        InetSocketAddress expected = new InetSocketAddress("gasgsf", 13456);
        reader = new StringReader("hostname: gasgsf\n"+
                "port: 13456");

        InetSocketAddress result = ConfigReader.readAddress(reader);

        Assert.assertEquals(expected, result);
    }

    @Test (expected = ConfigDataNotFoundException.class)
    public void noPortInConfigTest() {
        reader = new StringReader("hostname: gasgsf\n");

        ConfigReader.readAddress(reader);
    }

    @Test (expected = ConfigDataNotFoundException.class)
    public void noAddressInConfigTest() {
        reader = new StringReader("port: 1235");

        ConfigReader.readAddress(reader);
    }

    @Test
    public void readPortTest() {
        int result = ConfigReader.readPort(reader);

        Assert.assertEquals(13456, result);
    }
}
