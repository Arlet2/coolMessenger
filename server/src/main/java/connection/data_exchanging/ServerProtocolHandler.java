package connection.data_exchanging;

import connection.NetDataExchangeHandler;
import connection.PackageService;
import connection.data_objects.AuthDataObject;

import java.net.Socket;

public interface ServerProtocolHandler {
    AuthDataObject createSession(Socket socket);

    boolean isTheSameProtocolWithClient(AuthDataObject authDataObject);

    int getProtocolNumber();

    NetDataExchangeHandler getNetExchangeHandler();

    PackageService getPackageService();
}
