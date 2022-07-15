package chat_data_objects;

public abstract class ChatData {
    private final SendInfo sendInfo;
    protected ChatData(SendInfo sendInfo) {
        this.sendInfo = sendInfo;
    }

    public SendInfo getSendInfo() {
        return sendInfo;
    }
}
