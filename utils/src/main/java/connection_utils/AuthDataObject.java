package connection_utils;

public class AuthDataObject {
    private final int protocol;
    private final String nickname;
    private final String password;
    private final boolean isLogin;
    AuthDataObject(int protocol, String nickname, String password, boolean isLogin) {
        this.protocol = protocol;
        this.nickname = nickname;
        this.password = password;
        this.isLogin = isLogin;
    }

    public String getNickname() {
        return nickname;
    }

    public int getProtocol() {
        return protocol;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
