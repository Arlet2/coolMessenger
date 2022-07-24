package connection;

import connection.data_exchanging.ServerProtocolHandler;
import connection.data_objects.AuthDataObject;
import data_saving.DataStorageService;
import data_saving.UserCard;
import exceptions.AuthException;
import exceptions.DataSavingException;
import exceptions.IncorrectProtocolException;
import server_utils.PasswordHashGenerator;

import java.util.HashMap;

public class BasicAuthService implements AuthService {
    private final ServerProtocolHandler protocolHandler;
    private final DataStorageService dataStorage;
    private HashMap<String, UserCard> userCards;

    public BasicAuthService(ServerProtocolHandler protocolHandler, DataStorageService dataStorage) {
        this.protocolHandler = protocolHandler;
        this.dataStorage = dataStorage;

        loadUserCards();
    }

    private void loadUserCards() {
        try {
            userCards = (HashMap<String, UserCard>) dataStorage.loadData();
        } catch (ClassCastException e) {
            throw new DataSavingException("Incorrect structure was saved on file", e);
        }
    }

    public String authUserAndGetNickname(AuthDataObject authDataObject)
            throws AuthException, IncorrectProtocolException {

        if (authDataObject.getProtocol() != protocolHandler.getProtocolNumber())
            throw new IncorrectProtocolException();

        if (authDataObject.isLogin())
            login(authDataObject);
        else
            register(authDataObject);

        return authDataObject.getNickname();
    }

    private void login(AuthDataObject authDataObject) throws AuthException {
        if (!isNicknameExist(authDataObject.getNickname()))
            throw new AuthException("This login is not exist");
        String userPasswordHash = PasswordHashGenerator.generateHash(authDataObject.getPassword());
        if (!userPasswordHash.equals(userCards.get(authDataObject.getNickname()).getPasswordHash()))
            throw new AuthException("Incorrect password");
    }

    private void register(AuthDataObject authDataObject) throws AuthException {
        if (isNicknameExist(authDataObject.getNickname()))
            throw new AuthException("This login is exist");
        createAndAppendUserCard(authDataObject);
    }

    private boolean isNicknameExist(String nickname) {
        return userCards.containsKey(nickname);
    }

    private synchronized void createAndAppendUserCard(AuthDataObject authDataObject) {
        UserCard userCard = new UserCard(authDataObject.getNickname(),
                PasswordHashGenerator.generateHash(authDataObject.getPassword()));

        userCards.put(userCard.getNickname(), userCard);
    }
}
