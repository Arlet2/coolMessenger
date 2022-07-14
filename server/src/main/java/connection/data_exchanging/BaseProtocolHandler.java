package connection.data_exchanging;

import connection.User;
import connection_utils.AuthDataObject;
import exceptions.AuthException;

import java.net.Socket;

public class BaseProtocolHandler implements ProtocolHandler {
    public static final int PROTOCOL_NUMBER = 10001;

    private final ExchangeHandler exchangeHandler = new BaseExchangeHandler();

    @Override
    public AuthDataObject createSession(Socket socket) {
        return exchangeHandler.getAuthInfo(socket);
    }

    @Override
    public User createUser(AuthDataObject authDataObject) throws AuthException {
        //TODO: заглушка..
        if (authDataObject.isLogin())
            return null;
        else
            throw new AuthException("123");
    }

    @Override
    public boolean isTheSameProtocolWithClient(AuthDataObject authDataObject) {
        return PROTOCOL_NUMBER == authDataObject.getProtocol();
    }

    @Override
    public int getProtocolNumber() {
        return PROTOCOL_NUMBER;
    }

    @Override
    public ExchangeHandler getExchangeHandler() {
        return exchangeHandler;
    }
}
