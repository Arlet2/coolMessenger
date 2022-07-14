package server_utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;

public class Logger {
    private static final String LOGGER_CONFIG_FILE_NAME = "logger.ar";
    private static java.util.logging.Logger logger;

    public static java.util.logging.Logger getInstance() {
        if (logger != null)
            return logger;
        try (FileInputStream inputStream = new FileInputStream(LOGGER_CONFIG_FILE_NAME)) {
            LogManager.getLogManager().readConfiguration(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("server_utils.Logger's config file not found (name - +" + LOGGER_CONFIG_FILE_NAME +
                    "). Standard logger will be load");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot open config file");
        }
        logger = java.util.logging.Logger.getLogger("Server logger");
        return logger;
    }
}
