import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server_utils.Logger;

import java.io.*;

public class LoggerTest {
    private InputStream stream;
    private StringReader reader;

    @Before
    public void preparingBeforeTest() {
        if (stream != null) {
            try {
                reader.reset();
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail();
            }
            return;
        }
        String testLoggerConfig = "handlers = java.util.logging.ConsoleHandler\n" +
                "\n" +
                "java.util.logging.ConsoleHandler.level     = INFO\n" +
                "java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter";
        reader = new StringReader(testLoggerConfig);
        stream = new InputStream() {
            @Override
            public int read() throws IOException {
                return reader.read();
            }
        };
    }

    @Test
    public void createLoggerTest() {
        try {
            Logger.recreateInstance(stream).info("createLoggerTest");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createDefaultLoggerTest() {
        try {
            Logger.recreateInstance(InputStream.nullInputStream()).info("createDefaultLoggerTest");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createLoggerWithoutFileTest() {
        Logger.getInstance().info("createLoggerWithoutFileTest");
    }

}
