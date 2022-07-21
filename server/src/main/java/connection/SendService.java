package connection;

import chat_data_objects.Message;

import java.net.Socket;

public interface SendService {
    void sendMessage(Message message, Socket socket);
    void sendError(String msg, Socket socket);

    // and another later
}
