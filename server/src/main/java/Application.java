import connection.data_objects.AuthDataObject;
import encryptors.SimpleEncryptor;
import exceptions.AuthException;
import exceptions.DataSavingException;
import exceptions.IncorrectProtocolException;
import server_utils.Logger;
import connection.*;
import connection.data_exchanging.BasicServerProtocolHandler;
import connection.data_exchanging.ServerProtocolHandler;
import data_saving.DataStorageService;
import data_saving.FileStorage;
import exceptions.ServerOpeningException;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Application {
    private static ConnectionService connectionService;
    private static ServerProtocolHandler protocolHandler;
    private static NetDataExchangeHandler netDataExchangeHandler;
    private static DataStorageService storageService;
    private static PackageService packageService;
    private static AuthService authService;
    private static SendService sendService;

    // workers

    private static final ExecutorService authWorkers = Executors.newCachedThreadPool(new ThreadFactory() {
        private final ThreadGroup group = new ThreadGroup("auth-workers");
        private final AtomicInteger counter = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(group, r, group.getName() + "-" + counter.getAndIncrement());
        }
    });

    private static final ExecutorService generalWorkers = Executors.newCachedThreadPool(new ThreadFactory() {
        private final ThreadGroup group = new ThreadGroup("general-workers");
        private final AtomicInteger counter = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(group, r, group.getName() + "-" + counter.getAndIncrement());
        }
    });

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

        initDependentByProtocolHandlerServices();

        sendService = new BasicSendService(protocolHandler);


        storageService = new FileStorage(SimpleEncryptor.ENCRYPTION_PROTOCOL, "test_users.bin");

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
        authWorkers.shutdown();

        authService.saveAllUsersData();
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
        generalWorkers.execute(() -> {
            processUser(user);
        });
    }

    private static void disconnectWithSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException ignored) {
            // если не получается отключить сокет, то он всё равно не будет использоваться
        }
    }

    private static void disconnectWithSocket(Socket socket, String msg) {
        sendService.sendError(msg, socket);

        disconnectWithSocket(socket);
    }

    private static void processUser(User user) {

    }
}
