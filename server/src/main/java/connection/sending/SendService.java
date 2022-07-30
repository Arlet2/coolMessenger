package connection.sending;

import chat_data_objects.ChatData;
import chat_data_objects.Message;
import exceptions.NetDataTransferException;

import java.net.Socket;

public interface SendService {
    void sendChatContent(ChatData chatData, Socket socket) throws NetDataTransferException;
    void sendError(String msg, Socket socket) throws NetDataTransferException;

    // and another later
}
