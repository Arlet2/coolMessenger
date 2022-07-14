package connection.data_exchanging;

import connection.User;
import connection_utils.AuthDataObject;
import exceptions.AuthException;

import java.net.Socket;

public interface ProtocolHandler {
    AuthDataObject createSession(Socket socket);

    User createUser(AuthDataObject authDataObject) throws AuthException;

    boolean isTheSameProtocolWithClient(AuthDataObject authDataObject);

    int getProtocolNumber();

    ExchangeHandler getExchangeHandler();
}
