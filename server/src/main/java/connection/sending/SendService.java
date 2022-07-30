package connection.sending;

import chat_data_objects.ChatData;
import exceptions.NetDataTransferException;

import java.net.Socket;

public interface SendService {
    void sendChatContent(ChatData chatData, Socket socket) throws NetDataTransferException;
    void sendError(String msg, Socket socket) throws NetDataTransferException;
}
