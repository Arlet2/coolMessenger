package connection;

import connection.data_objects.AuthDataObject;
import exceptions.AuthException;
import exceptions.IncorrectProtocolException;

public interface AuthService {
    public String authUserAndGetNickname(AuthDataObject authDataObject)
            throws AuthException, IncorrectProtocolException;
}
