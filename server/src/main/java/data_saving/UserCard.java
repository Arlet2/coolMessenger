package data_saving;

public class UserCard {
    private final String nickname;
    private final String passwordHash;

    public UserCard(String nickname, String passwordHash) {
        this.nickname = nickname;
        this.passwordHash = passwordHash;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

}
