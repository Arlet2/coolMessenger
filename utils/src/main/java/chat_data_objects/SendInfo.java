package chat_data_objects;

public class SendInfo {
    private final int roomId;
    private final String senderName;

    SendInfo(int roomId, String senderName) {
        this.roomId = roomId;
        this.senderName = senderName;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getSenderName() {
        return senderName;
    }
}
