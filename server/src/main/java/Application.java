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

        /*
        User user = ..<auth service>..
         */

        // go to standard workers
    }

    private static void initServices() {

        connectionService = new ConnectionService();

        protocolHandler = new BasicServerProtocolHandler();

        storageService = new FileStorage(1,"test_users.bin");

        initDependentByProtocolHandlerServices();

        authService = new BasicAuthService(protocolHandler, storageService);
    }

    private static void initDependentByProtocolHandlerServices() {
        netDataExchangeHandler = protocolHandler.getNetExchangeHandler();
        packageService = protocolHandler.getPackageService();
    }
}
