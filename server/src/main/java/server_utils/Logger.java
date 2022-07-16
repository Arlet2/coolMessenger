package server_utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class Logger {
    private static final String LOGGER_CONFIG_FILE_NAME = "logger.ar";
    private static java.util.logging.Logger logger;

    private Logger() {

    }

    public static java.util.logging.Logger getInstance() {
        if (logger != null)
            return logger;

        try (FileInputStream inputStream = new FileInputStream(LOGGER_CONFIG_FILE_NAME)) {
            return recreateInstance(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Logger's config file not found (name - +" + LOGGER_CONFIG_FILE_NAME +
                    "). Standard logger will be load");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot open config file");
        }

        logger = createLogger();
        return logger;
    }

    public static java.util.logging.Logger recreateInstance(InputStream stream) throws IOException {
        LogManager.getLogManager().readConfiguration(stream);
        stream.close();

        logger = createLogger();
        return logger;
    }

    private static java.util.logging.Logger createLogger() {
        return java.util.logging.Logger.getLogger("Server logger");
    }
}
