package rooms;

import connection.User;
import server_utils.PasswordHashGenerator;

import java.util.ArrayList;

public class Room {
    private final int roomId;
    private String passwordHash;
    private final ArrayList<User> usersList = new ArrayList<>();
    private final String creatorNickname;

    public Room(String creatorNickname,int roomId, String password) {
        this.roomId = roomId;
        this.creatorNickname = creatorNickname;
        this.passwordHash = PasswordHashGenerator.generateHash(password);
    }

    public void changePassword(String password) {
        this.passwordHash = PasswordHashGenerator.generateHash(password);
    }

    public void addUser(User user) {
        usersList.add(user);
    }

    public void removeUser(User user) {
        usersList.remove(user);
    }

    public void clearRoomFromUsers() {
        usersList.clear();
    }

    public void clearRoomFromUsersExcept(User... users) {
        usersList.removeIf(user -> {
            for (User value : users) {
                if (user.equals(value))
                    return false;
            }
            return true;
        });
    }

    public int getRoomId() {
        return roomId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getCreatorNickname() {
        return creatorNickname;
    }
}
