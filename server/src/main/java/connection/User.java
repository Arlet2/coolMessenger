package connection;

import java.net.Socket;

public class User {
    private final Socket socket;
    private String nickname;

    User(String nickname, Socket socket) {
        this.socket = socket;
        this.nickname = nickname;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getNickname() {
        return nickname;
    }
}
