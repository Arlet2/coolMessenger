import connection.data_objects.AuthDataObject;
import connection.data_objects.NetDTO;
import exceptions.AuthException;
import exceptions.DataSavingException;
import exceptions.IncorrectProtocolException;
import server_utils.Logger;
import connection.*;
import connection.data_exchanging.BasicServerProtocolHandler;
import connection.data_exchanging.ServerProtocolHandler;
import connection.data_saving.DataStorageService;
import connection.data_saving.FileStorage;
import exceptions.ServerOpeningException;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    private static ConnectionService connectionService;
    private static ServerProtocolHandler protocolHandler;
    private static NetDataExchangeHandler netDataExchangeHandler;
    private static DataStorageService storageService;
    private static PackageService packageService;
    private static AuthService authService;

    // workers

    private static final ExecutorService authWorkers = Executors.newCachedThreadPool();

    public static void main(String[] args) {

        initServices();

        createServerTerminal();

        /*

        net exchange:

        exchangeHandler.sendDTO();
        exchangeHandler.getDTO();

        before it:

        packageService.packageDataWithEncryption();
        packageService.decryptData();

        */
    }

    private static void initServices() {

        connectionService = new ConnectionService();

        protocolHandler = new BasicServerProtocolHandler();

        storageService = new FileStorage(1, "test_users.bin");

        initDependentByProtocolHandlerServices();

        try {
            authService = new BasicAuthService(protocolHandler, storageService);
        } catch (DataSavingException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void initDependentByProtocolHandlerServices() {
        netDataExchangeHandler = protocolHandler.getNetExchangeHandler();
        packageService = protocolHandler.getPackageService();
    }

    private static void createServerTerminal() {
        Thread thread = new Thread(() -> {
            boolean isWorking = true;
            Scanner scanner = new Scanner(System.in);

            while (isWorking) {
                String input = scanner.nextLine();

                switch (input) {
                    case "start":
                        start();
                        break;
                    case "stop":
                        stop();
                        isWorking = false;
                        break;
                    case "help":
                        System.out.println("start - open server (or reopen if it's opened before)");
                        System.out.println("stop - close connection with all users");
                        break;
                    default:
                        System.out.println("Unknown command. Use help for view commands.");
                        break;
                }
            }
        }, "server-terminal");

        thread.setDaemon(true);

        thread.start();
    }

    private static void start() {
        try {
            connectionService.openServer();
        } catch (ServerOpeningException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        authWorkers.execute(Application::initUser);
    }

    private static void stop() {
        // disconnect all users + stop all workers
    }

    private static void initUser() {
        Socket socket;
        try {
            socket = connectionService.connectWithClient();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().severe("Failed to connect with client.");
            return;
        } finally {
            authWorkers.execute(Application::initUser);
        }

        AuthDataObject authDataObject = protocolHandler.createSession(socket);
        User user;
        try {
            user = new User(authService.authUserAndGetNickname(authDataObject), socket);
        } catch (AuthException e) {
            e.printStackTrace();
            Logger.getInstance().severe(e.getMessage());
            disconnectWithSocket(socket, e.getMessage());
            return;
        } catch (IncorrectProtocolException e) {
            e.printStackTrace();
            Logger.getInstance().severe("Incorrect protocol on client");
            disconnectWithSocket(socket);
            return;
        }

        // go to standard workers
    }

    private static void disconnectWithSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException ignored) {
            // если не получается отключить сокет, то он всё равно не будет использоваться
        }
    }

    private static void disconnectWithSocket(Socket socket, String msg) {
        // TODO: создать отдельный метод под отправку ошибок и сообщений
        NetDTO dto = packageService.packDataWithEncryption(msg, NetDTO.DataCode.ERROR);
        netDataExchangeHandler.sendDTO(socket, dto);

        disconnectWithSocket(socket);
    }
}
