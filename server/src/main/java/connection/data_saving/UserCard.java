package connection.data_saving;

import java.util.ArrayList;

public class UserCard {
    private final String nickname;
    private final String passwordHash;
    private final ArrayList<Long> availableRooms = new ArrayList<>();

    public UserCard(String nickname, String passwordHash) {
        this.nickname = nickname;
        this.passwordHash = passwordHash;
    }

    public void addNewRoom(long roomId) {
        if (!availableRooms.contains(roomId))
            availableRooms.add(roomId);
    }

    public void removeRoom(long roomId) {
        availableRooms.remove(roomId);
    }

    public String getNickname() {
        return nickname;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public ArrayList<Long> getAvailableRooms() {
        return availableRooms;
    }
}
