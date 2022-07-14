package connection.data_exchanging;

import connection.ExchangeHandler;
import connection.User;
import connection.data_objects.AuthDataObject;
import exceptions.AuthException;

import java.net.Socket;

public interface ServerProtocolHandler {
    AuthDataObject createSession(Socket socket);

    User createUser(AuthDataObject authDataObject) throws AuthException;

    boolean isTheSameProtocolWithClient(AuthDataObject authDataObject);

    int getProtocolNumber();

    ExchangeHandler getExchangeHandler();
}
