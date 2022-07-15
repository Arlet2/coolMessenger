package connection;

import exceptions.StreamReadingException;
import server_utils.Logger;
import utils.ConfigReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class ConnectionService {
    private ServerSocket serverSocket;
    private int serverPort;
    private final int DEFAULT_SERVER_PORT = 2020;
    private final String CONFIG_FILE_NAME = "config.ar";

    public ConnectionService() {
        serverPort = assignPort();
    }

    private int assignPort() {
        try {
            return ConfigReader.readPort(CONFIG_FILE_NAME);
        } catch (FileNotFoundException e) {
            Logger.getInstance().warning(CONFIG_FILE_NAME + " not found. Application will use default port: "
                    + DEFAULT_SERVER_PORT);
        } catch (StreamReadingException e) {
            System.out.println("Error with reading");
            Logger.getInstance().warning("Error with file reading. Application will use default port: " +
                    DEFAULT_SERVER_PORT);
        }
        return DEFAULT_SERVER_PORT;
    }

    public void openServerAndReassignPort() {
        serverPort = assignPort();
        openServer();
    }

    public void openServer() {
        closeServer();
        try {
            serverSocket = new ServerSocket(serverPort);
            Logger.getInstance().info("Server is opened on " + serverPort + " port");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().severe("Server was not opened. Try to use another port.");
        }
    }

    private void closeServer() {
        Optional.ofNullable(serverSocket).ifPresent((serverSocket) -> {
            try {
                serverSocket.close();
            } catch (IOException ignored) {
                // если не удалось закрыть, соединение будет пересоздано в любом случае
            }
        });
    }

    public Socket connectWithClient() throws IOException {
        return serverSocket.accept();
    }
}
